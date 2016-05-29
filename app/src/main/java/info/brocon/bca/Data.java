package info.brocon.bca;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import info.brocon.bca.objects.Member;
import info.brocon.bca.objects.TimetableItem;

public class Data
{
    private Context con;

    public Data(Context con)
    {
        this.con = con;
    }

    public ArrayList<TimetableItem> getTimetableItems()
    {
        ArrayList<TimetableItem> data   = new ArrayList<>();
        BufferedReader           reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(con.getAssets().open("Timetable.csv")));
            String mLine, temp[];
            int nums[] = new int[3];
            // s name, s location, i day, i time, i length
            while ((mLine = reader.readLine()) != null)
            {
                temp = mLine.split(",");
                try
                {
                    nums[0] = Integer.parseInt(temp[2]);
                    nums[1] = Integer.parseInt(temp[3]);
                    nums[2] = Integer.parseInt(temp[4]);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                data.add(new TimetableItem(temp[0], temp[1], nums[0], nums[1], nums[2]));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    public ArrayList<Member> getCommittee()
    {
        ArrayList<Member> mems   = new ArrayList<>();
        BufferedReader    reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(con.getAssets().open("Committee.csv")));
            String mLine, temp[];
            // s name, s job(s), s email
            while ((mLine = reader.readLine()) != null)
            {
                temp = mLine.split(",");
                mems.add(new Member(temp[0], temp[1], temp[2]));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return mems;
    }
}
