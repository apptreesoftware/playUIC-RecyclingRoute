package model;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import sdk.data.DataSetItem;
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

    @OneToOne
    @JoinColumn(name = "pickup_item_1")
    PickupType pickupItem1;

    @OneToOne
    @JoinColumn(name = "pickup_item_1_measurement")
    QuantityType pickupItem1QuantityType;

    @OneToOne
    @JoinColumn(name = "pickup_item_2")
    PickupType pickupItem2;

    @OneToOne
    @JoinColumn(name = "pickup_item_2_measurement")
    QuantityType pickupItem2QuantityType;

    @OneToOne
    @JoinColumn(name = "pickup_item_3")
    PickupType pickupItem3;

    @OneToOne
    @JoinColumn(name = "pickup_item_3_measurement")
    QuantityType pickupItem3QuantityType;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    Route route;

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
        if (pickupItem1 != null) dataSetItem.setListItemForAttributeIndex(pickupItem1.toListItem(), PICKUP_ITEM_1);
        if (pickupItem2 != null) dataSetItem.setListItemForAttributeIndex(pickupItem2.toListItem(), PICKUP_ITEM_2);
        if (pickupItem3 != null) dataSetItem.setListItemForAttributeIndex(pickupItem3.toListItem(), PICKUP_ITEM_3);
        if(pickupItem1QuantityType != null) dataSetItem.setListItemForAttributeIndex(pickupItem1QuantityType.toListItem(), PICKUP_ITEM_1_TYPE);
        if(pickupItem2QuantityType != null) dataSetItem.setListItemForAttributeIndex(pickupItem2QuantityType.toListItem(), PICKUP_ITEM_2_TYPE);
        if(pickupItem3QuantityType != null) dataSetItem.setListItemForAttributeIndex(pickupItem3QuantityType.toListItem(), PICKUP_ITEM_3_TYPE);
        return dataSetItem;
    }

    void copyFrom(DataSetItem dataSetItem) {
        if (dataSetItem.getCRUDStatus() != DataSetItem.CRUDStatus.Read) {
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
            dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_ITEM_1).ifPresent(listItem -> this.pickupItem1 = new PickupType(listItem));
            dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_ITEM_2).ifPresent(listItem -> this.pickupItem2 = new PickupType(listItem));
            dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_ITEM_3).ifPresent(listItem -> this.pickupItem3 = new PickupType(listItem));
            dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_ITEM_1_TYPE).ifPresent(listItem -> this.pickupItem1QuantityType = new QuantityType(listItem));
            dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_ITEM_2_TYPE).ifPresent(listItem -> this.pickupItem2QuantityType = new QuantityType(listItem));
            dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_ITEM_3_TYPE).ifPresent(listItem -> this.pickupItem3QuantityType = new QuantityType(listItem));
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
    }

    private String getAddress() {
        String address1 = this.streetAddress1 != null ? this.streetAddress1 : "";
        String address2 = this.streetAddress2 != null ? this.streetAddress2 : "";
        String city = this.city != null ? this.city : "";
        String state = this.state != null ? this.state : "";
        String zip = this.zip != null ? this.zip : "";

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
    private static int ADDRESS = 15;
    private static int PICKUP_ITEM_1 = 16;
    private static int PICKUP_ITEM_1_TYPE = 17;
    private static int PICKUP_ITEM_2 = 18;
    private static int PICKUP_ITEM_2_TYPE = 19;
    private static int PICKUP_ITEM_3 = 20;
    private static int PICKUP_ITEM_3_TYPE = 21;

    public static List<ServiceConfigurationAttribute> getServiceAttributes() {
        List<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(NAME).name("Name").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(STREET_1).name("STREET 1").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(STREET_2).name("STREET 2").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(CITY).name("City").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(STATE).name("STATE").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ZIP).name("ZIP").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(CONTACT_NAME).name("CONTACT_NAME").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(CONTACT_EMAIL).name("CONTACT_EMAIL").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(NOTIFY_CONTACT_ON_NEXT).name("NOTIFY_CONTACT_ON_NEXT").asBool().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(NOTIFY_CONTACT_ON_EXCEPTION).name("NOTIFY_CONTACT_ON_EXCEPTION").asBool().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(LOCATION).name("LOCATION").asLocation().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ENTER_DATE).name("ENTER_DATE").asDateTime().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(MODIFY_DATE).name("MODIFY_DATE").asDateTime().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ORDER).name("ORDER").asInt().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ADDRESS).name("ADDRESS").build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_ITEM_1).name("PICKUP_ITEM_1").asListItem(PickupType.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_ITEM_1_TYPE).name("PICKUP_ITEM_1_TYPE").asListItem(QuantityType.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_ITEM_2).name("PICKUP_ITEM_2").asListItem(PickupType.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_ITEM_2_TYPE).name("PICKUP_ITEM_2_TYPE").asListItem(QuantityType.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_ITEM_3).name("PICKUP_ITEM_3").asListItem(PickupType.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_ITEM_3_TYPE).name("PICKUP_ITEM_3_TYPE").asListItem(QuantityType.getListAttributes()).canCreate().canUpdate().build());
        return attributes;
    }
}
