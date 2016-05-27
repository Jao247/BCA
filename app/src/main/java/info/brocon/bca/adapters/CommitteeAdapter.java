package info.brocon.bca.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import info.brocon.bca.R;

public class CommitteeAdapter extends BaseAdapter
{
    private Context        con;
    private String[]       names;
    private String[]       email;
    private String[]       jobs;
    private LayoutInflater inflater;

    public CommitteeAdapter(Context con, String[] names, String[] emails, String[] jobs)
    {
        this.con      = con;
        this.names    = names;
        this.email    = emails;
        this.jobs     = jobs;
        this.inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return names.length; }

    @Override
    public Object getItem(int position) { return names[position]; }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int pos, View v, ViewGroup par)
    {
        if (v == null)
        {
            v = inflater.inflate(R.layout.listview_committee_row, par, false);
        }

        TextView name  = (TextView) v.findViewById(R.id.comm_n);
        TextView emailV = (TextView) v.findViewById(R.id.comm_e);
        TextView job   = (TextView) v.findViewById(R.id.comm_j);

        name.setText(names[pos]);
        emailV.setText(email[pos]);
        job.setText(jobs[pos]);

        return null;
    }
}
