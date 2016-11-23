package model;


import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import sdk.data.DataSetItem;
import sdk.data.RelatedServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eddie on 11/21/16.
 */
@Entity
@Table(name = "grr_route")
public class Route extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_id_seq")
    int routeID;

    @Column(name = "route_desc")
    private String description;
    private String city;
    private String state;
    private String zip;
    private DateTime enterDate;
    private DateTime modifyDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
    List<RouteStop> stops = new ArrayList<>();

    public static Model.Finder<Integer, Route> find = new Model.Finder<>(Route.class);

    public static Set<ServiceConfigurationAttribute> getServiceAttributes() {
        HashSet<ServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(DESCRIPTION).name("Description").asText().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(CITY).name("CITY").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(STATE).name("STATE").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ZIP).name("ZIP").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(ENTER_DATE).name("ENTER_DATE").asDateTime().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(MODIFY_DATE).name("MODIFY_DATE").asDateTime().canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(STOPS)
                .name("Stops")
                .asRelationship(new RelatedServiceConfiguration.Builder("Stops")
                        .withAttributes(RouteStop.getServiceAttributes())
                        .build()
                )
                .build()
        );
        return attributes;
    }

    public DataSetItem copyInto(DataSetItem dataSetItem) {
        dataSetItem.setPrimaryKey(routeID + "");
        dataSetItem.setStringForAttributeIndex(description, DESCRIPTION);
        dataSetItem.setStringForAttributeIndex(city, CITY);
        dataSetItem.setStringForAttributeIndex(state, STATE);
        dataSetItem.setStringForAttributeIndex(zip, ZIP);
        dataSetItem.setDateTimeForAttributeIndex(enterDate, ENTER_DATE);
        dataSetItem.setDateTimeForAttributeIndex(modifyDate, MODIFY_DATE);
        for ( RouteStop stop : stops ) {
            stop.copyInto(dataSetItem.addNewDataSetItemForAttributeIndex(STOPS));
        }
        return dataSetItem;
    }

    public void copyFrom(DataSetItem dataSetItem) {
        if ( dataSetItem.getCRUDStatus() != DataSetItem.CRUDStatus.Read ) {
            this.description = dataSetItem.getStringAttributeAtIndex(DESCRIPTION);
            this.city = dataSetItem.getStringAttributeAtIndex(CITY);
            this.state = dataSetItem.getStringAttributeAtIndex(STATE);
            this.zip = dataSetItem.getStringAttributeAtIndex(ZIP);
            if ( this.enterDate != null ) {
                this.enterDate = DateTime.now();
            }
            this.modifyDate = DateTime.now();
        }
        switch (dataSetItem.getCRUDStatus()) {
            case Create:
                this.insert();
                break;
            case Update:
                this.routeID = Integer.parseInt(dataSetItem.getPrimaryKey());
                this.update();
                break;
            default:
                break;
        }

        List<DataSetItem> stops = dataSetItem.getDataSetItemsAtIndex(STOPS);
        if ( stops != null ) {
            for ( DataSetItem stop : stops ) {
                RouteStop routeStop = new RouteStop();
                switch (stop.getCRUDStatus()) {
                    case Create:
                        routeStop.route = this;
                        routeStop.copyFrom(stop);
                    case Update:
                        routeStop.copyFrom(stop);
                }
            }
        }
    }

    private static int DESCRIPTION = 0;
    private static int CITY = 1;
    private static int STATE = 2;
    private static int ZIP = 3;
    private static int ENTER_DATE = 4;
    private static int MODIFY_DATE = 5;
    private static int STOPS = 6;
}
