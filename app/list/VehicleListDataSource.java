package list;

import model.Vehicle;
import org.joda.time.DateTime;
import sdk.datasources.base.CacheableList;
import sdk.list.List;
import sdk.list.ListItem;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by eddie on 11/18/16.
 */
public class VehicleListDataSource implements CacheableList {
    @Override
    public List getList(AuthenticationInfo authenticationInfo, Parameters params) {

        java.util.List<ListItem> listItems = Vehicle.find.all()
                .stream()
                .map(Vehicle::toListItem)
                .collect(Collectors.toList());

        java.util.List<Vehicle> vehicles = Vehicle.find.all();

        List list = new List();
        for ( Vehicle vehicle : vehicles ) {
            ListItem listItem = vehicle.toListItem();
            list.addListItem(listItem);
        }
        return list;
    }

    @Override
    public boolean isListContentGlobal() {
        return true;
    }

    @Override
    public Set<ListServiceConfigurationAttribute> getListServiceAttributes() {
        return Vehicle.getListAttributes();
    }

    @Override
    public String getServiceName() {
        return "Vehicles";
    }
}
