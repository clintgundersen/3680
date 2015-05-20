package edu.uvu.cpg.projectfourclintgundersen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileNotFoundException;

/**
 * Created by clint on 10/19/14.
 */
public class DrawView extends View implements View.OnTouchListener
{
    final int TILE_SIDE = 86;
    final int TILES_HEIGHT = 11;
    final int TILES_WIDTH = 8;
    Bitmap currentMap;
    Bitmap forest = BitmapFactory.decodeResource(getResources(), R.drawable.forest);
    Bitmap mountain = BitmapFactory.decodeResource(getResources(), R.drawable.mountain);
    Bitmap out = BitmapFactory.decodeResource(getResources(), R.drawable.out);
    Bitmap plain = BitmapFactory.decodeResource(getResources(), R.drawable.plain);
    Bitmap water = BitmapFactory.decodeResource(getResources(), R.drawable.water);
    Bitmap playerPic = BitmapFactory.decodeResource(getResources(), R.drawable.player);
    Character player;
    Canvas canvas;
    XYCoordinate mapCorner;
    XYCoordinate screenLocation;

    //constructors
    public DrawView(Context context) throws FileNotFoundException
    {
        super(context);
        setup();
    }

    //constructor required for inflation  from the resource file
    public DrawView(Context context, AttributeSet attributeSet) throws FileNotFoundException
    {
        super(context, attributeSet);
        setup();
    }

    public void setup() throws FileNotFoundException
    {
        setOnTouchListener(this);
        player = new Character(getContext());
        mapCorner = new XYCoordinate();
        screenLocation = new XYCoordinate();
    }

    public void onDraw(Canvas passCanvas)
    {
        canvas = passCanvas;
        Paint tile = new Paint();
        drawMap(canvas);
        drawPlayer();
    }

    public void drawMap(Canvas canvas)
    {
        Paint tile = new Paint();
        XYCoordinate track = new XYCoordinate(mapCorner.getXCoordinate(), mapCorner.getYCoordinate());
        for (int i = 0; i < TILES_HEIGHT; i++)
        {
            for (int j = 0; j < TILES_WIDTH; j++)
            {
                //Log.i("==Parsing", "" + track.toString());
                canvas.drawBitmap(parseTerrain(track), j * TILE_SIDE, i * TILE_SIDE, tile);
                track.xIncrement();
            }
            //Log.i("+++Incrementing ", "Column+++");
            track.yIncrement();
            track.setXCoordinate(mapCorner.getXCoordinate());
        }
    }

    public void drawPlayer()
    {
        Paint tile = new Paint();
        canvas.drawBitmap(playerPic, screenLocation.getXCoordinate() *
                TILE_SIDE, screenLocation.getYCoordinate() * TILE_SIDE, tile);
    }

    public Bitmap parseTerrain(XYCoordinate xy)
    {
        switch (player.getMap().getTerrainAtCoordinate(xy))
        {
            case 'M':
                return mountain;
            case '-':
                return plain;
            case 'T':
                return forest;
            case '#':
                return water;
            default:
                return out;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        //if you handle the touch return true, otherwise return false
        //if you return false it travels through the view heirarchy to find where it belings
        if (event.getAction()==MotionEvent.ACTION_UP)
        {
            final int MIN_TAB = 200; //for setting xy coordinates for movement
            final int MAX_TAB = 700; //for setting xy coordinates for movement
            final int MID_TAB = 400; //for setting xy coordinates for movement
            //move left
            Log.i("**Touch Event: ", "X: " + event.getX() + " Y:" + event.getY());
            if (event.getX() < MIN_TAB && event.getY() > MIN_TAB && event.getY() < MAX_TAB)
            {
                Log.i ("Moving--", "--Left <--");
                if (player.location.getXCoordinate() > 0)
                {
                    player.location.xDecrement();
                    screenLocation.xDecrement();
                    updateScreen();
                    invalidate();
                    //Log.i ("Location ", ":" + player.location);
                }
            }
            //move right
            else if (event.getX() > MID_TAB && event.getY() > MIN_TAB)
            {
                Log.i ("Moving--", "--Right -->");
                if (player.location.getXCoordinate() < player.getMap().mapWidth - 1)
                {
                    player.location.xIncrement();
                    screenLocation.xIncrement();
                    updateScreen();
                    invalidate();
                    //Log.i ("Location ", ":" + player.location);
                }
            }
            //move up
            else if (event.getX() > MIN_TAB && event.getY() < MIN_TAB)
            {
                Log.i ("Moving--", "--Up  ^");
                if (player.location.getYCoordinate() > 0)
                {
                    player.location.yDecrement();
                    screenLocation.yDecrement();
                    updateScreen();
                    invalidate();
                    //Log.i ("Location ", ":" + player.location);
                }
            }
            //move down
            else if (event.getX() > MIN_TAB && event.getY() > MAX_TAB)
            {
                Log.i ("Moving--", "--Down v  ^");
                if (player.location.getYCoordinate() < player.getMap().mapHeight - 1)
                {
                    player.location.yIncrement();
                    screenLocation.yIncrement();
                    updateScreen();
                    invalidate();
                    //Log.i ("Location ", ":" + player.location);
                }
            }
        }
        return true;
    }

    public void updateScreen()
    {
        if (player.location.getXCoordinate() > mapCorner.getXCoordinate() + TILES_WIDTH - 1)
        {
            mapCorner.setXCoordinate(player.getLocation().getXCoordinate());
            Log.i("Location ", ":" + player.location);
            Log.i ("Map Corner ", ":" + mapCorner);
            screenLocation.setXCoordinate(0);
            Log.i ("Screen Location ", ":" + screenLocation);
        }
        else if (player.location.getXCoordinate() < mapCorner.getXCoordinate())
        {
            mapCorner.setXCoordinate((player.getLocation().getXCoordinate() - TILES_WIDTH + 1));
            Log.i("Location ", ":" + player.location);
            Log.i ("Map Corner ", ":" + mapCorner);
            screenLocation.setXCoordinate(TILES_WIDTH - 1);
        }
        else if (player.location.getYCoordinate() > mapCorner.getYCoordinate() + TILES_HEIGHT - 1)
        {
            mapCorner.setYCoordinate(player.getLocation().getYCoordinate());
            Log.i ("Location ", ":" + player.location);
            Log.i ("Map Corner ", ":" + mapCorner);
            screenLocation.setYCoordinate(0);
            Log.i ("Screen Location ", ":" + screenLocation);
        }
        else if (player.location.getYCoordinate() < mapCorner.getYCoordinate())
        {
            mapCorner.setYCoordinate(player.getLocation().getYCoordinate() - TILES_HEIGHT + 1);
            Log.i ("Location ", ":" + player.location);
            Log.i ("Map Corner ", ":" + mapCorner);
            screenLocation.setYCoordinate(TILES_HEIGHT - 1);
        }
    }
}

