package model;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import sdk.data.ServiceConfigurationAttribute;
import sdk.list.ListServiceConfigurationAttribute;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eddie on 11/18/16.
 */
@Entity
@Table(name = "grr_route")
public class Route extends Model {
    @Column(name = "route_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_id_seq")
    @Id
    int id;
    @Column(name = "route_desc")
    String description;
    @Column(name = "city")
    String city;
    @Column(name = "state")
    String state;
    @Column(name = "zip")
    String zip;
    @Column(name = "enter_date")
    DateTime enterDate;
    @Column(name = "modify_date")
    DateTime modifyDate;

    public static Model.Finder<Integer, Vehicle> find = new Model.Finder<>(Vehicle.class);

    public static Set<ServiceConfigurationAttribute> getAttributes() {
        HashSet<ServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(0).name("route_desc").build());
        attributes.add(new ServiceConfigurationAttribute.Builder(1).name("Replacement Cost").build());
        return attributes;
    }

}
