package model;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew Smith on 11/22/16.
 * Copyright AppTree Software, Inc.
 */
@Entity
@Table(name = "grr_route_stop_pu_items")
public class RouteStopPickupItem extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_pickup_item_seq")
    int pickupItemId;
    int routeStopId;
    @Column(name = "pu_item")
    String name;
    DateTime enterDate = DateTime.now();
    DateTime modifyDate;

    @ManyToOne
    @JoinColumn(name = "route_stop_id", referencedColumnName = "route_stop_id")
    RouteStop routeStop;

    public static List<ServiceConfigurationAttribute> getServiceAttributes() {
        ArrayList<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(NAME).name("NAME").canCreate().canUpdate().build());
        return attributes;
    }

    public DataSetItem copyInto(DataSetItem dataSetItem) {
        dataSetItem.setPrimaryKey(this.pickupItemId + "");
        dataSetItem.setStringForAttributeIndex(this.name, NAME);
        return dataSetItem;
    }

    public void copyFrom(DataSetItem dataSetItem) {
        this.pickupItemId = Integer.parseInt(dataSetItem.getPrimaryKey());
        this.name = dataSetItem.getStringAttributeAtIndex(NAME);
    }

    private static int NAME = 0;
}
