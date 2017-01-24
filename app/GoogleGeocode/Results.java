package GoogleGeocode;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Orozco on 1/19/17.
 */
public class Results
{
    @JsonProperty("partial_match")
    public String partialMatch;

    @JsonProperty("place_id")
    public String placeID;

    @JsonProperty("address_components")
    public Address_components[] addressComponents;

    @JsonProperty("formatted_address")
    public String formattedAddress;

    public String[] types;

    public Geometry geometry;

    @Override
    public String toString()
    {
        return "ClassPojo [partial_match = "+partialMatch+", place_id = "+placeID+", address_components = "+addressComponents+", formattedAddress = "+ formattedAddress +", types = "+types+", geometry = "+geometry+"]";
    }
}
