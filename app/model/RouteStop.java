package model;

import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.models.Location;
import util.DataUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eddie on 11/21/16.
 */
public enum RouteStop implements ModelAttribute {
    RouteStopID(0,"ROUTE_STOP_ID"),
    RouteID(1, "ROUTE_ID"),
    RouteStopOrder(2,"ROUTE_STOP_ORDER"),
    RouteStopName(3, "ROUTE_STOP_NAME"),
    StreetAddress1(4, "STREET_ADDRESS1"),
    StreetAddress2(5, "STREET_ADDRESS2"),
    City(6, "CITY"),
    State(7, "STATE"),
    Zip(8, "ZIP"),
    ContactName(9, "CONTACT_NAME"),
    ContactEmail(10, "CONTACT_EMAIL"),
    NotifyContactOnNext(11, "NOTIFY_CONTACT_ON_NEXT"),
    NotifyContactOnException(12, "NOTIFY_CONTACT_ON_EXCEPTION"),
    Coordinate(13,"COORDINATE"),
    EnterDate(14, "ENTER_DATE"),
    ModifyDate(15, "MODIFY_DATE"),
    Latitude(NON_ATTRIBUTE_INDEX, "ROUTE_STOP_LAT"),
    Longitide(NON_ATTRIBUTE_INDEX, "ROUTE_STOP_LON");

    private int attributeIndex;
    private String columnName;
    private RouteStop(int attributeIndex, String columnName) {
        this.attributeIndex = attributeIndex;
        this.columnName = columnName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public int getAttributeIndex() {
        return attributeIndex;
    }


    public static List<ServiceConfigurationAttribute> getAttributes() {
        ArrayList<ServiceConfigurationAttribute> list = new ArrayList<>();
        list.add(new ServiceConfigurationAttribute.Builder(RouteStopID.attributeIndex)
                .name(RouteStopID.name())
                .asInt()
                .canCreate()
                .canUpdateAndRequired()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(RouteID.attributeIndex)
                .name(RouteID.name())
                .canCreateAndRequired()
                .canUpdateAndRequired()
                .canUpdate()
                .asInt()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(RouteStopOrder.attributeIndex)
                .name(RouteStopOrder.name())
                .canCreateAndRequired()
                .canUpdateAndRequired()
                .asInt()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(RouteStopName.attributeIndex)
                .name(RouteStopName.name())
                .canCreateAndRequired()
                .canUpdateAndRequired()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(StreetAddress1.attributeIndex)
                .name(StreetAddress1.name())
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(StreetAddress2.attributeIndex)
                .name(StreetAddress2.name())
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(City.attributeIndex)
                .name(City.name())
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(State.attributeIndex)
                .name(State.name())
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(Zip.attributeIndex)
                .name(Zip.name())
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(ContactName.attributeIndex)
                .name(ContactName.name())
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(ContactEmail.attributeIndex)
                .name(ContactEmail.name())
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(NotifyContactOnNext.attributeIndex)
                .name(NotifyContactOnNext.name())
                .canCreate()
                .canUpdate()
                .asBool()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(NotifyContactOnException.attributeIndex)
                .name(NotifyContactOnException.name())
                .asBool()
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(Coordinate.attributeIndex)
                .name(Coordinate.name())
                .asLocation()
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(EnterDate.attributeIndex)
                .name(EnterDate.name())
                .asDate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(ModifyDate.attributeIndex)
                .name(ModifyDate.name())
                .asDate()
                .canUpdate()
                .build());
        return list;

    }


    public static void updateDataSetItemFromResultSet(DataSetItem dataSetItem, ResultSet resultSet) throws SQLException {
        dataSetItem.setPrimaryKey(resultSet.getString(RouteStopID.columnName));
        dataSetItem.setIntForAttributeIndex(resultSet.getInt(RouteStopID.columnName), RouteStopID.attributeIndex);
        dataSetItem.setIntForAttributeIndex(resultSet.getInt(RouteID.columnName), RouteID.attributeIndex);
        dataSetItem.setIntForAttributeIndex(resultSet.getInt(RouteStopOrder.columnName), RouteStopOrder.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(RouteStopName.columnName), RouteStopName.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(StreetAddress1.columnName), StreetAddress1.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(StreetAddress2.columnName), StreetAddress2.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(City.columnName), City.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(State.columnName), State.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(Zip.columnName), Zip.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(ContactName.columnName), ContactName.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(ContactEmail.columnName), ContactEmail.attributeIndex);
        dataSetItem.setBooleanForAttributeIndex("Y".equals(resultSet.getString(NotifyContactOnNext.columnName)),NotifyContactOnNext.attributeIndex);
        dataSetItem.setBooleanForAttributeIndex("Y".equals(resultSet.getString(NotifyContactOnException.columnName)),NotifyContactOnException.attributeIndex);
        Location location = Location.fromLatLngString(resultSet.getString(Latitude.columnName)+"," + resultSet.getString(Longitide.columnName));
        dataSetItem.setLocationForAttributeIndex(location,Coordinate.attributeIndex);
        dataSetItem.setDateForAttributeIndex(DataUtils.dateTimeFromSQLDate(resultSet.getDate(EnterDate.columnName)), EnterDate.attributeIndex);
        dataSetItem.setDateForAttributeIndex(DataUtils.dateTimeFromSQLDate(resultSet.getDate(ModifyDate.columnName)), ModifyDate.attributeIndex);
    }




}
