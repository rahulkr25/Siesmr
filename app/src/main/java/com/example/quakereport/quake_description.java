package com.example.quakereport;

public class quake_description {
    private String mag="0";
    private String place="Default";
    private long time=0;


    public String getMag()
    {
        return mag;
    }
    public String getPlace()
    {
        return place;
    }
   public long getTime()
    {
        return time;
    }
    public  quake_description(String mag,String place,long time)
    {
        this.mag=mag;
        this.place=place;
        this.time=time;
    }


}
