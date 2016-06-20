package info.brocon.bca.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import info.brocon.bca.Data;
import info.brocon.bca.Main;
import info.brocon.bca.R;
import info.brocon.bca.adapters.TTableAdapter;
import info.brocon.bca.objects.TimetableItem;

public class Timetable extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private Data d = new Data(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;

        final ArrayList<TimetableItem> list = d.getTimetableItems();
        ListAdapter la = new TTableAdapter(this, list);
        ListView    lv = (ListView) findViewById(R.id.ttable_list);
        lv.setAdapter(la);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> par, final View v, int pos, long id)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                final int position = pos;

                // set title
                alertDialogBuilder.setTitle("Add event");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to add this event to your Phone's Calendar?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, Create the event in the Calendar
                                createCalendarEvent(list, position);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        DrawerLayout          drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void createCalendarEvent(ArrayList<TimetableItem> list, int pos)
    {
        Calendar                  cal = Calendar.getInstance();
        GregorianCalendar gc = new GregorianCalendar();
        int day = list.get(pos).getDay();
        switch (day)
        {
            case 0:
                cal.set(2016,Calendar.JULY,8,0,0,0);
                break;
            case 1:
                cal.set(2016,Calendar.JULY,9,0,0,0);
                break;
            case 2:
                cal.set(2016,Calendar.JULY,10,0,0,0);
                break;
            default:
                cal = Calendar.getInstance();
        }

        long startTime = cal.getTimeInMillis() + (list.get(pos).getTime() * 60 * 1000);
        long endTime = startTime + (list.get(pos).getTime() * 60 * 1000);

        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        i.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        i.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

        i.putExtra(CalendarContract.Events.TITLE, list.get(pos).getName());
        i.putExtra(CalendarContract.Events.DESCRIPTION,  "This is a sample description");
        i.putExtra(CalendarContract.Events.EVENT_LOCATION, list.get(pos).getLoc());

        startActivity(i);
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
            Toast.makeText(this, R.string.on_timetable, Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_guests)
        {
            Intent i = new Intent(this, Blank.class);
            startActivity(i);
        } else if (id == R.id.nav_blog)
        {
            Intent i = new Intent(this, Blank.class);
            startActivity(i);
        } else if (id == R.id.nav_committee)
        {
            Intent i = new Intent(this, Committee.class);
            startActivity(i);
        } else if (id == R.id.nav_facebook)
        {
            startActivity(Main.newFacebookIntent(this.getPackageManager(), "https://www.facebook.com/UL-BroCon-210222299054314/"));
        } else if (id == R.id.nav_about)
        {
            Intent i = new Intent(this, Blank.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
