package com.example.quakereport;

public class quake_description {
    private String mag="0";
    private String place="Default";
    private String date="11-11-11";


    public String getMag()
    {
        return mag;
    }
    public String getPlace()
    {
        return place;
    }
   public String getDate()
    {
        return date;
    }
    public  quake_description(String mag,String place,String date)
    {
        this.mag=mag;
        this.place=place;
        this.date=date;
    }


}
