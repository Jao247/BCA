package info.brocon.bca.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.brocon.bca.R;
import info.brocon.bca.objects.TimetableItem;

public class TTableAdapter extends ArrayAdapter<TimetableItem>
{
    public TTableAdapter(Context context, ArrayList<TimetableItem> res)
    {
        super(context, R.layout.listview_timetable_row, res);
    }

    @Override
    public View getView(int pos, View v, ViewGroup par)
    {
        LayoutInflater li         = LayoutInflater.from(getContext());
        View           customView = li.inflate(R.layout.listview_timetable_row, par, false);

        TimetableItem item = getItem(pos);
        TextView      name = (TextView) customView.findViewById(R.id.tt_item);
        TextView      date = (TextView) customView.findViewById(R.id.tt_date);
        TextView      loc  = (TextView) customView.findViewById(R.id.tt_loc);

        name.setText(item.getName());
        String s = item.getDayAsString() + " @ " + item.getTimeAsString();
        date.setText(s);

        loc.setText(item.getLoc());

        return customView;
    }
}
