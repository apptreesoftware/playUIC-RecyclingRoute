package model;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
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

    public DataSetItem copyInto(DataSetItem dataSetItem) {
        dataSetItem.setPrimaryKey(this.id + "");
        dataSetItem.setStringForAttributeIndex(this.description, DESCRIPTION);
        dataSetItem.setIntForAttributeIndex(this.replacementCost, REPLACEMENT_COST);
        dataSetItem.setDateTimeForAttributeIndex(this.enterDate, ENTER_DATE);
        dataSetItem.setDateTimeForAttributeIndex(this.modifyDate, MODIFY_DATE);
        return dataSetItem;
    }

    public void copyFrom(DataSetItem dataSetItem) {
        if ( dataSetItem.getCRUDStatus() != DataSetItem.CRUDStatus.Create ) {
            this.id = Integer.parseInt(dataSetItem.getPrimaryKey());
        }
        this.description = dataSetItem.getStringAttributeAtIndex(DESCRIPTION);
        this.enterDate = dataSetItem.getOptionalDateTimeAttributeAtIndex(ENTER_DATE).orElse(DateTime.now());
        this.modifyDate = dataSetItem.getOptionalDateTimeAttributeAtIndex(MODIFY_DATE).orElse(DateTime.now());
        this.replacementCost = dataSetItem.getIntAttributeAtIndex(REPLACEMENT_COST);
    }

    public static Set<ListServiceConfigurationAttribute> getListAttributes() {
        HashSet<ListServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ListServiceConfigurationAttribute.Builder(0).name("Description").build());
        attributes.add(new ListServiceConfigurationAttribute.Builder(1).name("Replacement Cost").build());
        return attributes;
    }

    public static List<ServiceConfigurationAttribute> getServiceAttributes() {
        ArrayList<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(DESCRIPTION).name("DESCRIPTION").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(REPLACEMENT_COST).name("REPLACEMENT_COST").asInt().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ENTER_DATE).name("ENTER_DATE").asDateTime().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(MODIFY_DATE).name("MODIFY_DATE").asDateTime().canCreate().canUpdate().build());
        return attributes;
    }

    private static int DESCRIPTION = 0;
    private static int REPLACEMENT_COST = 1;
    private static int ENTER_DATE = 2;
    private static int MODIFY_DATE = 3;

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
