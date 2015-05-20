package edu.uvu.cpg.projectsixclintgundersen;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DownloadPlanetsActivity extends Activity implements OnClickListener
{
    final int JUPITER_ORBIT = 4;
    final int GANYMEDE_ORBIT = 2;
    private String durneyURL = "http://universe.tc.uvu.edu/cs3680/project/p6/planets.js";
    private String webContent;
    private Button downloadButton;
    private TextView displayText;
    private Context context;
    private ArrayList<Planet> planets;
    //private JSONArray planets;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        downloadButton = (Button) findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(this);

        displayText = (TextView) findViewById(R.id.displayText);
        displayText.setText("Click to download JSON.");

        context = this;
    }

    @Override
    public void onClick(View view)
    {
        Button clickedButton = (Button) view;

        if (clickedButton == downloadButton)
        {
            //check to see if we have network connection
            //modified from example found at developer.androic.com/training/basics/network-ops/connecting.html
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
            {
                new DownloadPlanetsTask().execute(durneyURL);
            }
            else
            {
                displayText.setText("No network connection available.");
            }
        }
    }

    public String downloadJSON(String url) throws IOException
    {
        final int TIMEOUT = 15000; //milliseconds
        //final int BUFFER_LENGTH = 5500;
        InputStream input = null;

        //modified from example found at developer.android.com/training/basics/network-ops/connecting.html
        try
        {
            URL current = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) current.openConnection();
            connection.setReadTimeout(TIMEOUT);
            connection.setConnectTimeout(TIMEOUT);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            //start the query
            connection.connect();
            int response =  connection.getResponseCode();
            Log.i("Response is :", "" + response);
            input = connection.getInputStream();

            //convert the Input Stream into a String
            String content = readContent(input);

            return content;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return "ERROR";
        }
        finally
        {
            if (input != null)
                input.close();
        }
    }

    public String readContent(InputStream input) throws IOException, JSONException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));

        webContent = "";
        String soundingString = reader.readLine();
        while (soundingString != null)
        {
            webContent += soundingString;
            soundingString = reader.readLine();
        }

        reader.close();
        input.close();

        //json stuff
        JSONArray jArray = new JSONArray(webContent);
        parseSolarSystem(jArray);

        return webContent;
    }

    public void parseSolarSystem(JSONArray jArray) throws JSONException
    {
        planets = new ArrayList<Planet>();
        for (int i = 0; i < jArray.length(); i++)
        {

            planets.add(parsePlanet(jArray.getJSONObject(i)));
            Log.i("Planet " +  planets.get(i).getName(), " added.");

            if (jArray.getJSONObject(i).has("satellites"))
            {
                Log.i("Adding Moon", "");
                JSONArray satellites = jArray.getJSONObject(i).getJSONArray("satellites");
                for (int j = 0; j < satellites.length(); j++)
                {
                    planets.get(i).addSatellite(parsePlanet(satellites.getJSONObject(j)));
                    Log.i("Moon " +  planets.get(i).getSatelliteAtPosition(j).getName(), " added.");
                }
            }
        }
    }

    public Planet parsePlanet(JSONObject jPlanet) throws JSONException
    {
        Planet current = new Planet();

        if (jPlanet.has("type"))
            current.setType(jPlanet.getString("type"));
        if (jPlanet.has("name"))
            current.setName(jPlanet.getString("name"));
        if (jPlanet.has("moonCount"))
            current.setMoonCount(jPlanet.getInt("moonCount"));
        if (jPlanet.has("distanceFromSun"))
            current.setDistanceFromSun(jPlanet.getDouble("distanceFromSun"));
        if (jPlanet.has("diameterKm"))
            current.setDiameterInKilometers(jPlanet.getInt("diameterKm"));

        return current;
    }

    private class DownloadPlanetsTask extends AsyncTask <String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls)
        {
            // per android docs, params comes from the execute() call: params[0] is the url.
            try
            {
                return downloadJSON(urls[0]);
            }
            catch (IOException exception)
            {
                return "Error processing request.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {
            //fetch planet to make code easier to read
            Planet ganymede = planets.get(JUPITER_ORBIT).getSatelliteAtPosition(GANYMEDE_ORBIT);
            displayText.setText("The third satellite of Jupiter is " +
                    ganymede.getName() + ". It's diameter is " + ganymede.getDiameterInKilometers()
                    + " kilometers.");
        }
    }
}

