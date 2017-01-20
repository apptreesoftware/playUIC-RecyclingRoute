package GoogleGeocode;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Orozco on 1/19/17.
 */
public class Geometry
{
    private Viewport viewport;

    @JsonProperty("location_type")
    public String locationType;

    private Location location;


    @Override
    public String toString()
    {
        return "ClassPojo [viewport = "+viewport+", location_type = "+locationType+", location = "+location+"]";
    }
}