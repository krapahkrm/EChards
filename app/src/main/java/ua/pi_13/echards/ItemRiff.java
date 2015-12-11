package ua.pi_13.echards;

/**
 * Created by user on 10.12.2015.
 */
public class ItemRiff {
    private String name;
    private String parseLine;
    private int number;
    private Long ID;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setParseLine(String parseLine)
    {
        this.parseLine = parseLine;
    }

    public String getParseLine()
    {
        return this.parseLine;
    }
    public void setNumber(int number)
    {
        this.number = number;
    }
    public int getNumber()
    {
        return this.number;
    }

    public void setID(Long ID)
    {
        this.ID = ID;
    }

    public Long getID()
    {
        return this.ID;
    }
}
