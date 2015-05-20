package edu.uvu.cpg.project5cgundersensql;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by clint on 11/1/14.
 */
public class DataBase extends SQLiteOpenHelper
{
    final static int VERSION = 1;
    final static String DATA_BASE_NAME = "sciFiTop100Books.sqLite";
    Context context;

    public DataBase(Context context)
    {
        //handing null for factory
        super(context, DATA_BASE_NAME, null, VERSION);
        //store the context for later use
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i2)
    {

    }
}
