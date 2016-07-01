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
import android.widget.EditText;
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

        final ArrayList<TimetableItem> list = adaptList(d.getTimetableItems());
        updateList(list);

        DrawerLayout          drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void updateList(ArrayList<TimetableItem> array)
    {
        final ArrayList<TimetableItem> list = array;
        final Context                  con  = this;
        ListAdapter                    la;
        if (list != null)
        {
            if (list.isEmpty())
            {
                Toast.makeText(this, "Thanks for coming to the event!", Toast.LENGTH_LONG).show();
            } else
            {
                la = new TTableAdapter(this, list);
                ListView lv = (ListView) findViewById(R.id.ttable_list);
                lv.setAdapter(la);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> par, final View v, int pos, long id)
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);

                        final int position = pos;

                        alertDialogBuilder.setTitle("Add event");
                        alertDialogBuilder.setMessage("Do you want to add this event to your Phone's Calendar?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                createCalendarEvent(list, position);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.cancel();
                            }
                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                });
            }
        }

    }

    public void searchTimetable(View v)
    {
        Data                     d          = new Data(this);
        ArrayList<TimetableItem> list       = adaptList(d.getTimetableItems());
        EditText                 et         = (EditText) findViewById(R.id.searchField);
        String                   searchTerm = String.valueOf(et.getText()).toLowerCase();

        boolean foundInTags    = false, foundInDays = false;
        String  tags[], days[] = {"friday", "saturday", "sunday"};

        if (!searchTerm.equals("") || !searchTerm.equals(" "))
        {
            for (int i = (list.size() - 1); i >= 0; i--)
            {
                tags = list.get(i).getTags();
                if (tags.length != 0)
                {
                    for (int j = 0; j < tags.length && !foundInTags; j++)
                    {
                        if (tags[j].toLowerCase().contains(searchTerm)) foundInTags = true;
                    }
                    if (!foundInTags)
                    {
                        if (!list.get(i).getName().toLowerCase().contains(searchTerm))
                        {
                            if (!list.get(i).getLoc().toLowerCase().contains(searchTerm))
                            {
                                for (int j = 0; j == days.length && !foundInDays; j++)
                                {
                                    if (list.get(i).getDayAsString().toLowerCase().contains(days[j]) && days[j].contains(searchTerm)) foundInDays = true;
                                }
                                if (!foundInDays)
                                {
                                    list.remove(i);
                                } else foundInDays = false;
                            }
                        }
                    } else foundInTags = false;
                } else
                {
                    Toast.makeText(this, "Tags are empty", Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(this, "Done Search.", Toast.LENGTH_LONG).show();
            updateList(list);
        } else
        {
            Toast.makeText(this, "Please enter a non-blank search term.", Toast.LENGTH_LONG).show();
        }
    }

    public void refreshList(View v)
    {
        Data d = new Data(this);
        updateList(adaptList(d.getTimetableItems()));
    }

    public ArrayList<TimetableItem> adaptList(ArrayList<TimetableItem> array)
    {
        Calendar cal = Calendar.getInstance();
        int      day = cal.get(Calendar.DAY_OF_MONTH);
        int      searchVal;
        long     baseTime;
        switch (day)
        {
            case 8:
                searchVal = 0;
                cal.set(2016, Calendar.JULY, 8, 0, 0, 0);
                baseTime = cal.getTimeInMillis();
                break;
            case 9:
                searchVal = 1;
                cal.set(2016, Calendar.JULY, 9, 0, 0, 0);
                baseTime = cal.getTimeInMillis();
                break;
            case 10:
                searchVal = 2;
                cal.set(2016, Calendar.JULY, 10, 0, 0, 0);
                baseTime = cal.getTimeInMillis();
                break;
            default:
                return array;
        }
        cal = Calendar.getInstance();

        Toast.makeText(this, "Array size: " + array.size(), Toast.LENGTH_LONG).show();
        int i = array.size() - 1;
        while (i >= 0)
        {
            Toast.makeText(this, "for index: " + i, Toast.LENGTH_SHORT).show();
            if (array.get(i).getDay() == searchVal)
            {
                if ((baseTime + array.get(i).getTime() * 60 * 1000) <= cal.getTimeInMillis())
                {
                    array.remove(i);
                }
            }
            i--;
        }
        return array;
    }

    public void createCalendarEvent(ArrayList<TimetableItem> list, int pos)
    {
        Calendar          cal = Calendar.getInstance();
        GregorianCalendar gc  = new GregorianCalendar();
        int               day = list.get(pos).getDay();
        switch (day)
        {
            case 0:
                cal.set(2016, Calendar.JULY, 8, 0, 0, 0);
                break;
            case 1:
                cal.set(2016, Calendar.JULY, 9, 0, 0, 0);
                break;
            case 2:
                cal.set(2016, Calendar.JULY, 10, 0, 0, 0);
                break;
            default:
                cal = Calendar.getInstance();
        }

        long startTime = cal.getTimeInMillis() + (list.get(pos).getTime() * 60 * 1000);
        long endTime   = startTime + (list.get(pos).getTime() * 60 * 1000);

        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        i.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        i.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

        i.putExtra(CalendarContract.Events.TITLE, list.get(pos).getName());
        i.putExtra(CalendarContract.Events.DESCRIPTION, "This is a sample description");
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
