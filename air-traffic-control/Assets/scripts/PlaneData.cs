using System;
using System.Collections.Generic;
using UnityEngine;

[Serializable]
public class PlaneData
{
    public string id;
    public string fuelLevel;
    public bool hasScale;
    public string altitude;
    public Coordinates coordinates;
}

[Serializable]
public class Coordinates
{
    public float x;
    public float y;
}

[Serializable]
public class PlaneDataList
{
    public List<PlaneData> planes;
}