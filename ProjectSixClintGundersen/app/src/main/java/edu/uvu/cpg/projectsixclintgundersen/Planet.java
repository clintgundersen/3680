package edu.uvu.cpg.projectsixclintgundersen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by clint on 11/16/14.
 */
public class Planet
{
    @Expose
    private String type;
    @Expose
    private String name;
    @Expose
    private int moonCount;
    @Expose
    private double distanceFromSun;
    @Expose
    private int diameterInKilometers;
    @Expose
    private ArrayList <Planet> satellites;

    public Planet()
    {
        name = null;
        moonCount = 0;
        distanceFromSun = 0;
        diameterInKilometers = 0;
        satellites = new ArrayList<Planet>();
    }

    public Planet(String name, int moonCount, double distanceFromSun, int diameterInKilometers)
    {
        this.name = name;
        this.moonCount = moonCount;
        this.distanceFromSun = distanceFromSun;
        this.diameterInKilometers = diameterInKilometers;
        satellites = new ArrayList<Planet>();
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setDistanceFromSun(double distanceFromSun)
    {
        this.distanceFromSun = distanceFromSun;
    }

    public double getDistanceFromSun()
    {
        return distanceFromSun;
    }

    public void setMoonCount(int moonCount)
    {
        this.moonCount = moonCount;
    }

    public int getMoonCount()
    {
        return moonCount;
    }

    public void setDiameterInKilometers(int diameterInKilometers)
    {
        this.diameterInKilometers = diameterInKilometers;
    }

    public int getDiameterInKilometers()
    {
        return diameterInKilometers;
    }

    public void addSatellite(Planet moon)
    {
        satellites.add(moon);
    }

    public ArrayList getSatellites()
    {
        return satellites;
    }

    public Planet getSatelliteAtPosition(int position)
    {
        return satellites.get(position);
    }
}
