package model;

import com.avaje.ebean.Model;
import sdk.list.ListItem;
import sdk.list.ListServiceConfigurationAttribute;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Matthew Smith on 11/23/16.
 * Copyright AppTree Software, Inc.
 */
@Entity
@Table(name = "grr_pickup_quantity_type")
public class QuantityType extends Model {
    @Id
    @GeneratedValue
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

    private static int NAME = 0;
}
