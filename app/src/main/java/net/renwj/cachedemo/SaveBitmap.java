package net.renwj.cachedemo;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Y_a on 2016/8/26.
 */
public class SaveBitmap {
    public SaveBitmap() {

    }

    public Boolean saveImage(Bitmap bitmap) {

        String filename=System.currentTimeMillis()+".jpg";
        String path=Environment.getExternalStorageDirectory()+"/picFiles/";
        File picFile=new File(path,filename);
        try {
            FileOutputStream fo=new FileOutputStream(picFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fo);
            fo.flush();
            fo.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
