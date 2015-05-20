package edu.uvu.cpg.project5clintgundersen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import android.util.Log;
import android.view.View.OnClickListener;

import edu.uvu.cpg.project3clintgundersen.R;


public class ListActivity extends Activity implements OnClickListener
{
    //UI elements
    private ListView list;
    private Button addBookButton;
    private Button queryButton;
    private Button resetButton;
    AlertDialog queryDialog = null;

    //variable elements
    private static ArrayList<Book> books;
    private static ArrayAdapter<Book> bookAdapter;
    private Book currentBook;
    private int currentIndex;
    private Intent intent;
    private Context context;

    //database elements
    DataBaseHelper bookDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //setup listeners
        addBookButton = (Button) findViewById(R.id.addBookButton);
        addBookButton.setOnClickListener(this);
        queryButton = (Button) findViewById(R.id.queryButton);
        queryButton.setOnClickListener(this);
        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);

        list = (ListView)findViewById(R.id.bookList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                Log.v("Book" + books.get(position), "clicked");
                currentIndex = position;
                currentBook = books.get(position);
                launchDetailView();
            }
        });

        //set up database
        bookDB = new DataBaseHelper(this);

        refresh();

        //context
        context = this;
    }

    public void refresh()
    {
        books = bookDB.getAllBooks();
        updateList();
    }

    public void updateList()
    {
        bookAdapter = new ArrayAdapter<Book>(this, R.layout.rowlayout, R.id.label, books);
        list.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
    }

    public void queryAlert()
    {
        final String HAVE_READ = "Have Read";
        final String HAVE_NOT_READ = "Have Not Read";
        final String ON_OVERDRIVE = "On Overdrive";
        final String OLDER_1950 = "Older than 1950";
        final String MIDDLE_AGE = "Older than 2001";
        final String NEWER_2001 = "Newer than 2001";
        final String[] queries = new String[] {HAVE_READ, HAVE_NOT_READ, ON_OVERDRIVE,
                OLDER_1950, MIDDLE_AGE, NEWER_2001};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What kind of books are you looking for?");
        AlertDialog.Builder builder1 = builder.setSingleChoiceItems(queries, -1, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                switch (item)
                {
                    case 0:
                        parseQuery(HAVE_READ);
                        break;
                    case 1:
                        parseQuery(HAVE_NOT_READ);
                        break;
                    case 2:
                        parseQuery(ON_OVERDRIVE);
                        break;
                    case 3:
                        parseQuery(OLDER_1950);
                        break;
                    case 4:
                        parseQuery(MIDDLE_AGE);
                        break;
                    case 5:
                        parseQuery(NEWER_2001);
                        break;
                }
                queryDialog.dismiss();
            }

        });
        queryDialog = builder.create();
        queryDialog.show();
    }

    public void onClick(View myView)
    {
        Button clickedBtn = (Button)myView;
        if (clickedBtn == addBookButton)
        {
            //output to the log, do something
            Log.v("addBook Pressed", "Adding Book");
            currentBook = new Book();
            currentBook.setImageID(R.drawable.book);
            currentIndex = -1;
            launchDetailView();
        }
        else if (clickedBtn == queryButton)
        {
            queryAlert();
        }
        else if (clickedBtn == resetButton)
        {
            refresh();
        }
    }

    public void launchDetailView()
    {
        final int REQUEST_CODE = 1;
        Log.v("launching: ", "detail view");
        intent = new Intent(this, DetailView.class);
        intent.putExtra("Book", (android.os.Parcelable) currentBook);

        startActivityForResult(intent, REQUEST_CODE);
        Log.v("index", "" + currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        Log.v("onActivityResult", "*****");
        if (resultCode == RESULT_OK)
        {
            String command = intent.getExtras().getString("command");
            Log.v("COMMAND IS: ", "" + command);
            if(command.equals("edit"))
            {
                Book updatedBook = intent.getExtras().getParcelable("Book");
                if(currentIndex >= 0)
                {
                    int i = bookDB.modifyBook(updatedBook);
                    Log.i("Tried to modify got: ", "" + i);
                    refresh();
                }
                else
                {
                    bookDB.addBook(updatedBook);

                    refresh();
                }
            }
            if(command.equals("delete"))
            {
                if(currentIndex >=0)
                {
                    bookDB.deleteBook(books.get(currentIndex));
                    refresh();
                }
            }
        }
    }

    public void parseQuery(String query)
    {
        if (query.equals("Have Read"))
        {
            books = bookDB.runQuery("finshed = \'Yes\'");
        }
        else if (query.equals("Have Not Read"))
        {
            books = bookDB.runQuery("finshed = \'No\'");
        }
        else if (query.equals("On Overdrive"))
        {
            books = bookDB.runQuery("overdrive = \'Yes\'");
        }
        else if (query.equals("Older than 1950"))
        {
            books = bookDB.runQuery("published < 1950");
        }
        else if (query.equals("Newer than 2001"))
        {
            books = bookDB.runQuery("published > 2001");
        }
        else if (query.equals("Older than 2001"))
        {
            books = bookDB.runQuery("published < 2001");
        }
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
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


}
