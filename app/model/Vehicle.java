package model;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import sdk.list.ListItem;
import sdk.list.ListServiceConfigurationAttribute;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eddie on 11/18/16.
 */
@Entity
@Table(name = "grr_vehicle")
public class Vehicle  extends Model{
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_id_seq")
    @Id
    int id;
    @Column(name = "vehicle_desc")
    String description;
    @Column(name = "replacement_cost")
    int replacementCost;
    @Column(name = "enter_date")
    DateTime enterDate;
    @Column(name = "modify_date")
    DateTime modifyDate;

    public static Model.Finder<Integer, Vehicle> find = new Model.Finder<>(Vehicle.class);

    public ListItem toListItem() {
        ListItem listItem = new ListItem(this.id + "");
        listItem.setAttributeForIndex(description, 0);
        listItem.setAttributeForIndex(replacementCost, 1);
        return listItem;
    }

    public static Set<ListServiceConfigurationAttribute> getListAttributes() {
        HashSet<ListServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ListServiceConfigurationAttribute.Builder(0).name("Description").build());
        attributes.add(new ListServiceConfigurationAttribute.Builder(1).name("Replacement Cost").build());
        return attributes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(int replacementCost) {
        this.replacementCost = replacementCost;
    }

    public DateTime getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(DateTime enterDate) {
        this.enterDate = enterDate;
    }

    public DateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(DateTime modifyDate) {
        this.modifyDate = modifyDate;
    }
}
