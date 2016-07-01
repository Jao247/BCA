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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import info.brocon.bca.Data;
import info.brocon.bca.Main;
import info.brocon.bca.R;
import info.brocon.bca.adapters.CommAdapter;

public class Committee extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateListData();

        TextView tv = (TextView) findViewById(R.id.comm_intro_msg);
        tv.setTextColor(getResources().getColor(R.color.textDark));

        DrawerLayout          drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings ("ALL")
    private void updateListData()
    {
        Data        data = new Data(this);
        ListAdapter la   = new CommAdapter(this, data.getCommittee());
        ListView    lv   = (ListView) findViewById(R.id.comm_list);
        lv.setAdapter(la);
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
        }else if (id == R.id.nav_committee)
        {
            Toast.makeText(this, R.string.on_com, Toast.LENGTH_LONG).show();
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