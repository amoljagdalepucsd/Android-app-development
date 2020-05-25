package com.example.addpost;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText post_file_name= findViewById(R.id.edit2);
        Button insert= findViewById(R.id.button);
        final String AUTHORITY = "com.example.showpost.mydatabase";
        final String PATH  = "/subreddit";
        final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_file=post_file_name.getText().toString().trim();
                ContentValues values = new ContentValues();
                try {
                    File file = new File(Environment.getExternalStorageDirectory(),input_file);
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = br.readLine()) != null) {

                        String[] filenames = line.split("\\s");
                        String topic = filenames[0];
                        String post_img_name = filenames[1];
                        String post_name = filenames[2];
                        String img_name = filenames[3];


                        FileInputStream fis;
                        try {
                            File imagefile = new File(Environment.getExternalStorageDirectory() + "/html/", post_img_name);
                            fis = new FileInputStream(imagefile);
                            Bitmap bm = BitmapFactory.decodeStream(fis);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageInByte = baos.toByteArray();
                            values.put("post_img", imageInByte);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), " image file not found", Toast.LENGTH_SHORT).show();
                            }

                       try {
                           File file1 = new File(Environment.getExternalStorageDirectory() + "/html/", post_name);
                           Document document = Jsoup.parse(file1, "UTF-8");

                           Elements imgtag = document.select("img");
                            for (Element element : imgtag) {
                                String path = element.attr("src");

                                FileInputStream fis1 ;
                                try {
                                    File imagefile1 = new File(Environment.getExternalStorageDirectory() + "/html/", path);
                                    fis1 = new FileInputStream(imagefile1);
                                    Bitmap bm1 = BitmapFactory.decodeStream(fis1);
                                    ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                                    bm1.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
                                    byte[] imageInByte1 = baos1.toByteArray();
                                    ContentValues values1 = new ContentValues();
                                    values1.put("img", imageInByte1);
                                    Uri uri = getContentResolver().insert(CONTENT_URI, values1);
                                    String row;

                                    if (uri != null) {
                                        row = uri.getLastPathSegment();
                                        element.attr("src", row);
                                    }


                                }

                                catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"file not found", Toast.LENGTH_SHORT).show();


                                }

                            }
                            values.put("post_text",document.toString());
                        }

                    catch(IOException e) {
                        Toast.makeText(getApplicationContext(),"post html file not found", Toast.LENGTH_SHORT).show();
                       }


                        values.put("topic_name", topic);
                        getContentResolver().insert(CONTENT_URI, values);
                        Toast.makeText(getApplicationContext(),"post inserted successfully"+post_name, Toast.LENGTH_SHORT).show();


                    }
                    br.close();
                }

                catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"file not found", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
