package GoogleGeocode;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Orozco on 1/19/17.
 */
public class Location
{
    @JsonProperty("lng")
    public double longitude;

    @JsonProperty("lat")
    public double lattitude;

    @Override
    public String toString()
    {
        return "ClassPojo [longitude = "+longitude+", lattitude = "+lattitude+"]";
    }
}
