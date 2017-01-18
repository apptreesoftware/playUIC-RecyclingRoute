package model;


import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import sdk.data.DataSetItem;
import sdk.data.RelatedServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.list.ListItem;
import sdk.list.ListServiceConfigurationAttribute;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eddie on 11/21/16.
 */
@Entity
@Table(name = "grr_route")
public class Route extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_id_seq")
    private int routeID;

    @Column(name = "route_desc")
    private String description;
    private String city;
    private String state;
    private String zip;
    private DateTime enterDate;
    private DateTime modifyDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
    @OrderBy(value = "route_stop_order")
    private List<RouteStop> stops = new ArrayList<>();

    public static Model.Finder<Integer, Route> find = new Model.Finder<>(Route.class);


    public static Set<ServiceConfigurationAttribute> getServiceAttributes() {
        HashSet<ServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(DESCRIPTION).name("Description").asText().canCreateAndRequired().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(CITY).name("CITY").canCreateAndRequired().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(STATE).name("STATE").canCreateAndRequired().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ZIP).name("ZIP").canCreateAndRequired().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ENTER_DATE).name("ENTER_DATE").asDateTime().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(MODIFY_DATE).name("MODIFY_DATE").asDateTime().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(STOPS)
                .name("Stops")
                .canCreate()
                .canUpdate()
                .asRelationship(new RelatedServiceConfiguration.Builder("Stops")
                        .withAttributes(RouteStop.getServiceAttributes())
                        .build()
                )
                .build()
        );
        return attributes;
    }

    public static Set<ListServiceConfigurationAttribute> getListServiceAttributes() {
        HashSet<ListServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ListServiceConfigurationAttribute.Builder(DESCRIPTION).name("Description").build());
        attributes.add(new ListServiceConfigurationAttribute.Builder(CITY).name("City").build());
        attributes.add(new ListServiceConfigurationAttribute.Builder(STATE).name("State").build());
        return attributes;
    }

    public ListItem toListItem() {
        ListItem listItem = new ListItem(this.routeID + "");
        listItem.setStringForAttributeIndex(this.description, DESCRIPTION);
        listItem.setStringForAttributeIndex(this.city, CITY);
        listItem.setStringForAttributeIndex(this.state, STATE);
        return listItem;
    }

    public DataSetItem copyInto(DataSetItem dataSetItem) {
        dataSetItem.setPrimaryKey(routeID + "");
        dataSetItem.setStringForAttributeIndex(description, DESCRIPTION);
        dataSetItem.setStringForAttributeIndex(city, CITY);
        dataSetItem.setStringForAttributeIndex(state, STATE);
        dataSetItem.setStringForAttributeIndex(zip, ZIP);
        dataSetItem.setDateTimeForAttributeIndex(enterDate, ENTER_DATE);
        dataSetItem.setDateTimeForAttributeIndex(modifyDate, MODIFY_DATE);
        for ( RouteStop stop : stops ) {
            stop.copyInto(dataSetItem.addNewDataSetItemForAttributeIndex(STOPS));
        }
        return dataSetItem;
    }

    public void copyFrom(DataSetItem dataSetItem) {
        if ( dataSetItem.getCRUDStatus() != DataSetItem.CRUDStatus.Read ) {
            this.description = dataSetItem.getStringAttributeAtIndex(DESCRIPTION);
            this.city = dataSetItem.getStringAttributeAtIndex(CITY);
            this.state = dataSetItem.getStringAttributeAtIndex(STATE);
            this.zip = dataSetItem.getStringAttributeAtIndex(ZIP);
            if ( this.enterDate != null ) {
                this.enterDate = DateTime.now();
            }
            this.modifyDate = DateTime.now();
        }
        switch (dataSetItem.getCRUDStatus()) {
            case Create:
                this.insert();
                break;
            case Update:
                this.routeID = Integer.parseInt(dataSetItem.getPrimaryKey());
                this.update();
                break;
            default:
                break;
        }

        List<DataSetItem> stops = dataSetItem.getDataSetItemsAtIndex(STOPS);
        if ( stops != null ) {
            for ( DataSetItem stop : stops ) {
                RouteStop routeStop = new RouteStop();
                switch (stop.getCRUDStatus()) {
                    case Create:
                        routeStop.setRoute(this);
                        routeStop.copyFrom(stop);
                        break;
                    case Update:
                        routeStop.copyFrom(stop);
                        break;
                }
            }
        }
    }

    public int getRouteID() {
        return routeID;
    }

    public List<RouteStop> getStops() {
        return stops;
    }

    public void setStops(List<RouteStop> stops) {
        this.stops = stops;
    }

    private static int DESCRIPTION = 0;
    private static int CITY = 1;
    private static int STATE = 2;
    private static int ZIP = 3;
    public static int ENTER_DATE = 4;
    private static int MODIFY_DATE = 5;
    public static int STOPS = 6;
}
