package info.brocon.bca.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.brocon.bca.R;
import info.brocon.bca.objects.Member;

public class CommAdapter extends ArrayAdapter<Member>
{
    public CommAdapter(Context con, ArrayList<Member> mems)
    {
        super (con, R.layout.listview_committee_row, mems);
    }

    @Override
    public View getView(int pos, View v, ViewGroup par)
    {
        LayoutInflater li = LayoutInflater.from(getContext());
        View customView = li.inflate(R.layout.listview_committee_row, par, false);

        Member item = getItem(pos);
        TextView name = (TextView) customView.findViewById(R.id.comm_n);
        TextView email = (TextView) customView.findViewById(R.id.comm_e);
        TextView job = (TextView) customView.findViewById(R.id.comm_j);

        name.setText(item.getName());
        email.setText(item.getEmail());
        job.setText(item.getJob());

        return customView;
    }
}
