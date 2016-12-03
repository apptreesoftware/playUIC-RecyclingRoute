package data;

import model.*;
import org.joda.time.DateTime;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.base.InspectionSource;
import sdk.list.ListItem;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by matthew on 11/28/16.
 */
public class RouteInspectionDataSource implements InspectionSource {
    @Override
    public Collection<ServiceConfigurationAttribute> getInspectionItemAttributes() {
        return RouteCollectionAction.getServiceAttributes();
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getInspectionSearchAttributes() {
        HashSet<ServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(SEARCH_ROUTE).name("Route")
                .asListItem(Route.getListServiceAttributes())
                .canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(SEARCH_VEHICLE).name("Vehicle")
                .asListItem(Vehicle.getListAttributes())
                .canCreate()
                .canUpdate()
                .build());

        return attributes;
    }

    @Override
    public String getServiceName() {
        return "Refuse Route";
    }

    @Override
    public DataSet startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) {
        ListItem vehicleListItem = inspectionSearchDataSetItem.getOptionalListItemAttributeAtIndex(SEARCH_VEHICLE).orElseThrow(() -> new RuntimeException("No vehicle provided"));
        ListItem routeListItem = inspectionSearchDataSetItem.getOptionalListItemAttributeAtIndex(SEARCH_ROUTE).orElseThrow(() -> new RuntimeException("No route provided"));
        Vehicle vehicle = Vehicle.find.byId(Integer.parseInt(vehicleListItem.id));
        Route route = Route.find.byId(Integer.parseInt(routeListItem.id));
        if ( vehicle == null ) throw new RuntimeException("Vehicle not found");
        if ( route == null ) throw new RuntimeException("Route not found");

        RouteCollection routeCollection = newRouteCollectionFromRoute(route, vehicle, authenticationInfo.getUserID());
        DataSet dataSet = newEmptyInspectionDataSet();

        routeCollection.getRouteActions().forEach(routeCollectionAction -> routeCollectionAction.copyTo(dataSet.addNewDataSetItem()));

        return dataSet;
    }

    @Override
    public DataSet completeInspection(DataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters) {
        return null;
    }

    @Override
    public DataSet updateInspectionItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) {
        return null;
    }

    @Override
    public boolean shouldSendIncrementalUpdates() {
        return true;
    }

    private static int SEARCH_ROUTE = 0;
    private static int SEARCH_VEHICLE = 1;


    public static RouteCollection newRouteCollectionFromRoute(Route route, Vehicle vehicle, String username) {
        RouteCollection routeCollection = new RouteCollection();
        routeCollection.setRouteBeginTime(DateTime.now());
        routeCollection.setVehicle(vehicle);
        routeCollection.setUser(username);
        routeCollection.setRouteId(route.getRouteID());

        for (RouteStop stop : route.getStops()) {
            RouteCollectionAction routeAction = new RouteCollectionAction();
            routeAction.setLatitude(stop.getLatitude());
            routeAction.setLongitude(stop.getLongitude());
            routeAction.setPickupItem1(stop.getPickupItem1());
            routeAction.setPickupItem1QuantityType(stop.getPickupItem1QuantityType());
            routeAction.setPickupItem2(stop.getPickupItem2());
            routeAction.setPickupItem2QuantityType(stop.getPickupItem2QuantityType());
            routeAction.setPickupItem3(stop.getPickupItem3());
            routeAction.setPickupItem3QuantityType(stop.getPickupItem3QuantityType());
            routeAction.setRouteOrder(stop.getRouteStopOrder());
            routeCollection.getRouteActions().add(routeAction);
        }
        routeCollection.insert();
        return routeCollection;
    }
}
