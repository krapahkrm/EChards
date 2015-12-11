package ua.pi_13.echards;

/**
 * Created by user on 23.11.2015.
 */
public class ItemChord {
    private String name;
    private String srsImage;
    private String srsMusic;
    private int number;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
    public void setImage(String srsImage)
    {
        this.srsImage = srsImage;
    }
    public String getImage()
    {
        return this.srsImage;
    }
    public void setMusic(String srsMusic)
    {
        this.srsMusic = srsMusic;
    }
    public String getMusic()
    {
        return this.srsMusic;
    }
    public void setNumber(int number)
    {
        this.number = number;
    }
    public int getNumber()
    {
        return this.number;
    }


}
