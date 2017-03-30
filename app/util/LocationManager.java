package util;

import GoogleGeocode.GoogleGeocodeResponse;
import GoogleGeocode.Results;
import com.google.inject.Inject;
import play.api.Application;
import play.api.Play;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Controller;
import rx.Observable;
import sdk.AppTreeSource;
import sdk.models.Location;

import java.util.concurrent.CompletionStage;

/**
 * Created by Orozco on 1/19/17.
 */
public class LocationManager {

    private WSClient client;

    private String googleApiKey;

    @Inject
    public LocationManager(WSClient client, String googleApiKey) {
        this.client = client;
        this.googleApiKey = googleApiKey;
    }

    public Observable<GoogleGeocode.Location> getLocationFromAddress(String address) {
        WSRequest request = client.url("https://maps.googleapis.com/maps/api/geocode/json");
        request.setQueryParameter("key", googleApiKey);
        request.setQueryParameter("address", address);
        return Observable.create(subscriber -> {
            subscriber.onStart();
            request.get().thenAccept(response -> {
                try {
                    GoogleGeocodeResponse googleResponse = Json.fromJson(response.asJson(), GoogleGeocodeResponse.class);
                    if ( googleResponse.getResults().length > 0) {
                        GoogleGeocode.Location location = googleResponse.getResults()[0].geometry.location;
                        subscriber.onNext(location);
                    } else {
                        subscriber.onError(new RuntimeException("Location not found from google"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }
            });
        });
    }

}
