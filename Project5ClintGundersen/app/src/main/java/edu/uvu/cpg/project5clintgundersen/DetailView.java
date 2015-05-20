package edu.uvu.cpg.project5clintgundersen;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

import edu.uvu.cpg.project3clintgundersen.R;


public class DetailView extends Activity implements OnClickListener, OnFocusChangeListener
{
    Book selectedBook;
    ImageView cover;
    ImageView icon;
    EditText titleLabel;
    EditText authorLabel;
    EditText listRankingLabel;
    EditText userRatingLabel;
    EditText publishedLabel;
    EditText onOverdriveLabel;
    EditText readItLabel;
    EditText descriptionText;
    Button editButton;
    Button saveButton;
    Button deleteButton;
    Intent returnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        selectedBook = getIntent().getExtras().getParcelable("Book");

        //set up UI elements
        cover = (ImageView) findViewById((R.id.coverImage));
        if (selectedBook.getImageID() > 0)
            cover.setImageResource(selectedBook.getImageID());

        titleLabel = (EditText) findViewById(R.id.titleLabel);
        titleLabel.setText(selectedBook.getTitle());
        titleLabel.setOnFocusChangeListener(this);

        icon =  (ImageView) findViewById((R.id.icon));

        authorLabel = (EditText) findViewById(R.id.authorLabel);
        authorLabel.setText(selectedBook.getAuthor());
        authorLabel.setOnFocusChangeListener(this);

        listRankingLabel = (EditText) findViewById(R.id.listRankingLabel);
        listRankingLabel.setText(Integer.toString(selectedBook.getListRanking()));
        listRankingLabel.setOnFocusChangeListener(this);

        userRatingLabel = (EditText) findViewById(R.id.userRatingLabel);
        userRatingLabel.setText(Integer.toString(selectedBook.getRating()));
        userRatingLabel.setOnFocusChangeListener(this);

        publishedLabel = (EditText) findViewById(R.id.publishedLabel);
        publishedLabel.setText(Integer.toString(selectedBook.getYearPublished()));
        publishedLabel.setOnFocusChangeListener(this);

        onOverdriveLabel = (EditText) findViewById((R.id.overdriveLabel));
        onOverdriveLabel.setText(selectedBook.getOnPioneerOverdrive());

        readItLabel = (EditText) findViewById(R.id.readLabel);
        readItLabel.setText(selectedBook.getHaveRead());

        descriptionText = (EditText) findViewById((R.id.descriptionLabel));
        descriptionText.setText(selectedBook.getDescription());
        //descriptionText.setOnFocusChangeListener(this);

        editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(this);
        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.v("DetailView", " Resuming");
        setNotEditable(titleLabel);
        setNotEditable(authorLabel);
        setNotEditable(listRankingLabel);
        setNotEditable(userRatingLabel);
        setNotEditable(publishedLabel);
        setNotEditable(onOverdriveLabel);
        setNotEditable(readItLabel);
        setNotEditable(descriptionText);
        saveButton.setText("Done");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View myView)
    {
        Button clickedBtn = (Button)myView;

        if (clickedBtn == editButton)
        {
            Log.v("Button", "editButton clicked");
            //set our UI into a mode that can be edited
            saveButton.setText("Save");
            setEditable(titleLabel);
            setEditable(authorLabel);
            setEditable(listRankingLabel);
            setEditable(userRatingLabel);
            setEditable(publishedLabel);
            setEditable(onOverdriveLabel);
            setEditable(readItLabel);
            setEditable(descriptionText);
            titleLabel.requestFocus();

            //set listeners for our binary fields;
            onOverdriveLabel.setOnFocusChangeListener(new OnFocusChangeListener()
            {
                @Override
                public void onFocusChange(View view, boolean b)
                {
                    if (titleLabel.isFocused())
                    {
                        //keyboard wasnt showing, this is to force it
                        forceKeyboard();
                    }
                    else if (authorLabel.isFocused())
                    {
                        forceKeyboard();
                    }
                    else if (listRankingLabel.isFocused())
                    {
                        forceKeyboard();
                    }
                    else if (userRatingLabel.isFocused())
                    {
                        forceKeyboard();
                    }
                    else if (onOverdriveLabel.isFocused())
                    {
                        alert("Is this book available as an eBook on Pioneer Overdrive?",onOverdriveLabel);
                    }
                    else if(readItLabel.isFocused())
                    {
                        alert("Have you finished this book?",readItLabel);
                    }
                    else if(descriptionText.isFocused())
                    {
                        forceKeyboard();
                    }
                }
            });
        }
        else if (clickedBtn == saveButton)
        {
            Log.v("Button", "saveButton clicked");
            if (titleLabel.isFocusable())
            {
                //update selectedBook with changed values
                returnIntent = new Intent();
                selectedBook.setTitle(titleLabel.getText().toString());
                selectedBook.setAuthor(authorLabel.getText().toString());
                selectedBook.setListRanking(Integer.parseInt(listRankingLabel.getText().toString()));
                selectedBook.setRating(Integer.parseInt(userRatingLabel.getText().toString()));
                selectedBook.setYearPublished(Integer.parseInt(publishedLabel.getText().toString()));
                selectedBook.setOnPioneerOverdrive(onOverdriveLabel.getText().toString());
                selectedBook.setHaveRead(readItLabel.getText().toString());
                selectedBook.setDescription(descriptionText.getText().toString());
                Log.v("**Book Updated In DetailView** ", selectedBook.toString());

                returnIntent.putExtra("command", "edit");
                returnIntent.putExtra("Book", (android.os.Parcelable) selectedBook);
                setResult(Activity.RESULT_OK, returnIntent);
            }

            finish();
        }
        else if (clickedBtn ==  deleteButton)
        {
            Log.v("Button", "deleteButton clicked");
            returnIntent = new Intent();
            returnIntent.putExtra("command", "delete");
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    //couldnt get the keyboard to work, this is to force it
    public void forceKeyboard()
    {
        //*********************************************
        //this is only commented out because prof durney
        //is using a hardware keyboard, and I dont want to
        //force the software one on him, but it works
        //*********************************************
        //for forcing the keyboard to show up
        //InputMethodManager imm = (InputMethodManager)
        //        getSystemService(this.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void setEditable(EditText textSpot)
    {
        textSpot.setFocusable(true);
        textSpot.setTextIsSelectable(true);
        textSpot.setBackgroundColor(Color.LTGRAY);
    }

    public void setNotEditable(EditText textSpot)
    {
        textSpot.setFocusable(false);
        textSpot.setTextIsSelectable(false);
        textSpot.setBackgroundColor(Color.TRANSPARENT);
    }

    public void alert(String message, final EditText textToUpdate)
    {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage(message);
        myAlert.setCancelable(true);
        boolean check = false;
        myAlert.setPositiveButton("Yes",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        textToUpdate.setText("Yes");
                        dialog.cancel();
                    }
                });
        myAlert.setNegativeButton("No",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        textToUpdate.setText("No");
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = myAlert.create();
        alert11.show();
    }

    //this is here because Android studio says it has to be even though i have implemented the method
    //twice up above.
    @Override
    public void onFocusChange(View view, boolean b)
    {
    }
}
