package model;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew Smith on 12/2/16.
 * Copyright AppTree Software, Inc.
 */
@Entity
@Table(name = "grr_route_collection")
public class RouteCollection extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_coll_id_seq")
    @Column(name = "route_coll_id")
    int routeCollectionId;
    @Column(name = "user_id")
    private String user;
    private DateTime routeBeginTime;
    private DateTime routeEndTime;

    @OneToOne
    @Column(name = "vehicle_id")
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeCollection")
    @OrderBy(value = "routeOrder")
    private List<RouteCollectionAction> routeActions = new ArrayList<>();

    private int routeId;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public DateTime getRouteBeginTime() {
        return routeBeginTime;
    }

    public void setRouteBeginTime(DateTime routeBeginTime) {
        this.routeBeginTime = routeBeginTime;
    }

    public DateTime getRouteEndTime() {
        return routeEndTime;
    }

    public void setRouteEndTime(DateTime routeEndTime) {
        this.routeEndTime = routeEndTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<RouteCollectionAction> getRouteActions() {
        return routeActions;
    }

    public void setRouteActions(List<RouteCollectionAction> routeActions) {
        this.routeActions = routeActions;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
