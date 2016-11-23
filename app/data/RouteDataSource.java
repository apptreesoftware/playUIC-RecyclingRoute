package data;

import model.Route;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.RecordActionResponse;
import sdk.datasources.base.DataSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;

/**
 * Created by eddie on 11/21/16.
 */
public class RouteDataSource implements DataSource {
    @Override
    public String getServiceDescription() {
        return "Routes";
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getAttributes() {
        return Route.getServiceAttributes();
    }

    @Override
    public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        DataSet dataSet = newEmptyDataSet();
        Route.find.all().forEach(route -> route.copyInto(dataSet.addNewDataSetItem()));
        return dataSet;
    }

    @Override
    public DataSetItem getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        Route route = Route.find.byId(Integer.parseInt(id));
        if (route == null) {
            throw new RuntimeException("Route not found");
        }
        return route.copyInto(new DataSetItem(getAttributes()));
    }

    @Override
    public RecordActionResponse updateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        Route route = Route.find.byId(Integer.parseInt(dataSetItem.getPrimaryKey()));
        if ( route == null ) throw new RuntimeException("Route not found");
        route.copyFrom(dataSetItem);
        DataSetItem updatedItem = new DataSetItem(getAttributes());
        route.copyInto(updatedItem);
        return new RecordActionResponse.Builder()
                .withMessage("Submission successful")
                .withRecord(updatedItem)
                .build();
    }
}
