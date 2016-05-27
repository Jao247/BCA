package info.brocon.bca.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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

        TimetableItem item   = getItem(pos);
        TextView      name   = (TextView) customView.findViewById(R.id.tt_item);
        TextView      date   = (TextView) customView.findViewById(R.id.tt_date);
        CheckBox      marked = (CheckBox) customView.findViewById(R.id.tt_check);

        name.setText(item.getName());
        String s = item.getDay() + " @ " + item.getTime();
        date.setText(s);

        customView.setTag(0, marked);

        marked.setChecked(item.getMarked());

        return customView;
    }
}
