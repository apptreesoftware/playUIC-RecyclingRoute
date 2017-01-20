package data;

import GoogleGeocode.Location;
import com.avaje.ebean.Model;
import model.Route;
import model.RouteStop;
import play.Logger;
import rx.Observable;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.RecordActionResponse;
import sdk.datasources.base.CacheableList;
import sdk.datasources.rx.DataSource;
import sdk.list.List;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;
import util.LocationManager;

import java.util.Collection;
import java.util.Set;

/**
 * Created by eddie on 11/21/16.
 */
public class RouteDataSource implements DataSource, CacheableList {
    @Override
    public String getServiceDescription() {
        return "Routes";
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getAttributes() {
        return Route.getServiceAttributes();
    }

    @Override
    public Observable<DataSet> getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        DataSet dataSet = newEmptyDataSet();
        Route.find.all().forEach(route -> route.copyInto(dataSet.addNewDataSetItem()));
        return Observable.just(dataSet);
    }

    @Override
    public Observable<DataSetItem> getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        Route route = Route.find.byId(Integer.parseInt(id));
        if (route == null) {
            throw new RuntimeException("Route not found");
        }
        return Observable.just(route.copyInto(new DataSetItem(getAttributes())));
    }

    @Override
    public Observable<RecordActionResponse> updateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        Route route = Route.find.byId(Integer.parseInt(dataSetItem.getPrimaryKey()));
        if ( route == null ) throw new RuntimeException("Route not found");
        route.copyFrom(dataSetItem);
        return Observable.just(route)
                .flatMap(route1 -> Observable.from(route1.getStops()))
                .flatMap(this::hydrateRouteStopIfNecessary)
                .toList()
                .map(list -> route)
                .map(updatedRoute -> {
                    DataSetItem createdItem = new DataSetItem(getAttributes());
                    updatedRoute.copyInto(createdItem);
                    return createdItem;
                })
                .map(createdItem -> new RecordActionResponse.Builder()
                        .withMessage("Route successfully updated.")
                        .withRecord(createdItem)
                        .build());
    }

    public Observable<RouteStop> hydrateRouteStopIfNecessary(RouteStop routeStop) {
        if (routeStop.hasValidLocation()) {
            return Observable.just(routeStop);
        } else {
            return hydrateLocationForRouteStop(routeStop)
                    .doOnNext(updatedRouteStop -> {
                        if ( updatedRouteStop.hasValidLocation() ) {
                            updatedRouteStop.update();
                        }
                    });

        }
    }

    @Override
        public Observable<RecordActionResponse> createRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
            Route route = new Route();
            route.copyFrom(dataSetItem);
            return Observable.just(route)
                    .flatMap(route1 -> Observable.from(route1.getStops()))
                    .flatMap(this::hydrateRouteStopIfNecessary)
                    .toList()
                    .map(list -> route)
                    .map(updatedRoute -> {
                        DataSetItem createdItem = new DataSetItem(getAttributes());
                        updatedRoute.copyInto(createdItem);
                        return createdItem;
                    })
                    .map(createdItem -> new RecordActionResponse.Builder()
                            .withMessage("Route successfully created.")
                            .withRecord(createdItem)
                            .build());
        }

    @Override
    public List getList(AuthenticationInfo authenticationInfo, Parameters params) {
        List list = new List();
        Route.find.all()
                .forEach(route -> list.addListItem(route.toListItem()));
        return list;
    }

    @Override
    public boolean isListContentGlobal() {
        return true;
    }

    @Override
    public Set<ListServiceConfigurationAttribute> getListServiceAttributes() {
        return Route.getListServiceAttributes();
    }

    @Override
    public String getServiceName() {
        return "Route List";
    }

    private Observable<RouteStop> hydrateLocationForRouteStop(RouteStop routeStop) {
        LocationManager locationManager = new LocationManager(getWSClient());
        return locationManager.getLocationFromAddress(routeStop.getAddress())
                .doOnNext(location -> {
                    routeStop.setLatitude(location.lattitude);
                    routeStop.setLongitude(location.longitude);
                })
                .map(location -> routeStop)
                .onErrorReturn(throwable -> routeStop);
    }
}
