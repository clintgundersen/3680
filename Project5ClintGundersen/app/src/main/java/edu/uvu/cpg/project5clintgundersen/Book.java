package edu.uvu.cpg.project5clintgundersen;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import edu.uvu.cpg.project3clintgundersen.R;

/**
 * Created by clint on 10/4/14.
 */
public class Book implements Parcelable
{
    private int id;
    private int imageID;
    private String title;
    private String author;
    private int listRanking;
    private int yearPublished;
    private int rating;
    private String description;
    private boolean haveRead;
    private boolean onPioneerOverdrive;

    public Book()
    {
        id = 0;
        imageID = R.drawable.book;
        title = "Title";
        author = "Author";
        listRanking = 0;
        yearPublished = 0;
        rating = 0;
        description = "Summary";
        haveRead = false;
        onPioneerOverdrive = false;
    }


    public Book(Parcel input)
    {
        readFromParcel(input);
    }

    public Book(String title, String author)
    {
        this.imageID = 0;
        this.title = title;
        this.author = author;
        this.listRanking = 0;
        this.yearPublished = 0;
        this.rating = 0;
        this.description = "Incomplete";
        this.haveRead = false;
        this.onPioneerOverdrive = false;
    }

    public Book(String title, String author, int listRanking, int yearPublished, int rating, String description, boolean haveRead, boolean onPioneerOverdrive)
    {
        this.imageID = 0;
        this.title = title;
        this.author = author;
        this.listRanking = listRanking;
        this.yearPublished = yearPublished;
        checkRating(rating);
        this.description = description;
        this.haveRead = haveRead;
        this.onPioneerOverdrive = onPioneerOverdrive;
    }

    public Book(int id, int imageID, String title, String author, int listRanking, int yearPublished, int rating, String description, boolean haveRead, boolean onPioneerOverdrive)
    {
        this.id = id;
        this.imageID = imageID;
        this.title = title;
        this.author = author;
        this.listRanking = listRanking;
        this.yearPublished = yearPublished;
        checkRating(rating);
        this.description = description;
        this.haveRead = haveRead;
        this.onPioneerOverdrive = onPioneerOverdrive;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>()
    {
        public Book createFromParcel(Parcel input)
        {
            return new Book(input);
        }

        public Book[] newArray(int size)
        {
            return new Book[size];
        }
    };

    public void writeToParcel(Parcel bookParcel, int flags)
    {
        bookParcel.writeInt(id);
        bookParcel.writeInt(imageID);
        bookParcel.writeString(title);
        bookParcel.writeString(author);
        bookParcel.writeInt(listRanking);
        bookParcel.writeInt(yearPublished);
        bookParcel.writeInt(rating);
        bookParcel.writeString(description);
        if(!haveRead)
            bookParcel.writeInt(0);
        else bookParcel.writeInt(1);
        if(!onPioneerOverdrive)
            bookParcel.writeInt(0);
        else bookParcel.writeInt(1);
    }

    public void readFromParcel(Parcel input)
    {
        id = input.readInt();
        imageID = input.readInt();
        title = input.readString();
        author = input.readString();
        listRanking = input.readInt();
        yearPublished = input.readInt();
        rating = input.readInt();
        description = input.readString();
        if (input.readInt() == 0 )
            haveRead = false;
        else haveRead = true;
        if (input.readInt() == 0)
            onPioneerOverdrive = false;
        else onPioneerOverdrive = true;
    }

    public void checkRating(int rating)
    {
        final int MAX_RATING = 10;
        if (rating < 0)
            this.rating = 0;
        else if (rating > MAX_RATING)
            this.rating = MAX_RATING;
        else this.rating = rating;
    }

    public String toString()
    {
        return title + ": " + author;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getImageID()
    {
        return imageID;
    }

    public void setImageID(int imageID)
    {
        this.imageID = imageID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public int getListRanking()
    {
        return listRanking;
    }

    public void setListRanking(int listRanking)
    {
        this.listRanking = listRanking;
    }

    public int getYearPublished()
    {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished)
    {
        this.yearPublished = yearPublished;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getHaveRead()
    {
        if (haveRead)
            return "Yes";
        else return "No";
    }

    public void setHaveRead(String haveRead)
    {
        if (haveRead.equals("Yes"))
            this.haveRead = true;
        else this.haveRead = false;
    }

    public String getOnPioneerOverdrive()
    {
        if(onPioneerOverdrive)
            return "Yes";
        else return "No";
    }

    public void setOnPioneerOverdrive(String onPioneerOverdrive)
    {
        if(onPioneerOverdrive.equals("Yes"))
            this.onPioneerOverdrive = true;
        else this.onPioneerOverdrive = false;
    }

}


