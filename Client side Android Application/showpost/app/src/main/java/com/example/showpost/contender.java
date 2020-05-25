package com.example.showpost;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Random;

public class contender extends ContentProvider {





    public static final String AUTHORITY="com.example.showpost.mydatabase";
    public static final String PATH="/subreddit";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);
    private DatabaseHelper mydb;




    @Override
    public boolean onCreate() {
        mydb=new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    public void displayNotification(String s1,String s2,int topic_id){
        Intent intent = new Intent(this.getContext(),postListing.class);
        intent.putExtra("text_row",topic_id);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getContext(),100,intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationHelper helper;
        helper=new NotificationHelper(getContext());
        Notification.Builder builder=helper.getchannelnotification(s1, s2,pendingIntent);
        helper.getManager().notify(new Random().nextInt(),builder.build());


    }



    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db=mydb.getWritableDatabase();



        if (contentValues.containsKey("img")){


            long rowid=db.insert("image",null,contentValues);
            db.close();

            if(rowid> 0) {
                Uri articleUri =  Uri.parse("content://" + AUTHORITY + "/" + Long.toString(rowid));
                getContext().getContentResolver().notifyChange(articleUri, null);
                return articleUri;
            }

            throw new IllegalArgumentException("Unknown URI: " + uri);


        }

        else
            {

        String topic_name=contentValues.getAsString("topic_name");

        long topic_id= DatabaseUtils.longForQuery(db,"select topic_id from topics where topic_name"+"=?;", new String[]{ topic_name});
        contentValues.remove("topic_name");
        contentValues.put("topic_id",(int)topic_id);
        long row_id=db.insert("posts",null,contentValues);
        db.close();

        if(row_id> 0) {
            displayNotification("updated","new post added in "+topic_name,(int)topic_id);
            Uri articleUri = ContentUris.withAppendedId(CONTENT_URI, row_id);
            getContext().getContentResolver().notifyChange(articleUri, null);
            return articleUri;
        }

        throw new IllegalArgumentException("Unknown URI: " + uri);
            }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
