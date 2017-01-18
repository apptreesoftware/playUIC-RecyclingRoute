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
@Table(name = "grr_pickup_quantity_type")
public class QuantityType extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quantity_type_id_seq")
    private int id;

    @Column(name = "quantity_name")
    private String name;

    public QuantityType() {}

    public QuantityType(ListItem listItem) {
        this.id = Integer.parseInt(listItem.id);
    }

    public static Model.Finder<Integer, QuantityType> find = new Model.Finder<>(QuantityType.class);

    public ListItem toListItem() {
        ListItem listItem = new ListItem(id + "");
        listItem.setStringForAttributeIndex(name, NAME);
        return listItem;
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


    public void copyFrom(DataSetItem dataSetItem){
        this.name = dataSetItem.getStringAttributeAtIndex(NAME);
        switch( dataSetItem.getCRUDStatus() ){
            case Create:
                this.insert();
                break;
            case Update:
                this.id = Integer.parseInt( dataSetItem.getPrimaryKey() );
                this.update();
                break;
        }
    }

    public DataSetItem copyTo(DataSetItem dataSetItem){
            dataSetItem.setPrimaryKey(this.id + "");
            dataSetItem.setStringForAttributeIndex(this.name, NAME);
            return dataSetItem;
    }



    private static int NAME = 0;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
