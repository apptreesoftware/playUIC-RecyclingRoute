package model;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import sdk.list.ListItem;
import sdk.list.ListServiceConfigurationAttribute;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by matthew on 11/24/16.
 */
@Entity
@Table(name = "grr_route_exceptions")
public class RouteException extends Model {
    @Id
    @Column(name = "route_action_exception")
    private String exception;
    private DateTime enterDate;
    private DateTime modifyDate;

    public static Model.Finder<String, RouteException> find = new Model.Finder<>(RouteException.class);

    public static Set<ListServiceConfigurationAttribute> getListAttributes() {
        return new HashSet<>();
    }

    public ListItem toListItem() {
        return new ListItem(exception);
    }

    public RouteException() {}
    public RouteException(ListItem listItem) {
        this.exception = listItem.id;
    }

}
