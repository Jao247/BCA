package info.brocon.bca.objects;

public class TimetableItem
{
    private String name, loc, tags[];
    private int day, time;

    public TimetableItem(String name, String loc, int day, int time, String  ... tags)
    {
        this.day = day;
        this.name = name;
        this.time = time;
        this.loc = loc;
        this.tags = tags;
    }

    public int getDay() { return day; }

    public int getTime() { return time; }

    public String[] getTags() { return tags; }

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
