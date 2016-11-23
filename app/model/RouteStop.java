package model;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import sdk.data.DataSetItem;
import sdk.data.RelatedServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.models.Location;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eddie on 11/21/16.
 */
@Entity
@Table(name = "grr_route_stop")
public class RouteStop extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_stop_id_seq")
    private int routeStopId;
    private int routeStopOrder;
    @Column(name = "route_stop_name")
    private String name;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zip;
    private String contactName;
    private String contactEmail;
    private boolean notifyContactOnNext;
    private boolean notifyContactOnException;
    @Column(name = "route_stop_lat")
    private double latitude;
    @Column(name = "route_stop_lon")
    private double longitude;
    private DateTime enterDate;
    private DateTime modifyDate;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    Route route;

    @OneToMany(mappedBy = "routeStop")
    private List<RouteStopPickupItem> pickupItems;

    public static Model.Finder<Integer, RouteStop> find = new Model.Finder<>(RouteStop.class);

    DataSetItem copyInto(DataSetItem dataSetItem) {
        dataSetItem.setPrimaryKey(this.routeStopId + "");
        dataSetItem.setStringForAttributeIndex(this.name, NAME);
        dataSetItem.setStringForAttributeIndex(this.streetAddress1, STREET_1);
        dataSetItem.setStringForAttributeIndex(this.streetAddress2, STREET_2);
        dataSetItem.setStringForAttributeIndex(this.city, CITY);
        dataSetItem.setStringForAttributeIndex(this.state, STATE);
        dataSetItem.setStringForAttributeIndex(this.zip, ZIP);
        dataSetItem.setStringForAttributeIndex(this.contactName, CONTACT_NAME);
        dataSetItem.setStringForAttributeIndex(this.contactEmail, CONTACT_EMAIL);
        dataSetItem.setBooleanForAttributeIndex(this.notifyContactOnNext, NOTIFY_CONTACT_ON_NEXT);
        dataSetItem.setBooleanForAttributeIndex(this.notifyContactOnException, NOTIFY_CONTACT_ON_EXCEPTION);
        dataSetItem.setLocationForAttributeIndex(new Location(this.latitude, this.longitude), LOCATION);
        dataSetItem.setDateTimeForAttributeIndex(this.enterDate, ENTER_DATE);
        dataSetItem.setDateTimeForAttributeIndex(this.modifyDate, MODIFY_DATE);
        dataSetItem.setIntForAttributeIndex(this.routeStopOrder, ORDER);
        dataSetItem.setStringForAttributeIndex(getAddress(), ADDRESS);
        pickupItems.forEach(item -> {
            item.copyInto(dataSetItem.addNewDataSetItemForAttributeIndex(PICKUP_ITEMS));
        });
        return dataSetItem;
    }

    void copyFrom(DataSetItem dataSetItem) {
        if ( dataSetItem.getCRUDStatus() != DataSetItem.CRUDStatus.Read ) {
            this.name = dataSetItem.getStringAttributeAtIndex(NAME);
            this.streetAddress1 = dataSetItem.getStringAttributeAtIndex(STREET_1);
            this.streetAddress2 = dataSetItem.getStringAttributeAtIndex(STREET_2);
            this.city = dataSetItem.getStringAttributeAtIndex(CITY);
            this.state = dataSetItem.getStringAttributeAtIndex(STATE);
            this.zip = dataSetItem.getStringAttributeAtIndex(ZIP);
            this.contactName = dataSetItem.getStringAttributeAtIndex(CONTACT_NAME);
            this.contactEmail = dataSetItem.getStringAttributeAtIndex(CONTACT_EMAIL);
            this.notifyContactOnException = dataSetItem.getBoolValueAtIndex(NOTIFY_CONTACT_ON_EXCEPTION);
            this.notifyContactOnNext = dataSetItem.getBoolValueAtIndex(NOTIFY_CONTACT_ON_NEXT);
            dataSetItem.getOptionalLocationAtIndex(LOCATION)
                    .ifPresent(location -> {
                        this.latitude = location.getLatitude();
                        this.longitude = location.getLongitude();
                    });
            this.enterDate = this.enterDate != null ? this.enterDate : DateTime.now();
            this.modifyDate = DateTime.now();
            this.routeStopOrder = dataSetItem.getIntAttributeAtIndex(ORDER);
        }
        switch (dataSetItem.getCRUDStatus()) {
            case Create:
                this.insert();
                break;
            case Update:
                this.routeStopId = Integer.parseInt(dataSetItem.getPrimaryKey());
                this.update();
                break;
        }

        List<DataSetItem> pickupItems = dataSetItem.getDataSetItemsAtIndex(PICKUP_ITEMS);
        if (pickupItems != null) {
            pickupItems.forEach(item -> {
                RouteStopPickupItem pickupItem = new RouteStopPickupItem();
                pickupItem.copyFrom(dataSetItem);
                this.pickupItems.add(pickupItem);
            });
        }
    }

    private String getAddress() {
        String address1 = this.streetAddress1 != null ? this.streetAddress1 : "";
        String address2 = this.streetAddress2 != null ? this.streetAddress2 : "";
        String city = this.city != null ? this.city : "";
        String state = this.state != null ? this.state : "";
        String zip = this.zip != null ? this.zip  : "";

        return String.format("%s %s\n%s %s %s", address1, address2, city, state, zip);
    }

    private static int NAME = 0;
    private static int STREET_1 = 1;
    private static int STREET_2 = 2;
    private static int CITY = 3;
    private static int STATE = 4;
    private static int ZIP = 5;
    private static int CONTACT_NAME = 6;
    private static int CONTACT_EMAIL = 7;
    private static int NOTIFY_CONTACT_ON_NEXT = 8;
    private static int NOTIFY_CONTACT_ON_EXCEPTION = 9;
    private static int LOCATION = 10;
    private static int ENTER_DATE = 11;
    private static int MODIFY_DATE = 12;
    private static int ORDER = 13;
    private static int PICKUP_ITEMS = 14;
    private static int ADDRESS = 15;

    public static List<ServiceConfigurationAttribute> getServiceAttributes() {
        List<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(NAME).name("Name")
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(STREET_1).name("STREET 1")
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(STREET_2).name("STREET 2")
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(CITY).name("City")
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(STATE).name("STATE")
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ZIP).name("ZIP")
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(CONTACT_NAME).name("CONTACT_NAME")
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(CONTACT_EMAIL).name("CONTACT_EMAIL")
                .asText()
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(NOTIFY_CONTACT_ON_NEXT).name("NOTIFY_CONTACT_ON_NEXT")
                .asBool()
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(NOTIFY_CONTACT_ON_EXCEPTION).name("NOTIFY_CONTACT_ON_EXCEPTION")
                .asBool()
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(LOCATION).name("LOCATION")
                .asLocation()
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ENTER_DATE).name("ENTER_DATE")
                .asDateTime()
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(MODIFY_DATE).name("MODIFY_DATE")
                .asDateTime()
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ORDER).name("ORDER")
                .asInt()
                .canCreate()
                .canUpdate()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_ITEMS).name("Pickup Items")
                .asRelationship(new RelatedServiceConfiguration.Builder("Pickup Items")
                        .withAttributes(RouteStopPickupItem.getServiceAttributes())
                        .build())
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ADDRESS).name("ADDRESS")
                .build());

        return attributes;
    }
}
