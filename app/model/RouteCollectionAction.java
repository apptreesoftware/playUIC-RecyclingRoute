package model;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.models.Location;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Matthew Smith on 12/2/16.
 * Copyright AppTree Software, Inc.
 */
@Entity
@Table(name = "grr_route_coll_action")
public class RouteCollectionAction extends Model {

    @Id
    @Column(name = "route_coll_action_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_coll_action_id_seq")
    private int routeActionId;

    @Column(name = "route_stop_order")
    private int routeOrder;
    @Column(name = "route_action_comment")
    private String comment;
    @Column(name = "route_action_lat")
    private double latitude;
    @Column(name = "route_action_lon")
    private double longitude;

    @Column(name = "route_stop_name")
    private String name;

    private String address;

    private String contactName;
    private String contactEmail;

    private boolean notifyContactOnNext;
    private boolean notifyContactOnException;

    private DateTime arrivalTime;
    private DateTime departureTime;

    @OneToOne
    @JoinColumn(name = "route_action_exception")
    private
    RouteException routeException;

    @OneToOne
    @JoinColumn(name = "pickup_item_1")
    private PickupType pickupItem1;

    @OneToOne
    @JoinColumn(name = "pickup_item_1_measurement")
    private QuantityType pickupItem1QuantityType;

    @Column(name = "pickup_item_1_quantity")
    private float pickupItem1Quantity;

    @Column(name = "pickup_item_1_weight")
    private int pickupItem1Weight;

    @OneToOne
    @JoinColumn(name = "pickup_item_2")
    private PickupType pickupItem2;

    @OneToOne
    @JoinColumn(name = "pickup_item_2_measurement")
    private QuantityType pickupItem2QuantityType;

    @Column(name = "pickup_item_2_quantity")
    private float pickupItem2Quantity;

    @Column(name = "pickup_item_2_weight")
    private int pickupItem2Weight;

    @OneToOne
    @JoinColumn(name = "pickup_item_3")
    private PickupType pickupItem3;

    @OneToOne
    @JoinColumn(name = "pickup_item_3_measurement")
    private QuantityType pickupItem3QuantityType;

    @Column(name = "pickup_item_3_quantity")
    private float pickupItem3Quantity;

    @Column(name = "pickup_item_3_weight")
    private int pickupItem3Weight;

    @ManyToOne
    @JoinColumn(name = "route_coll_id", referencedColumnName = "route_coll_id")
    @JsonIgnore
    private RouteCollection routeCollection;

    public static Model.Finder<Integer, RouteCollectionAction> find = new Model.Finder<>(RouteCollectionAction.class);

    public DataSetItem copyTo(DataSetItem dataSetItem) {
        dataSetItem.setPrimaryKey(this.routeActionId + "");
        dataSetItem.setIntForAttributeIndex(this.routeOrder, ROUTE_ORDER);
        dataSetItem.setStringForAttributeIndex(this.comment, COMMENT);
        dataSetItem.setLocationForAttributeIndex(new Location(getLatitude(), getLongitude()), LOCATION);
        dataSetItem.setDateTimeForAttributeIndex(getArrivalTime(), ARRIVAL_TIME);
        dataSetItem.setDateTimeForAttributeIndex(getDepartureTime(), DEPARTURE_TIME);
        if ( getRouteException() != null ) dataSetItem.setListItemForAttributeIndex(getRouteException().toListItem(), ROUTE_EXCEPTION);
        if ( getPickupItem1() != null ) dataSetItem.setListItemForAttributeIndex(getPickupItem1().toListItem(), PICKUP_TYPE_1);
        if ( getPickupItem2() != null ) dataSetItem.setListItemForAttributeIndex(getPickupItem2().toListItem(), PICKUP_TYPE_2);
        if ( getPickupItem3() != null ) dataSetItem.setListItemForAttributeIndex(getPickupItem3().toListItem(), PICKUP_TYPE_3);

        if ( getPickupItem1QuantityType() != null ) dataSetItem.setListItemForAttributeIndex(getPickupItem1QuantityType().toListItem(), PICKUP_QUANTITY_TYPE_1);
        if ( getPickupItem2QuantityType() != null ) dataSetItem.setListItemForAttributeIndex(getPickupItem2QuantityType().toListItem(), PICKUP_QUANTITY_TYPE_2);
        if ( getPickupItem3QuantityType() != null ) dataSetItem.setListItemForAttributeIndex(getPickupItem3QuantityType().toListItem(), PICKUP_QUANTITY_TYPE_3);

        dataSetItem.setDoubleForAttributeIndex(getPickupItem1Quantity(), PICKUP_QUANTITY_1);
        dataSetItem.setDoubleForAttributeIndex(getPickupItem2Quantity(), PICKUP_QUANTITY_2);
        dataSetItem.setDoubleForAttributeIndex(getPickupItem3Quantity(), PICKUP_QUANTITY_3);

        dataSetItem.setIntForAttributeIndex(this.pickupItem1Weight, PICKUP_ITEM_1_WEIGHT);
        dataSetItem.setIntForAttributeIndex(this.pickupItem2Weight, PICKUP_ITEM_2_WEIGHT);
        dataSetItem.setIntForAttributeIndex(this.pickupItem3Weight, PICKUP_ITEM_3_WEIGHT);
        dataSetItem.setStringForAttributeIndex(this.name, NAME);
        dataSetItem.setStringForAttributeIndex(this.address, ADDRESS);
        dataSetItem.setStringForAttributeIndex(this.contactName, CONTACT_NAME);
        dataSetItem.setStringForAttributeIndex(this.contactEmail, CONTACT_EMAIL);
        return dataSetItem;
    }

