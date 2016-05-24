package info.brocon.bca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import info.brocon.bca.Data;
import info.brocon.bca.Main;
import info.brocon.bca.R;
import info.brocon.bca.adapters.CommitteeAdapter;
import info.brocon.bca.objects.Member;

public class Blank extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try
        {
            ListView lv = (ListView) findViewById(R.id.blankList);

            Data d = new Data(this);
            ArrayList<Member> comm = d.getCommittee();

            String names[] = new String[comm.size()], emails[] = new String[comm.size()], jobs[] = new String[comm.size()];

            for (int i = 0; i < comm.size(); i++)
            {
                names[i] = comm.get(i).getName();
                emails[i] = comm.get(i).getEmail();
                jobs[i] = comm.get(i).getJob();
            }

            CommitteeAdapter ca = new CommitteeAdapter(this, names, emails, jobs);
            lv.setAdapter(ca);
        }catch(NullPointerException e)
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
            startActivity(Main.newFacebookIntent(this.getPackageManager(), "https://www.facebook.com/UL-BroCon-210222299054314/"));
        } else if (id == R.id.nav_about)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
