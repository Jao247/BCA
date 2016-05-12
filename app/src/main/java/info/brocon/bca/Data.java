package info.brocon.bca;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import info.brocon.bca.objects.Member;

public class Data
{
    private Context con;

    public Data (Context con)
    {
        this.con = con;
    }

    public ArrayList<Member> getCommittee()
    {
        ArrayList<Member> mems = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(con.getAssets().open("Committee.csv")));
            String mLine, temp[];
            // s name, s job(s), s email
            while ((mLine = reader.readLine()) != null)
            {
                temp = mLine.split(",");
                mems.add(new Member(temp[0], temp[1], temp[2]));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            if (reader != null)
            {
                try {
                    reader.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return mems;
    }
}
