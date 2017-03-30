package GoogleGeocode;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Orozco on 1/19/17.
 */
public class Address_components
{
    @JsonProperty("long_name")
    public String longName;

    private String[] types;

    @JsonProperty("short_name")
    private String shortName;

    @Override
    public String toString()
    {
        return "ClassPojo [long_name = "+longName+", types = "+types+", short_name = "+shortName+"]";
    }
}
