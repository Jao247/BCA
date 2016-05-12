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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.brocon.bca.Data;
import info.brocon.bca.Main;
import info.brocon.bca.R;
import info.brocon.bca.objects.Member;

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

        DrawerLayout          drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("ALL")
    private void updateListData()
    {
        Data              data = new Data(this);
        ArrayList<Member> mems = data.getCommittee();
        /* Con Director(CD), Vice Director(VD), Secretary(SE)
        *  Treasurer(TR), Staff Officer(SO), Public Relations(PR)
        *  Advertising(APR), Panels Officer(PO), Health and Safety(HS)
        *  Screenings(SCO), Card Games (CGO), Consoles (CO), RPG
        */
        int names[] = {R.id.CD_n, R.id.VD_n, R.id.SE_n, R.id.TR_n, R.id.SO_n, R.id.PR_n, R.id.APR_n, R.id.PO_n, R.id.HS_n, R.id.SCO_n, R.id.CGO_n, R.id.CO_n, R.id.RPG_n};
        int jobs[]  = {R.id.CD_j, R.id.VD_j, R.id.SE_j, R.id.TR_j, R.id.SO_j, R.id.PR_j, R.id.APR_j, R.id.PO_j, R.id.HS_j, R.id.SCO_j, R.id.CGO_j, R.id.CO_j, R.id.RPG_j};
        int email[] = {R.id.CD_e, R.id.VD_e, R.id.SE_e, R.id.TR_e, R.id.SO_e, R.id.PR_e, R.id.APR_e, R.id.PO_e, R.id.HS_e, R.id.SCO_e, R.id.CGO_e, R.id.CO_e, R.id.RPG_e};

        TextView tv = (TextView) findViewById(R.id.com_intro_msg);
        tv.setTextColor(getResources().getColor(R.color.textDark));

        for (int i = 0; i < mems.size(); i++)
        {
            tv = (TextView) findViewById(names[i]);
            tv.setTextColor(getResources().getColor(R.color.textDark));
            tv.setText("" + mems.get(i).getName());
            tv = (TextView) findViewById(jobs[i]);
            tv.setTextColor(getResources().getColor(R.color.textMid));
            tv.setText("" + mems.get(i).getJob());
            tv = (TextView) findViewById(email[i]);
            tv.setTextColor(getResources().getColor(R.color.textLight));
            tv.setText("" + mems.get(i).getEmail());
        }
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
            Toast.makeText(this, R.string.on_com, Toast.LENGTH_LONG).show();
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