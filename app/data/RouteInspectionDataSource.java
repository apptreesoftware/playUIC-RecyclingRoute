package data;

import com.avaje.ebean.Ebean;
import model.*;
import org.joda.time.DateTime;
import play.Logger;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.InspectionDataSet;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.RecordActionResponse;
import sdk.datasources.base.InspectionSource;
import sdk.list.ListItem;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.*;

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
                .canSearchAndRequired()
                .canUpdateAndRequired()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(SEARCH_VEHICLE).name("Vehicle")
                .asListItem(Vehicle.getListAttributes())
                .canSearchAndRequired()
                .canUpdateAndRequired()
                .build());

        return attributes;
    }

    @Override
    public String getServiceName() {
        return "Refuse Route";
    }

    @Override
    public InspectionDataSet startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) {
        ListItem vehicleListItem = inspectionSearchDataSetItem.getOptionalListItemAttributeAtIndex(SEARCH_VEHICLE).orElseThrow(() -> new RuntimeException("No vehicle provided"));
        ListItem routeListItem = inspectionSearchDataSetItem.getOptionalListItemAttributeAtIndex(SEARCH_ROUTE).orElseThrow(() -> new RuntimeException("No route provided"));
        Vehicle vehicle = Vehicle.find.byId(Integer.parseInt(vehicleListItem.id));
        Route route = Route.find.byId(Integer.parseInt(routeListItem.id));
        if ( vehicle == null ) throw new RuntimeException("Vehicle not found");
        if ( route == null ) throw new RuntimeException("Route not found");
        RouteCollection routeCollection = newRouteCollectionFromRoute(route, vehicle, authenticationInfo.getUserID());
        InspectionDataSet dataSet = newEmptyInspectionDataSet();
        dataSet.setContextValue("Vehicle", vehicle.getId() + "");
        dataSet.setContextValue("Route", route.getRouteID() + "");
        routeCollection.getRouteActions().forEach(routeCollectionAction -> routeCollectionAction.copyTo(dataSet.addNewDataSetItem()));
        return dataSet;
    }

    @Override
    public DataSetItem searchForInspectionItem(String primaryKey, Map<String, String> inspectionContext, AuthenticationInfo authenticationInfo, Parameters parameters) {
        DataSetItem dataSetItem = new DataSetItem(getInspectionItemAttributes());
        dataSetItem.setPrimaryKey(UUID.randomUUID().toString());
        dataSetItem.setStringForAttributeIndex("TEST ITEM", 1);
        return dataSetItem;
    }

    @Override
    public DataSet completeInspection(InspectionDataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters) {
        RouteCollection routeCollection = null;
        Ebean.beginTransaction();
        for (DataSetItem dataSetItem : completedDataSet.getDataSetItems()) {
            RouteCollectionAction action = RouteCollectionAction.find.byId(Integer.parseInt(dataSetItem.getPrimaryKey()));
            if (action == null) continue;
            if (routeCollection == null) routeCollection = action.getRouteCollection();
            action.copyFrom(dataSetItem);
            action.update();
        }
        if ( routeCollection == null ) {
            Ebean.rollbackTransaction();
            throw new RuntimeException("Collection not found associated with this route action");
        }
        routeCollection.setRouteEndTime(completedDataSet.getEndDate());
        routeCollection.update();
        Ebean.commitTransaction();
        return completedDataSet;
    }

    @Override
    public RecordActionResponse updateInspectionItem(DataSetItem dataSetItem,Map<String, String> inspectionContext, AuthenticationInfo authenticationInfo, Parameters parameters) {
        RouteCollectionAction action = RouteCollectionAction.find.byId(Integer.parseInt(dataSetItem.getPrimaryKey()));
        if ( action == null ) throw new RuntimeException("Route action not found with ID of " + dataSetItem.getPrimaryKey());
        action.copyFrom(dataSetItem);
        action.save();
        DataSetItem responseDataSetItem = new DataSetItem(getInspectionItemAttributes());
        action.copyTo(responseDataSetItem);

        if ( action.getRouteException() != null && action.isNotifyContactOnException() && action.getContactEmail() != null ) {
            Logger.debug("Send email about exception");
        }
        if ( action.isNotifyContactOnNext() ) {
            RouteCollectionAction nextAction = getNextRouteActionAfter(action);
            if ( nextAction != null && nextAction.getContactEmail() != null ) {
                Logger.debug("Send email about next stop");
            }
        }

        return new RecordActionResponse.Builder().withRecord(responseDataSetItem).build();
    }

    private RouteCollectionAction getNextRouteActionAfter(RouteCollectionAction action) {
        RouteCollection collection = action.getRouteCollection();
        List<RouteCollectionAction> actions = collection.getRouteActions();
        int index = actions.indexOf(action);
        index++;
        if ( index < actions.size() ) {
            return actions.get(index);
        }
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

        int index = 0;
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
            routeAction.setRouteOrder(index);
            routeAction.setName(stop.getName());
            routeAction.setNotifyContactOnException(stop.isNotifyContactOnException());
            routeAction.setNotifyContactOnNext(stop.isNotifyContactOnNext());
            routeAction.setContactName(stop.getContactName());
            routeAction.setContactEmail(stop.getContactEmail());
            routeAction.setAddress(stop.getAddress());
            routeCollection.getRouteActions().add(routeAction);
            index++;
        }
        routeCollection.insert();
        return routeCollection;
    }
}
