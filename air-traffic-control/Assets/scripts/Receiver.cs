using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using UnityEngine;

public class Receiver : MonoBehaviour
{
    public static Receiver Instance;

    public GameObject planePrefab;
    public Vector3 runwayStart;
    public Vector3 runwayEnd;
    public Vector3 skyPosition;

    private Queue<PlaneController> planeQueue = new Queue<PlaneController>();
    private bool isProcessing = false;

    private static Socket socket;
    private static byte[] _receiveBuffer = new byte[8192];
    private Queue<List<PlaneData>> planesToUpdate = new Queue<List<PlaneData>>();

    void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
            DontDestroyOnLoad(gameObject);
        }
        else
        {
            Destroy(gameObject);
        }

        ClientSocket();
    }

    void Start()
    {
        runwayStart = new Vector3(-9.0f, -3.013f, 0);
        runwayEnd = new Vector3(7.40f, -3.013f, 0);
        skyPosition = new Vector3(12.90f, 1.9612f, 0);

        socket.BeginReceive(_receiveBuffer, 0, _receiveBuffer.Length, SocketFlags.None, new AsyncCallback(ReceiveCallback), null);
    }

    void Update()
    {
        // Processar os dados na thread principal
        if (planesToUpdate.Count > 0)
        {
            List<PlaneData> planes;
            lock (planesToUpdate)
            {
                planes = planesToUpdate.Dequeue();
            }
            UpdatePlanes(planes);
        }
    }

    static void ClientSocket()
    {
        try
        {
            Debug.Log("Iniciando conexão");

            IPHostEntry ipHostInfo = Dns.GetHostEntry(Dns.GetHostName());
            IPAddress ipAddress = ipHostInfo.AddressList[0];
            IPEndPoint remoteEP = new IPEndPoint(ipAddress, 1234);

            socket = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
            socket.Connect(remoteEP);

            Debug.Log("Cliente conectado em " + socket.RemoteEndPoint.ToString());
        }
        catch (Exception e)
        {
            Debug.LogError("Exception : " + e.ToString());
        }
    }

    void ReceiveCallback(IAsyncResult AR)
    {
        int received = socket.EndReceive(AR);

        if (received <= 0)
            return;

        byte[] recData = new byte[received];
        Buffer.BlockCopy(_receiveBuffer, 0, recData, 0, received);

        string str = Encoding.Default.GetString(recData);
        Debug.Log("Dados recebidos do servidor: " + str);

        PlaneDataList dataList = JsonUtility.FromJson<PlaneDataList>(str);
        if (dataList != null && dataList.planes != null)
        {
            Debug.Log("Colocando dados na fila para atualização.");
            lock (planesToUpdate)
            {
                planesToUpdate.Enqueue(dataList.planes);
            }
        }

        socket.BeginReceive(_receiveBuffer, 0, _receiveBuffer.Length, SocketFlags.None, new AsyncCallback(ReceiveCallback), null);
    }

    public void UpdatePlanes(List<PlaneData> planes)
    {
        Debug.Log("Atualizando aviões com dados.");

        foreach (PlaneData plane in planes)
        {
            Debug.Log($"Atualizando avião: ID={plane.id}, Posição=({plane.coordinates.x}, {plane.coordinates.y})");
            GameObject planeObject = GameObject.Find(plane.id);

            if (planeObject == null)
            {
                if (planePrefab != null)
                {
                    planeObject = Instantiate(planePrefab);
                    planeObject.name = plane.id;
                    Debug.Log($"Nome do aviao = {planeObject.name}");
                }
                else
                {
                    Debug.LogWarning("Prefab de avião não atribuído.");
                    continue;
                }
            }

            PlaneController controller = planeObject.GetComponent<PlaneController>();
            if (controller != null)
            {
                controller.UpdatePlane(plane);
                Debug.Log("Avião " + plane.id + " atualizado.");

                planeQueue.Enqueue(controller);
            }
            else
            {
                Debug.LogWarning("PlaneController não encontrado para o avião " + plane.id);
            }
        }

        if (!isProcessing && planeQueue.Count > 0)
        {
            ProcessNextPlane();
        }
    }

    private void ProcessNextPlane()
    {
        if (planeQueue.Count > 0)
        {
            PlaneController controller = planeQueue.Dequeue();
            int numWaypointsToRunway = 10;
            int numWaypointsOnRunway = 10;

            controller.GenerateWaypointsForRunway(runwayStart, runwayEnd, skyPosition, numWaypointsToRunway, numWaypointsOnRunway);
            isProcessing = true;
        }
    }

    public void OnPlaneLandingCompleted(PlaneController controller)
    {
        isProcessing = false;
        if (planeQueue.Count > 0)
        {
            ProcessNextPlane();
        }
    }
}