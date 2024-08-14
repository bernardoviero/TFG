using UnityEngine;
using System.Collections.Generic;

public class PlaneController : MonoBehaviour
{
    public string id;
    public float speed = 5.0f;

    private string fuelLevel;
    private bool hasScale;
    private string altitude;

    public GameObject planePrefab; 

    private Queue<Vector3> waypoints = new Queue<Vector3>();
    private bool isMoving = false;
    private bool isLanded = false;

    void Update()
    {
        if (isMoving && waypoints.Count > 0)
        {
            MoveToNextWaypoint();
        }
        else
        {
            // Movimento básico, se necessário
            float horizontalInput = Input.GetAxis("Horizontal");
            float verticalInput = Input.GetAxis("Vertical");

            Vector2 movement = new Vector2(horizontalInput, verticalInput) * speed * Time.deltaTime;

            Vector3 newPosition = transform.position + new Vector3(movement.x, movement.y, 0);

            transform.position = newPosition;
        }
    }

    public void UpdatePlane(PlaneData planeData)
    {
        id = planeData.id;
        fuelLevel = planeData.fuelLevel;
        hasScale = planeData.hasScale;
        altitude = planeData.altitude;
        Vector3 newPosition = new Vector3(planeData.coordinates.x, planeData.coordinates.y, transform.position.z);
        transform.position = newPosition;
        Debug.Log($"Avião {id} atualizado para posição {newPosition}");
    }




    public void SetWaypoints(List<Vector3> points)
    {
        waypoints = new Queue<Vector3>(points);
        isMoving = waypoints.Count > 0;
        isLanded = false;
    }

    private void MoveToNextWaypoint()
    {
        Vector3 target = waypoints.Peek();
        Vector3 direction = (target - transform.position).normalized;
        float step = speed * Time.deltaTime;
        transform.position = Vector3.MoveTowards(transform.position, target, step);

        Debug.Log($"Movendo avião {id} para {target}. Posição atual: {transform.position}");

        if (Vector3.Distance(transform.position, target) < 0.1f)
        {
            waypoints.Dequeue();
            if (waypoints.Count == 0)
            {
                isMoving = false;
                isLanded = true;
                NotifyLandingCompleted();
            }
        }
    }

    private void NotifyLandingCompleted()
    {
        Debug.Log("Avião " + id + " completou o pouso e decolagem.");
        Receiver.Instance.OnPlaneLandingCompleted(this);
    }

    public void GenerateWaypointsForRunway(Vector3 runwayStart, Vector3 runwayEnd, Vector3 skyPosition, int numWaypointsToRunway, int numWaypointsOnRunway)
    {
        List<Vector3> points = new List<Vector3>();
        // Waypoints do céu até o início da pista
        for (int i = 1; i <= numWaypointsToRunway; i++) {
            float t = (float)i / (numWaypointsToRunway + 1);
            Vector3 waypoint = Vector3.Lerp(transform.position, runwayStart, t);
            points.Add(waypoint);
            Debug.Log($"Waypoint céu-pista: {waypoint}");
        }
        // Waypoints ao longo da pista (aterrissagem)
        for (int i = 0; i < numWaypointsOnRunway; i++) {
            float t = (float)i / (numWaypointsOnRunway - 1);
            Vector3 waypoint = Vector3.Lerp(runwayStart, runwayEnd, t);
            points.Add(waypoint);
            Debug.Log($"Waypoint pista: {waypoint}");
        }
        // Waypoints do fim da pista de volta ao céu (decolagem)
        for (int i = 1; i <= numWaypointsToRunway; i++) {
            float t = (float)i / (numWaypointsToRunway + 1);
            Vector3 waypoint = Vector3.Lerp(runwayEnd, skyPosition, t);
            points.Add(waypoint);
            Debug.Log($"Waypoint pista-céu: {waypoint}");
        }
        SetWaypoints(points);
    }
}