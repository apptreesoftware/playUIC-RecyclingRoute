package model;

import com.avaje.ebean.Model;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.list.ListItem;
import sdk.list.ListServiceConfigurationAttribute;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Matthew Smith on 11/23/16.
 * Copyright AppTree Software, Inc.
 */
@Entity
@Table(name = "grr_pickup_type")
public class PickupType extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pickup_type_id_seq")
    private int typeId;
    private String name;

    public static Model.Finder<Integer, PickupType> find = new Model.Finder<>(PickupType.class);

    public ListItem toListItem() {
        ListItem listItem = new ListItem(typeId + "");
        listItem.setStringForAttributeIndex(name, NAME);
        return listItem;
    }

    public PickupType() {}

    public PickupType(ListItem listItem) {
        this.typeId = Integer.parseInt(listItem.id);
    }

    public static Set<ListServiceConfigurationAttribute> getListAttributes() {
        HashSet<ListServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ListServiceConfigurationAttribute.Builder(NAME).name("Name").build());
        return attributes;
    }

    public static List<ServiceConfigurationAttribute> getServiceAttributes() {
        ArrayList<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(NAME).name("NAME").canCreateAndRequired().canUpdateAndRequired().build());
        return attributes;
    }

    public void copyFrom(DataSetItem dataSetItem) {
        this.name = dataSetItem.getStringAttributeAtIndex(NAME);
        switch (dataSetItem.getCRUDStatus()) {
            case Create:
                this.insert();
                break;
            case Update:
                this.typeId = Integer.parseInt(dataSetItem.getPrimaryKey());
                this.update();
                break;
        }
    }

    public DataSetItem copyTo(DataSetItem dataSetItem) {
        dataSetItem.setPrimaryKey(this.typeId + "");
        dataSetItem.setStringForAttributeIndex(this.name, NAME);
        return dataSetItem;
    }

    private static int NAME = 0;
}
