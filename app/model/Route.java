package model;


import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import sdk.data.DataSetItem;
import sdk.data.RelatedServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import util.Constants;
import util.DataUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eddie on 11/21/16.
 */
public enum Route implements ModelAttribute {
    RouteID(0,"ROUTE_ID"),
    RouteDescription(1, "ROUTE_DESC"),
    City(2,"CITY"),
    State(3, "STATE"),
    Zip(4, "ZIP"),
    EnterDate(5, "ENTER_DATE"),
    ModifyDate(6, "MODIFY_DATE"),
    //Repeating forms
    RouteStops(30, "");

    private int attributeIndex;
    private String columnName;
    private Route(int attributeIndex, String columnName) {
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
        list.add(new ServiceConfigurationAttribute.Builder(RouteID.attributeIndex)
                .name(RouteID.name())
                .canUpdateAndRequired()
                .canSearch()
                .asInt()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(RouteDescription.attributeIndex)
                .name(RouteDescription.name())
                .canSearch()
                .canCreateAndRequired()
                .canUpdateAndRequired()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(City.attributeIndex)
                .name(City.name())
                .canSearch()
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(State.attributeIndex)
                .name(State.name())
                .canSearch()
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(Zip.attributeIndex)
                .name(Zip.name())
                .canSearch()
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(EnterDate.attributeIndex)
                .name(EnterDate.name())
                .asDate()
                .canSearch()
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(ModifyDate.attributeIndex)
                .name(ModifyDate.name())
                .asDate()
                .canSearch()
                .canCreate()
                .canUpdate()
                .build());
        list.add(new ServiceConfigurationAttribute.Builder(Route.RouteStops.attributeIndex)
                .name("RouteStops")
                .canUpdate()
                .canCreate()
                .asSingleRelationship(new RelatedServiceConfiguration(RouteStops.name(), RouteStop.getAttributes()))
                .build());
        return list;
    }

    public static void updateDataSetItemFromResultSet(DataSetItem dataSetItem, ResultSet resultSet) throws SQLException {
        dataSetItem.setPrimaryKey(resultSet.getString(RouteID.columnName));
        dataSetItem.setIntForAttributeIndex(resultSet.getInt(RouteID.columnName), RouteID.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(RouteDescription.columnName), RouteDescription.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(City.columnName), City.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(State.columnName), State.attributeIndex);
        dataSetItem.setStringForAttributeIndex(resultSet.getString(Zip.columnName), Zip.attributeIndex);
        dataSetItem.setDateForAttributeIndex(DataUtils.dateTimeFromSQLDate(resultSet.getDate(EnterDate.columnName)), EnterDate.attributeIndex);
        dataSetItem.setDateForAttributeIndex(DataUtils.dateTimeFromSQLDate(resultSet.getDate(ModifyDate.columnName)), ModifyDate.attributeIndex);
    }

}
