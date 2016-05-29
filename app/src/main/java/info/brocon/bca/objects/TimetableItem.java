package info.brocon.bca.objects;

public class TimetableItem
{
    private String name, loc;
    private int day, time, length;

    public TimetableItem(String name, String loc, int day, int time, int length)
    {
        this.day = day;
        this.name = name;
        this.time = time;
        this.loc = loc;
        this.length = length;
    }

    public int getDay() { return day; }

    public int getTime() { return time; }

    public int getLength() { return length; }

    public String getDayAsString()
    {
        switch (day)
        {
            case 0:
                return "Friday";
            case 1:
                return "Saturday";
            case 2:
                return "Sunday";
            default:
                return "day";
        }
    }

    public String getTimeAsString()
    {
        String returnMe;

        if (time / 60 < 10) returnMe = "0" + time / 60 + ":";
        else returnMe = "" + time / 60 + ":";
        if (time % 60 < 10) returnMe += "0" + time % 60;
        else returnMe += "" + time % 60;

        return returnMe;
    }

    public String getName() { return name; }

    public String getLoc() { return loc; }
}
