package com.ertleast.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "stock_offline";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "mycourses";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String DURATION_COL = "duration";

    // below variable for our course description column.
    private static final String DESCRIPTION_COL = "description";

    // below variable is for our course tracks column.
    private static final String TRACKS_COL = "tracks";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DURATION_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + TRACKS_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewCourse(String courseName, String courseDuration, String courseDescription, String courseTracks) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, courseName);
        values.put(DURATION_COL, courseDuration);
        values.put(DESCRIPTION_COL, courseDescription);
        values.put(TRACKS_COL, courseTracks);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean searchDuplicates(String keyword) {
        SQLiteDatabase db = getReadableDatabase();
        boolean found_status = false;

        Cursor res = db.rawQuery( "select * from " + TABLE_NAME + " where " + NAME_COL +
                                        " like ?", new String[]{"%" + keyword + "%"});


        if ((res != null) && (res.getCount() > 0))
            found_status =false;
        else
            found_status = true;
        db.close();
        return found_status;

    }
    // below is the method for updating our courses
    public boolean updateCourse( String courseName, String courseDescription,
                             String courseTracks, String courseDuration) {
        boolean value_added;
        String originalCourseName = courseName;
        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, courseName);
        values.put(DURATION_COL, courseDuration);
        values.put(DESCRIPTION_COL, courseDescription);
        values.put(TRACKS_COL, courseTracks);
        //Log.i("aniruddha", String.valueOf(values) );
        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        int update_count = db.update(TABLE_NAME, values, "name=?", new String[]{originalCourseName});

        if (update_count == 0){
            addNewCourse(originalCourseName, "courseDuration", courseDescription, courseTracks);
            value_added = true;
        }
        else {
            value_added = false;
        }
        db.close();

        return value_added;
    }


    public ArrayList getAllStocks() {
        //removeDuplicates();

        SQLiteDatabase db = getReadableDatabase();
        //ArrayList<CalculatorsItem> array_list = new ArrayList<CalculatorsItem>();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery( "select * from "+ TABLE_NAME + " order by tracks desc ", null );
        res.moveToFirst();

        while(res.isAfterLast() == false) {

            String display_info = res.getString(res.getColumnIndex("name")) ;
            String location_info = res.getString(res.getColumnIndex("description"));
            String time_info = res.getString(res.getColumnIndex("tracks"));
            //array_list.add(new CalculatorsItem (R.drawable.ic_android,display_info, location_info));

            array_list.add(display_info);
            Log.i("aniruddha",display_info );
            res.moveToNext();
        }
        db.close();
        return array_list;
    }
}
