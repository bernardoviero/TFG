using UnityEngine;
using System.Collections.Generic;

public class ReceiverJSON : MonoBehaviour
{
    public static ReceiverJSON Instance;

    public GameObject planePrefab; // Prefab que será instanciado se o GameObject não existir
    public Vector3 runwayStart; // Coordenadas do início da pista
    public Vector3 runwayEnd;   // Coordenadas do fim da pista
    public Vector3 skyPosition; // Posição no céu

    private Queue<PlaneController> planeQueue = new Queue<PlaneController>();
    private bool isProcessing = false;

    void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
        }
        else
        {
            Destroy(gameObject);
        }
    }

    void Start()
    {
        // Definir as coordenadas do início e fim da pista
        runwayStart = new Vector3(-9.0f, -3.013f, 0); // Início da pista
        runwayEnd = new Vector3(7.40f, -3.013f, 0);   // Fim da pista
        skyPosition = new Vector3(12.90f, 1.9612f, 0); // Coordenadas fornecidas para o céu
    }

    public void UpdatePlanes(List<PlaneData> planes)
    {
        Debug.Log("Atualizando aviões com dados.");

        foreach (PlaneData plane in planes)
        {
            GameObject planeObject = GameObject.Find(plane.id);

            if (planeObject == null)
            {
                // Se o GameObject não for encontrado, crie um novo a partir do prefab
                if (planePrefab != null)
                {
                    planeObject = Instantiate(planePrefab);
                    planeObject.name = plane.id; // Defina o nome do novo GameObject
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

                // Adicionar à fila de aviões
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
            int numWaypointsToRunway = 10; // Número de waypoints para chegar à pista
            int numWaypointsOnRunway = 10; // Número de waypoints ao longo da pista

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