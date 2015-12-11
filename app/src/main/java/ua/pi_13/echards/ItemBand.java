package ua.pi_13.echards;

/**
 * Created by user on 11.12.2015.
 */
public class ItemBand {
    private String name;
    private int counts;
    private Long _ID;
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setCounts(int counts)
    {
        this.counts = counts;
    }
    public int getCounts()
    {
        return this.counts;
    }

    public void set_ID(Long id) {this._ID = id;}

    public Long get_ID() {return this._ID;}

}
