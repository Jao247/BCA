package info.brocon.bca.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import info.brocon.bca.Main;
import info.brocon.bca.R;

public class Floorplans extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private int floor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floorplans);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView image = (ImageView) findViewById(R.id.map_image);
        try
        {
            InputStream ims = getAssets().open("maps/ground.png");
            Drawable d = Drawable.createFromStream(ims, null);
            image.setImageDrawable(d);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        DrawerLayout          drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void switchToGround(View v)
    {
        if (floor != 0)
        {
            ImageView iv = (ImageView) findViewById(R.id.map_image);
            try
            {
                InputStream is = getAssets().open("maps/ground.png");
                Drawable d = Drawable.createFromStream(is, null);
                iv.setImageDrawable(d);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            floor = 0;
        } else
            Toast.makeText(this, "You are already on the ground floor map.", Toast.LENGTH_LONG).show();
    }

    public void switchToUpper(View v)
    {
        if (floor != 1)
        {
            ImageView iv = (ImageView) findViewById(R.id.map_image);
            try
            {
                InputStream is = getAssets().open("maps/upper.png");
                Drawable d = Drawable.createFromStream(is, null);
                iv.setImageDrawable(d);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            floor = 1;
        } else
            Toast.makeText(this, "You are already on the first floor map.", Toast.LENGTH_LONG).show();
    }

    public void switchToTrade(View v)
    {
        if (floor != 2)
        {
            ImageView iv = (ImageView) findViewById(R.id.map_image);
            try
            {
                InputStream is = getAssets().open("maps/trade_hall.png");
                Drawable d = Drawable.createFromStream(is, null);
                iv.setImageDrawable(d);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            floor = 2;
        } else
            Toast.makeText(this, "You are already on the Trade Hall map.", Toast.LENGTH_LONG).show();
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
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        } else if (id == R.id.nav_timetable)
        {
            Intent i = new Intent(this, Timetable.class);
            startActivity(i);
        } else if (id == R.id.nav_map)
        {
            Toast.makeText(this, R.string.on_map, Toast.LENGTH_LONG);
        } else if (id == R.id.nav_committee)
        {
            Intent i = new Intent(this, Committee.class);
            startActivity(i);
        } else if (id == R.id.nav_facebook)
        {
            startActivity(Main.newFacebookIntent(this.getPackageManager(), "https://www.facebook.com/UL-BroCon-210222299054314/"));
        } else if (id == R.id.nav_about)
        {
            Intent i = new Intent(this, About.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
