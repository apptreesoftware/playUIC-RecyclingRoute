package model;

import com.avaje.ebean.Model;
import sdk.list.ListItem;
import sdk.list.ListServiceConfigurationAttribute;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Matthew Smith on 11/23/16.
 * Copyright AppTree Software, Inc.
 */
@Entity
@Table(name = "grr_pickup_type")
public class PickupType extends Model {
    @Id
    @GeneratedValue
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

    private static int NAME = 0;
}
