package com.example.quakereport;

public class quake_description {
    private String mag="0";
    private String place="Default";
    private long time=0;
    private String url="";

    public  String getMag()
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
    public String getUrl(){return url;}
    public  quake_description(String mag,String place,long time,String url)
    {
        this.mag=mag;
        this.place=place;
        this.time=time;
        this.url=url;
    }



}
