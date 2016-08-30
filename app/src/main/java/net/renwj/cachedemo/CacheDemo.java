package net.renwj.cachedemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CacheDemo extends Activity implements View.OnClickListener {
    Button downButton;
    Button cacheButon;
    DownThread downThread = null;
    String pictureurl;
    Bitmap mediaware;
    ImageView imageview;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_demo);

        downButton = (Button) findViewById(R.id.downBtn);
        cacheButon = (Button) findViewById(R.id.cacheBtn);
        imageview = (ImageView) findViewById(R.id.netpic);
        downButton.setOnClickListener(this);
        cacheButon.setOnClickListener(this);
        pictureurl = "http://i.guancha.cn/news/2016/08/25/20160825124040137.jpg";
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.downBtn) {
            if (downThread == null) {
                downThread = new DownThread();
                downThread.execute(pictureurl);
                System.out.println("path=" + Environment.getExternalStorageDirectory());
            }
            if (v.getId() == R.id.cacheBtn) {
                SaveBitmap saveBitmap = new SaveBitmap();
                saveBitmap.saveImage(mediaware);
                Log.i("renwj", "path=" + Environment.getExternalStorageDirectory());
            }
        }
    }


    class DownThread extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream fi = connection.getInputStream();
                mediaware = BitmapFactory.decodeStream(fi);
                fi.close();
                File dir = new File(Environment.getExternalStorageDirectory() + "/zheshidir/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File caiyingwen = new File(dir, "caiyingwen.jpg");
                FileOutputStream fo = new FileOutputStream(caiyingwen);
                mediaware.compress(Bitmap.CompressFormat.JPEG, 90, fo);
                fo.flush();
                fo.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            imageview.setImageBitmap(mediaware);
        }
    }

    @Override
    protected void onStop() {
        downThread.cancel(true);
        super.onStop();
    }
}