    public void copyFrom(DataSetItem dataSetItem) {
        this.comment = dataSetItem.getStringAttributeAtIndex(COMMENT);
        dataSetItem.getOptionalLocationAtIndex(LOCATION).ifPresent(location -> {
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
        });
        this.arrivalTime = dataSetItem.getDateTimeAttributeAtIndex(ARRIVAL_TIME);
        this.departureTime = dataSetItem.getDateTimeAttributeAtIndex(DEPARTURE_TIME);
        dataSetItem.getOptionalListItemAttributeAtIndex(ROUTE_EXCEPTION).ifPresent(listItem -> this.routeException = new RouteException(listItem));

        dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_TYPE_1).ifPresent(listItem -> this.pickupItem1 = new PickupType(listItem));
        dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_QUANTITY_TYPE_1).ifPresent(listItem -> this.pickupItem1QuantityType = new QuantityType(listItem));
        dataSetItem.getOptionalDoubleAttributeAtIndex(PICKUP_QUANTITY_1).ifPresent(value -> this.pickupItem1Quantity = value.floatValue());

        dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_TYPE_2).ifPresent(listItem -> this.pickupItem2 = new PickupType(listItem));
        dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_QUANTITY_TYPE_2).ifPresent(listItem -> this.pickupItem2QuantityType = new QuantityType(listItem));
        dataSetItem.getOptionalDoubleAttributeAtIndex(PICKUP_QUANTITY_2).ifPresent(value -> this.pickupItem2Quantity = value.floatValue());

        dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_TYPE_3).ifPresent(listItem -> this.pickupItem3 = new PickupType(listItem));
        dataSetItem.getOptionalListItemAttributeAtIndex(PICKUP_QUANTITY_TYPE_3).ifPresent(listItem -> this.pickupItem3QuantityType = new QuantityType(listItem));
        dataSetItem.getOptionalDoubleAttributeAtIndex(PICKUP_QUANTITY_3).ifPresent(value -> this.pickupItem3Quantity = value.floatValue());

        dataSetItem.getOptionalIntAttributeAtIndex(PICKUP_ITEM_1_WEIGHT).ifPresent(value -> this.pickupItem1Weight = value);
        dataSetItem.getOptionalIntAttributeAtIndex(PICKUP_ITEM_2_WEIGHT).ifPresent(value -> this.pickupItem2Weight = value);
        dataSetItem.getOptionalIntAttributeAtIndex(PICKUP_ITEM_3_WEIGHT).ifPresent(value -> this.pickupItem3Weight = value);
    }

    public static Collection<ServiceConfigurationAttribute> getServiceAttributes() {
        HashSet<ServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(ROUTE_ORDER).name("ROUTE_ORDER").asInt().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(COMMENT).name("COMMENT").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(LOCATION).name("LOCATION").asLocation().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ARRIVAL_TIME).name("ARRIVAL_TIME").asDateTime().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(DEPARTURE_TIME).name("DEPARTURE_TIME").asDateTime().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ROUTE_EXCEPTION).name("ROUTE_EXCEPTION").asListItem(RouteException.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_TYPE_1).name("PICKUP_TYPE_1").asListItem(PickupType.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_TYPE_2).name("PICKUP_TYPE_2").asListItem(PickupType.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_TYPE_3).name("PICKUP_TYPE_3").asListItem(PickupType.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_QUANTITY_TYPE_1).name("PICKUP_QUANTITY_TYPE_1").asListItem(QuantityType.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_QUANTITY_TYPE_2).name("PICKUP_QUANTITY_TYPE_2").asListItem(QuantityType.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_QUANTITY_TYPE_3).name("PICKUP_QUANTITY_TYPE_3").asListItem(QuantityType.getListAttributes()).canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_QUANTITY_1).name("PICKUP_QUANTITY_1").asDouble().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_QUANTITY_2).name("PICKUP_QUANTITY_2").asDouble().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_QUANTITY_3).name("PICKUP_QUANTITY_3").asDouble().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_ITEM_1_WEIGHT).name("PICKUP_ITEM_1_WEIGHT").asInt().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_ITEM_2_WEIGHT).name("PICKUP_ITEM_2_WEIGHT").asInt().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PICKUP_ITEM_3_WEIGHT).name("PICKUP_ITEM_3_WEIGHT").asInt().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ADDRESS).name("ADDRESS").build());
        attributes.add(new ServiceConfigurationAttribute.Builder(NAME).name("NAME").build());
        attributes.add(new ServiceConfigurationAttribute.Builder(CONTACT_NAME).name("CONTACT_NAME").build());
        attributes.add(new ServiceConfigurationAttribute.Builder(CONTACT_EMAIL).name("CONTACT_EMAIL").build());

        return attributes;
    }

    private static int ROUTE_ORDER = 0;
    private static int COMMENT = 1;
    private static int LOCATION = 2;
    private static int ARRIVAL_TIME = 3;
    private static int DEPARTURE_TIME = 4;
    private static int ROUTE_EXCEPTION = 5;
    private static int PICKUP_TYPE_1 = 6;
    private static int PICKUP_QUANTITY_TYPE_1 = 7;
    private static int PICKUP_QUANTITY_1 = 8;

    private static int PICKUP_TYPE_2 = 9;
    private static int PICKUP_QUANTITY_TYPE_2 = 10;
    private static int PICKUP_QUANTITY_2 = 11;

    private static int PICKUP_TYPE_3 = 12;
    private static int PICKUP_QUANTITY_TYPE_3 = 13;
    private static int PICKUP_QUANTITY_3 = 14;
    private static int ADDRESS = 15;
    private static int PICKUP_ITEM_1_WEIGHT = 16;
    private static int PICKUP_ITEM_2_WEIGHT = 17;
    private static int PICKUP_ITEM_3_WEIGHT = 18;
    private static int NAME = 19;
    private static int CONTACT_NAME = 20;
    private static int CONTACT_EMAIL = 21;

    public int getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(int routeOrder) {
        this.routeOrder = routeOrder;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public DateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(DateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public DateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(DateTime departureTime) {
        this.departureTime = departureTime;
    }

    public RouteException getRouteException() {
        return routeException;
    }

    public void setRouteException(RouteException routeException) {
        this.routeException = routeException;
    }

    public PickupType getPickupItem1() {
        return pickupItem1;
    }

    public void setPickupItem1(PickupType pickupItem1) {
        this.pickupItem1 = pickupItem1;
    }

    public QuantityType getPickupItem1QuantityType() {
        return pickupItem1QuantityType;
    }

    public void setPickupItem1QuantityType(QuantityType pickupItem1QuantityType) {
        this.pickupItem1QuantityType = pickupItem1QuantityType;
    }

    public float getPickupItem1Quantity() {
        return pickupItem1Quantity;
    }

    public void setPickupItem1Quantity(float pickupItem1Quantity) {
        this.pickupItem1Quantity = pickupItem1Quantity;
    }

    public PickupType getPickupItem2() {
        return pickupItem2;
    }

    public void setPickupItem2(PickupType pickupItem2) {
        this.pickupItem2 = pickupItem2;
    }

    public QuantityType getPickupItem2QuantityType() {
        return pickupItem2QuantityType;
    }

    public void setPickupItem2QuantityType(QuantityType pickupItem2QuantityType) {
        this.pickupItem2QuantityType = pickupItem2QuantityType;
    }

    public float getPickupItem2Quantity() {
        return pickupItem2Quantity;
    }

    public void setPickupItem2Quantity(float pickupItem2Quantity) {
        this.pickupItem2Quantity = pickupItem2Quantity;
    }

    public PickupType getPickupItem3() {
        return pickupItem3;
    }

    public void setPickupItem3(PickupType pickupItem3) {
        this.pickupItem3 = pickupItem3;
    }

    public QuantityType getPickupItem3QuantityType() {
        return pickupItem3QuantityType;
    }

    public void setPickupItem3QuantityType(QuantityType pickupItem3QuantityType) {
        this.pickupItem3QuantityType = pickupItem3QuantityType;
    }

    public float getPickupItem3Quantity() {
        return pickupItem3Quantity;
    }

    public void setPickupItem3Quantity(float pickupItem3Quantity) {
        this.pickupItem3Quantity = pickupItem3Quantity;
    }

    public RouteCollection getRouteCollection() {
        return routeCollection;
    }

    public void setRouteCollection(RouteCollection routeCollection) {
        this.routeCollection = routeCollection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public boolean isNotifyContactOnNext() {
        return notifyContactOnNext;
    }

    public void setNotifyContactOnNext(boolean notifyContactOnNext) {
        this.notifyContactOnNext = notifyContactOnNext;
    }

    public boolean isNotifyContactOnException() {
        return notifyContactOnException;
    }

    public void setNotifyContactOnException(boolean notifyContactOnException) {
        this.notifyContactOnException = notifyContactOnException;
    }
}
