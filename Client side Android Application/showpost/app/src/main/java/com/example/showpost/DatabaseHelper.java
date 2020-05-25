package com.example.showpost;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    String DB_PATH ;
    private static String DB_NAME = "mydatabase";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 10);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName()  + "/databases/";
    }


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH+DB_NAME ;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String myPath = DB_PATH+DB_NAME ;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        return myDataBase;
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public class MyException extends Exception {
        String str;
        MyException(String str1) {

            str = str1;
        }
        public String toString()
            {
                return("Excception Occurs:"+str);
            }

   }



    public ArrayList<com.example.showpost.ModelClass> getAllImagesData(int row_id ){

            myDataBase = this.getReadableDatabase();
            ArrayList<com.example.showpost.ModelClass>objectModelClassList = new ArrayList<>();
            Cursor objectCursor = myDataBase.rawQuery("select * from posts where topic_id="+row_id,null);

            if(objectCursor!=null && objectCursor.getCount()>0){
                while (objectCursor.moveToNext()) {
                    int postid = objectCursor.getInt(0);
                    String nameOfImage = objectCursor.getString(2);
                    byte[] imageBytes = objectCursor.getBlob(3);
                    Document document = Jsoup.parse(nameOfImage);
                    Element bold = document.select("p").first();
                    int len=bold.text().length();
                    int mod_len=Math.min(len,100);

                    Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    objectModelClassList.add(new com.example.showpost.ModelClass(postid,bold.text().substring(0,mod_len).concat(".."),objectBitmap));
                }
                return objectModelClassList;
            }
            else{
               return null;
            }

    }

    public ArrayList<com.example.showpost.modelc1> gettopicsData(){

            myDataBase = this.getReadableDatabase();
            ArrayList<com.example.showpost.modelc1>objectModelClassList1 = new ArrayList<>();
            Cursor objectCursor = myDataBase.rawQuery("select * from topics",null);

            if(objectCursor!=null && objectCursor.getCount()!=0){
                while (objectCursor.moveToNext()) {
                    String nameOftopic = objectCursor.getString(1);
                    String topicinfo = objectCursor.getString(2);
                    objectModelClassList1.add(new com.example.showpost.modelc1(nameOftopic,topicinfo));
                }
                return objectModelClassList1;
            }
            else{
                Toast.makeText(myContext,"No value Exists in Database",Toast.LENGTH_SHORT).show();
                return null;
            }

    }

}