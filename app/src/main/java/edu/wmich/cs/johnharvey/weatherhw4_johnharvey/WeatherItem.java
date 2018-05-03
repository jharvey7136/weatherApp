package edu.wmich.cs.johnharvey.weatherhw4_johnharvey;

/**
 * Created by John on 6/18/2017.
 */

public class WeatherItem {
    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String getmTemp() {
        return mTemp;
    }

    public void setmTemp(String mTemp) {
        this.mTemp = mTemp;
    }



    private String mCity;
    private String mIcon;
    private String mTemp;


    @Override
    public String toString() {
        return mTemp;
    }


}
