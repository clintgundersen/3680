package edu.uvu.cpg.project3clintgundersen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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


public class ListActivity extends Activity implements OnClickListener
{
    //UI elements
    private ListView list;
    private Button addBookButton;

    //variable elements
    private static ArrayList<Book> books;
    private static ArrayAdapter<Book> bookAdapter;
    private Book currentBook;
    private int currentIndex;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //setup listeners
        addBookButton = (Button) findViewById(R.id.addBookButton);
        addBookButton.setOnClickListener(this);

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

        //fill books with values until a later assignment where I use a database
        populateArray();

        //setup array adapter for list view
        bookAdapter = new ArrayAdapter<Book>(this, R.layout.rowlayout, R.id.label, books);
        list.setAdapter(bookAdapter);
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
                    books.set(currentIndex, updatedBook);
                else books.add(updatedBook);

                Log.v("===Books Updated With at " + currentIndex, updatedBook.toString());
                bookAdapter.notifyDataSetChanged();
            }
            if(command.equals("delete"))
            {
                if(currentIndex >=0)
                    books.remove(currentIndex);
                bookAdapter.notifyDataSetChanged();
            }
        }
    }

    public void populateArray()
    {
        books = new ArrayList <Book>();
        books.add(new Book(R.drawable.nineteen84, "Nineteen Eighty-Four", "George Orwell", 5, 1949,
                7, "English Socialism or Ingsoc controls the thoughts of the English populace as one " +
                "man deals with government surveillance and thought police.", true, true));
        books.add(new Book(R.drawable.ringworld, "Ringworld", "Lary Niven", 13, 1970, 7, "A group " +
                "of explorers investigate the \"Ringworld.\" a compromise on the Dyson Sphere.", true, false));
        books.add(new Book(R.drawable.ubik, "Ubik", "Phillip K. Dick", 42, 1969, 10, "A team of " +
                "anti-psychics tries to discover why the world is unraveling around them", true, true));
        books.add(new Book(R.drawable.diamondage, "Diamond Age", "Neal Stephenson", 51, 1995, 10,
                "A Nano-punk novel predicting the impending future of 3-D printing", true, true));
        books.add(new Book(R.drawable.rama, "Rendezvous With Ranma", "Arthur C. Clarke", 14, 1973,
                7, "A team of scientists embark to explore the first alien craft discovered in our " +
                "solar system.", true, false));
        books.add(new Book(R.drawable.carbon,"Altered Carbon", "Richard K. Morgan", 87, 2002, 9,
                "A private eye sets out to solve the murder of his immortal employer", true, false));
        books.add(new Book(R.drawable.canticle, "A Canticle for Leibowitz", "Walter M. Miller Jr",
                48, 1960, 7, "In a future where science and reading are persecuted, a group of " +
                "monks seek to protect the knowledge of the past", true, false));
        books.add(new Book(R.drawable.caves, "Caves of Steel", "Isaac Asimov", 30, 1954, 8, "A " +
                "detective and his robot partner set out to solve a murder.", true, false));
        books.add(new Book(R.drawable.gateway, "Gateway", "Fredrick Pohl", 32, 1977, 7, "Trying to " +
                "make some money, a man volunteers to test alien technology to explore the " +
                "universe.", true, false ));
        books.add(new Book(R.drawable.lordoflight, "Lord of Light", "Roger Zelazny", 33, 1967, 00,
                "In the far future, refugees from Earth try to survive", false, false));
        books.add(new Book(R.drawable.triffids, "Day of the Triffids", "John Wyndham", 43, 1961,
                10, "A man and a woman try to survive as the end of the world unfolds around them.",
                true, true));
        books.add(new Book(R.drawable.stars, "The Stars My Destination", "Alfred Bester", 31,
                1956, 9, "A man embarks on a quest for revenge against the star ship that left him " +
                "to die.", true, false));
        books.add(new Book(R.drawable.stranger, "Stranger In A Strange Land", "Robert A Heinlan",
                6, 1961, 00, "The story of Valentine Michael Smith, the man from Mars who taught " +
                "humankind grokking and water-sharing. And love.", false, false));
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
