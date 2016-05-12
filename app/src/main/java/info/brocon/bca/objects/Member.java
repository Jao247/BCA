package info.brocon.bca.objects;

public class Member
{
    private String _name, _job, _email;

    public Member(String name, String job, String email)
    {
        this._name  =  name;
        this._job   =   job;
        this._email = email;
    }

    public String  getName() { return  _name; }
    public String   getJob() { return   _job; }
    public String getEmail() { return _email; }
}
