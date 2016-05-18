package info.brocon.bca.objects;


public class TimetableItem
{
    private String day, name, time, loc;
    private boolean marked;

    public TimetableItem(String day, String time, String name, String loc, boolean marked)
    {
        this.day    =    day;
        this.name   =   name;
        this.time   =   time;
        this.loc    =    loc;
        this.marked = marked;
    }

    public String getDay()  { return  day; }
    public String getName() { return name; }
    public String getTime() { return time; }
    public String getLoc()  { return  loc; }

    public boolean getMarked() { return marked; }
}
