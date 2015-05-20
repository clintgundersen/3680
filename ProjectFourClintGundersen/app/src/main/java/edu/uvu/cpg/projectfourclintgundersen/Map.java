package edu.uvu.cpg.projectfourclintgundersen;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by clint on 10/20/14.
 */
public class Map
{
    char[][] grid;
    int mapWidth;
    int mapHeight;

    public Map(Context context) throws FileNotFoundException
    {
        Log.i("Map Constructor ", "Accessed");
        populateMap(context);
    }

    public void populateMap(Context context) throws FileNotFoundException
    {
        //use an internal map txt file for this project
        Resources mapText = context.getResources();
        Scanner fileScanner = new Scanner(mapText.openRawResource(R.raw.maplayout));

        //setup map
        mapWidth = Integer.parseInt(fileScanner.next());
        Log.i("Map Width=", " " + mapWidth);
        mapHeight = Integer.parseInt(fileScanner.next());
        Log.i("Map Height=", " " + mapHeight);

        grid = new char[mapWidth][mapHeight];

        //loop through the remainder of the file and parse it out.
        int indexCount = 0;
        String line;
        while(fileScanner.hasNext())
        {
            line = fileScanner.next();
            grid[indexCount] = line.toCharArray();
            indexCount++;
        }
    }

    public char getTerrainAtCoordinate(XYCoordinate location)
    {
        final char OUT_OF_BOUNDS = 'X';
        if (locationInBounds(location))
            return grid[location.getYCoordinate()][location.getXCoordinate()];
        else return OUT_OF_BOUNDS;
    }

    public boolean locationInBounds(XYCoordinate location)
    {
        if (location.getXCoordinate() >= 0
                && location.getYCoordinate() >= 0
                && location.getXCoordinate() < mapWidth
                && location.getYCoordinate() < mapHeight)
            return true;
        else return false;
    }
}
