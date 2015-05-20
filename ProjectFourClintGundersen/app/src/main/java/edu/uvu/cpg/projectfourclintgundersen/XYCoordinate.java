package edu.uvu.cpg.projectfourclintgundersen;

import android.util.Log;

/**
 * Created by clint on 10/20/14.
 */
public class XYCoordinate
{
    private int xCoor;
    private int yCoor;


    public XYCoordinate()
    {
        xCoor = 0;
        yCoor = 0;
    }

    public XYCoordinate(int xCoor, int yCoor)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }

    public void setXY(int xCoor, int yCoor)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }

    public int getXCoordinate()
    {
        return xCoor;
    }

    public void setXCoordinate(int xCoor)
    {
        this.xCoor = xCoor;
    }

    public int getYCoordinate()
    {
        return yCoor;
    }

    public void setYCoordinate(int yCoor)
    {
        this.yCoor = yCoor;
    }

    public String toString()
    {
        return String.valueOf(xCoor) + ',' + String.valueOf(yCoor);
    }

    public void xIncrement()
    {
        xCoor++;
    }

    public void xDecrement()
    {
        xCoor--;
    }

    public void yIncrement()
    {
        yCoor++;
    }

    public void yDecrement()
    {
        yCoor--;
    }
}
