package info.brocon.bca;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import info.brocon.bca.activities.About;
import info.brocon.bca.activities.Floorplans;
import info.brocon.bca.activities.Timetable;
import info.brocon.bca.helper.BackgroundHelper;

public class Main extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTimer();
        Thread t = new Thread()
        {

            @Override
            public void run()
            {
                try
                {
                    while (!isInterrupted())
                    {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                startTimer();
                            }
                        });
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }

    public void startTimer()
    {
        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(new Date(0));
        thatDay.set(Calendar.DAY_OF_MONTH, 8);
        thatDay.set(Calendar.MONTH, Calendar.JULY);
        thatDay.set(Calendar.YEAR, 2016);
        thatDay.set(Calendar.HOUR_OF_DAY, 0);
        thatDay.set(Calendar.MINUTE, 0);
        thatDay.set(Calendar.SECOND, 0);

        Calendar today = Calendar.getInstance();
        long     diff  = thatDay.getTimeInMillis() - today.getTimeInMillis();
        long     thing = diff / 1000;

        long w = thing / (24 * 60 * 60 * 7);
        thing %= (24 * 60 * 60 * 7);
        long d = thing / (24 * 60 * 60);
        thing %= (24 * 60 * 60);
        long h = thing / (60 * 60);
        thing %= (60 * 60);
        long m = thing / 60;
        long s = thing % 60;

        //String thisW = "w:" + w + ",d:" + d + ",h:" + h + ",m:" + m + ",s:" + s;
        //Toast.makeText(this, thisW, Toast.LENGTH_SHORT).show();

        TextView tv;
        if (w >= 10)
        {
            tv = (TextView) findViewById(R.id.timerWL);
            tv.setText("" + (w / 10));
            tv = (TextView) findViewById(R.id.timerWR);
            tv.setText("" + (w % 10));
        } else
        {
            tv = (TextView) findViewById(R.id.timerWR);
            tv.setText("" + w);
        }
        tv = (TextView) findViewById(R.id.timerDR);
        tv.setText("" + d);
        tv = (TextView) findViewById(R.id.timerHL);
        tv.setText("" + (h / 10));
        tv = (TextView) findViewById(R.id.timerHR);
        tv.setText("" + (h % 10));
        tv = (TextView) findViewById(R.id.timerML);
        tv.setText("" + (m / 10));
        tv = (TextView) findViewById(R.id.timerMR);
        tv.setText("" + (m % 10));
        tv = (TextView) findViewById(R.id.timerSL);
        tv.setText("" + (s / 10));
        tv = (TextView) findViewById(R.id.timerSR);
        tv.setText("" + (s % 10));
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    // Provided from StackOverflow's Jared Rummler (http://stackoverflow.com/users/1048340/jared-rummler)
    public static Intent newFacebookIntent(PackageManager pm, String url)
    {
        Uri uri = Uri.parse(url);
        try
        {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled)
            {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored)
        {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public void goTimetable(View view)
    {
        Intent i = new Intent(this, Timetable.class);
        startActivity(i);
    }

    public void goMap(View view)
    {
        Intent i = new Intent(this, Floorplans.class);
        startActivity(i);
    }

    public void goAbout(View view)
    {
        Intent i = new Intent(this, About.class);
        startActivity(i);
    }

    private Handler chuckFinishedHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    Toast.makeText(Main.this, "Wallpaper set", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(Main.this, "Uh oh, can't do that right now", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    public void getWallpaper(View v)
    {
        final Dialog           dialog = new Dialog(this);
        final BackgroundHelper bgHelp = new BackgroundHelper(this, chuckFinishedHandler);
        dialog.setContentView(R.layout.dialog_wallpaper);
        dialog.setTitle("Set this as your wallpaper?");

        ImageView image = (ImageView) dialog.findViewById(R.id.bgPreview);
        try
        {
            // get input stream
            InputStream ims = getAssets().open("bg.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            image.setImageDrawable(d);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Button yes = (Button) dialog.findViewById(R.id.yButton);
        Button no  = (Button) dialog.findViewById(R.id.nButton);
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bgHelp.setResourceAsWallpaper("bg.png");
                dialog.cancel();
            }
        });
        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void goFacebook(View view)
    {
        startActivity(newFacebookIntent(this.getPackageManager(), "https://www.facebook.com/UL-BroCon-210222299054314/"));
    }
}
