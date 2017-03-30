package GoogleGeocode;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Orozco on 1/19/17.
 */
public class Southwest
{
    @JsonProperty("lng")
    public String longitude;

    @JsonProperty("lat")
    public String lattitude;

    @Override
    public String toString()
    {
        return "ClassPojo [lng = "+longitude+", lat = "+lattitude+"]";
    }
}
