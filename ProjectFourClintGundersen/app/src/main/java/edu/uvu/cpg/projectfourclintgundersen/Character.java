package edu.uvu.cpg.projectfourclintgundersen;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;

/**
 * Created by clint on 10/20/14.
 */
public class Character
{
    XYCoordinate location;
    Map current;

    public Character(Context context) throws FileNotFoundException
    {
        final int FIND_CENTER = 2;
        current = new Map(context);
        location = new XYCoordinate();
        //location = new XYCoordinate(current.mapWidth / FIND_CENTER, current.mapHeight / FIND_CENTER);
        Log.i("X = " + location.getXCoordinate(), "Y = " + location.getYCoordinate());
    }

    public XYCoordinate getLocation()
    {
        return location;
    }

    public Map getMap()
    {
        return current;
    }

}
