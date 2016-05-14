package info.brocon.bca;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import info.brocon.bca.activities.Committee;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout          drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        startTimer();
    }

    public void startTimer()
    {
        final int SECONDS_IN_A_DAY = 24 * 60 * 60;
        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(new Date(0));
        thatDay.set(Calendar.DAY_OF_MONTH,8);
        thatDay.set(Calendar.MONTH,6);
        thatDay.set(Calendar.YEAR, 2016);

        Calendar today = Calendar.getInstance();
        long diff =  thatDay.getTimeInMillis() - today.getTimeInMillis();
        long diffSec = diff / 1000;

        long weeks = diffSec / (SECONDS_IN_A_DAY * 7);
        long days = (diffSec / SECONDS_IN_A_DAY) / 7;
        long secondsDay = diffSec % SECONDS_IN_A_DAY;
        long seconds = secondsDay % 60;
        long minutes = (secondsDay / 60) % 60;
        long hours = (secondsDay / 3600);

        TextView tv = (TextView)  findViewById(R.id.timerW);
        if (weeks % 10 < 10) tv.setText("0" + weeks);
        else tv.setText("" + weeks);
        tv = (TextView) findViewById(R.id.timerD);
        tv.setText("0" + days);
        tv = (TextView) findViewById(R.id.timerH);
        if (hours % 10 < 10) tv.setText("0" + hours);
        else tv.setText("" + hours);
        tv = (TextView) findViewById(R.id.timerM);
        if (minutes % 10 < 10) tv.setText("0" + minutes);
        else tv.setText("" + minutes);
        tv = (TextView) findViewById(R.id.timerS);
        if (seconds % 10 < 10) tv.setText("0" + seconds);
        else tv.setText("" + seconds);
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

    @SuppressWarnings ("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            Toast.makeText(this, R.string.on_home, Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_timetable)
        {

        } else if (id == R.id.nav_guests)
        {

        } else if (id == R.id.nav_blog)
        {

        } else if (id == R.id.nav_committee)
        {
            Intent i = new Intent(this, Committee.class);
            startActivity(i);
        } else if (id == R.id.nav_facebook)
        {
            startActivity(newFacebookIntent(this.getPackageManager(), "https://www.facebook.com/UL-BroCon-210222299054314/"));
        } else if (id == R.id.nav_about)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goTimetable(View view)
    {

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
}
