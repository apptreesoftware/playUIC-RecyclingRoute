package data;

import model.Vehicle;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.RecordActionResponse;
import sdk.datasources.base.DataSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;

/**
 * Created by Matthew Smith on 11/22/16.
 * Copyright AppTree Software, Inc.
 */
public class VehicleDataSource implements DataSource {
    @Override
    public String getServiceDescription() {
        return "Vehicles";
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getAttributes() {
        return Vehicle.getServiceAttributes();
    }

    @Override
    public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        DataSet dataSet = newEmptyDataSet();
        Vehicle.find.all().forEach(vehicle -> {vehicle.copyInto(dataSet.addNewDataSetItem());});
        return dataSet;
    }

    @Override
    public DataSetItem getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        DataSetItem dataSetItem = new DataSetItem(getAttributes());
        Vehicle vehicle = Vehicle.find.byId(Integer.parseInt(id));
        if ( vehicle == null ) throw new RuntimeException("Vehicle not found");
        vehicle.copyInto(dataSetItem);
        return dataSetItem;
    }

    @Override
    public RecordActionResponse createRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        Vehicle vehicle = new Vehicle();
        vehicle.copyFrom(dataSetItem);
        vehicle.insert();
        DataSetItem responseItem = new DataSetItem(getAttributes());
        return new RecordActionResponse.Builder()
                .withMessage("Vehicle Added")
                .withRecord(vehicle.copyInto(responseItem))
                .build();
    }

    @Override
    public RecordActionResponse updateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        Vehicle vehicle = new Vehicle();
        vehicle.copyFrom(dataSetItem);
        vehicle.update();
        DataSetItem responseItem = new DataSetItem(getAttributes());
        return new RecordActionResponse.Builder()
                .withMessage("Vehicle Updated")
                .withRecord(vehicle.copyInto(responseItem))
                .build();
    }
}
