package info.brocon.bca.helper;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
public class BackgroundHelper
{
    public static final int SUCCESS = 0;
    public static final int FAIL    = 0;

    private final Context          con;
    private final Handler          callback;
    private       WallpaperManager manager;

    public BackgroundHelper(Context con, Handler callback)
    {
        this.con = con;
        this.callback = callback;
        this.manager = (WallpaperManager) con.getSystemService(Context.WALLPAPER_SERVICE);
    }

    public void setResourceAsWallpaper(final String resourceName)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    manager.setBitmap(getImage(resourceName));
                    callback.sendEmptyMessage(SUCCESS);
                } catch (IOException e)
                {
                    Log.e("Main", "Cant set wallpaper");
                    callback.sendEmptyMessage(FAIL);
                }
            }
        }.start();
    }

    private Bitmap getImage(String resourceName)
    {
        Bitmap bitmap = null;
        try
        {
            bitmap = BitmapFactory.decodeStream(con.getAssets().open(resourceName));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if (bitmap != null)
        {
            return bitmap;
        }else return null;
    }
}
