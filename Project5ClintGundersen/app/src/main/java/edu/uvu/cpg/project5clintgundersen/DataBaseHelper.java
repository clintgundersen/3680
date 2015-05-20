package edu.uvu.cpg.project5clintgundersen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by clint on 11/1/14.
 */
public class DataBaseHelper extends SQLiteOpenHelper
{
    //database field names
    final static String TABLE = "SciFiBooks";
    final static String IMAGE_ID = "imagepath";
    final static String ID = "_id";
    final static String TITLE = "title";
    final static String AUTHOR = "author";
    final static String LIST_RANKING = "listRank";
    final static String USER_RATING = "userRating";
    final static String PUBLISHED_DATE = "published";
    final static String ON_OVERDRIVE = "overdrive";
    final static String HAVE_READ = "finshed";
    final static String DESCRIPTION = "description";

    final static String[] COLUMNS = {ID, TITLE, AUTHOR};

    //database location
    final static String DB_PATH = "/data/data/edu.uvu.cpg.project5clintgundersen/databases/";

    //set up constructor
    final static int VERSION = 1;
    final static String DATA_BASE_NAME = "sciFiTop100Books.sqLite";

    //class variables
    private Context context;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Book current;

    public DataBaseHelper(Context context)
    {
        //handing null for factory
        super(context, DATA_BASE_NAME, null, VERSION);
        //store the context for later use
        this.context = context;
        Log.i("DB Constructor", "accessed +++");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //i am out of time and doing things ugly
        Log.i("DB OnCreate", "accessed +++");


        db.execSQL("CREATE TABLE SciFiBooks (imagepath TEXT, _id INTEGER PRIMARY KEY, title TEXT, author TEXT, listRank NUMERIC, userRating NUMERIC, published NUMERIC, overdrive TEXT, finshed TEXT, description TEXT);");
        db.execSQL("INSERT INTO SciFiBooks VALUES('nineteen84',1,'Nineteen Eighty-Four','George Orwell',5,7,1949,'Yes','Yes','English socialism or Ingsoc conrols the thoughts of the population as one man deals with government surveillance and thought police.');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('ringworld',2,'Ringworld','Lary Niven',13,7,1970,'No','Yes','A group of explorers investigate the Ringworld, a compromise on the Dyson Sphere.');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('ubik',3,'Ubik','Phillip K Dick',42,10,1969,'Yes','Yes','A team of nti-psychics tries to discover why the world is unraveling around them');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('diamondage',4,'Diamond Age','Neal Stephenson',51,10,1995,'Yes','Yes','A Nano-punk novel predicting the impending future of 3-D printing.');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('rama',5,'Rendezvous With Rama','Arthur C. Clarke',14,7,1973,'No','Yes','A team of scientists embark to explore the first alien craft discovered in our solar system.');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('carbon',6,'Altered Carbon','Richard K. Morgan',87,9,2002,'No','Yes','A private eye sets out to solve the murder of his immortal employer.');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('canticle',7,'A Canticle for Leibowitz','Walter M. Miller Jr',48,7,1960,'No','Yes','In a future where science and reading are persecuted, a group of onks seek to protect the knowledge of the past.');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('caves',8,'Caves of Steel','Isaac Asimov',30,8,1954,'No','Yes','A etective and his robot partner set out to solve a murder.');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('gateway',9,'Gateway','Fredrick Pohl',32,7,1977,'No','Yes','Trying to  make some money, a man volunteers to test alien technology to explore the universe.');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('lordoflight',10,'Lord of Light','Roger Zelazny',33,0,1967,'No','No','In the far future, refugees from Earth try to survive.');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('triffids',11,'Day of the Triffids\n" +
                "','John Wyndham',43,10,1961,'Yes','Yes','A man and a woman try to survive as the end of the world unfolds around them.');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('stars',12,'The Stars My Destination','Alfred Bester',31,9,1956,'No','Yes','A man embarks on a quest for revenge against the star ship that left him to die.');");
        db.execSQL("INSERT INTO SciFiBooks VALUES('stranger',13,'Stranger In A Strange Land\n" +
                "','Robert A Heinlan',6,0,1961,'No','No','The story of Valentine Michael Smith, the man from Mars who taught humankind grokking and water-sharing. And love.');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersionNumber, int newVersionNumber)
    {
        Log.w(DataBaseHelper.class.getName(),
                "Upgrading database from: " + oldVersionNumber + " to " + newVersionNumber);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public Book getBook(int id)
    {
        db = this.getReadableDatabase();
        cursor = db.query(TABLE, COLUMNS, " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Book book = buildBookFromCursor(cursor);
        return book;
    }

    public void addBook(Book book)
    {
        db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put(IMAGE_ID, book.getImageID());
        newValues.put(TITLE, book.getTitle());
        newValues.put(AUTHOR, book.getAuthor());
        newValues.put(LIST_RANKING, book.getListRanking());
        newValues.put(USER_RATING, book.getRating());
        newValues.put(PUBLISHED_DATE, book.getYearPublished());
        newValues.put(ON_OVERDRIVE, book.getOnPioneerOverdrive());
        newValues.put(HAVE_READ, book.getHaveRead());
        newValues.put(DESCRIPTION, book.getDescription());

        Log.i("Adding book", " to dB");

        db.insert(TABLE, null, newValues);
        db.close();
    }

    public int modifyBook(Book book)
    {
        db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put(TITLE, book.getTitle());
        newValues.put(AUTHOR, book.getAuthor());
        newValues.put(LIST_RANKING, book.getListRanking());
        newValues.put(USER_RATING, book.getRating());
        newValues.put(PUBLISHED_DATE, book.getYearPublished());
        newValues.put(ON_OVERDRIVE, book.getOnPioneerOverdrive());
        newValues.put(HAVE_READ, book.getHaveRead());
        newValues.put(DESCRIPTION, book.getDescription());

        Log.i("****Updating book", "" + book.getId());
        int i = db.update(TABLE, newValues, ID + "=" + book.getId(), null);
        //int i = db.update("SciFiBooks", newValues, "_id=0", null);
        db.close();
        return i;
    }

    public void deleteBook(Book book)
    {
        db = this.getWritableDatabase();
        db.delete(TABLE, ID + " = ?", new String[]{String.valueOf(book.getId())});
        db.close();
    }

    public ArrayList<Book> getAllBooks()
    {
        ArrayList <Book> books = new ArrayList<Book>();
        String query = "SELECT * FROM " + TABLE;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(query, null);

        Book book =  null;
        if (cursor.moveToFirst())
        {
            do
            {
                book = buildBookFromCursor(cursor);

                books.add(book);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return books;
    }

    public Book buildBookFromCursor(Cursor cursor)
    {
        final int IMAGE_COL = 0;
        final int ID_COL = 1;
        final int TITLE_COL = 2;
        final int AUTHOR_COL = 3;
        final int LIST_RANK_COL = 4;
        final int RATING_COL = 5;
        final int PUBLISHED_COL = 6;
        final int OVERDRIVE_COL = 7;
        final int HAVE_READ_COL = 8;
        final int DESCRIPTION_COL = 9;

        Book book = new Book();

        book.setImageID(getImageId(cursor.getString(IMAGE_COL)));
        book.setId(cursor.getInt(ID_COL));
        book.setTitle(cursor.getString(TITLE_COL));
        book.setAuthor(cursor.getString(AUTHOR_COL));
        book.setListRanking(cursor.getInt(LIST_RANK_COL));
        book.setRating(cursor.getInt(RATING_COL));
        book.setYearPublished(cursor.getInt(PUBLISHED_COL));
        book.setOnPioneerOverdrive(cursor.getString(OVERDRIVE_COL));
        book.setHaveRead(cursor.getString(HAVE_READ_COL));
        book.setDescription(cursor.getString(DESCRIPTION_COL));

        return book;
    }

    public int getImageId(String name)
    {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public ArrayList <Book> runQuery(String request)
    {
        ArrayList <Book> books = new ArrayList <Book>();
        String query = "SELECT * FROM " + TABLE + " WHERE " + request;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(query, null);

        Log.v("Running Query", "----");

        Book book =  null;
        if (cursor.moveToFirst())
        {
            do
            {
                book = buildBookFromCursor(cursor);
                Log.i("Book: ", book.toString());
                books.add(book);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return books;
    }
}


