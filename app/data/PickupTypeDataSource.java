package data;

import model.PickupType;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.RecordActionResponse;
import sdk.datasources.base.CacheableList;
import sdk.datasources.base.DataSource;
import sdk.list.List;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;
import java.util.Set;

public class PickupTypeDataSource implements CacheableList, DataSource {
    @Override
    public List getList(AuthenticationInfo authenticationInfo, Parameters params) {
        List list = new List();
        PickupType.find.all().forEach(item -> list.addListItem(item.toListItem()));
        return list;
    }

    @Override
    public boolean isListContentGlobal() {
        return true;
    }

    @Override
    public Set<ListServiceConfigurationAttribute> getListServiceAttributes() {
        return PickupType.getListAttributes();
    }

    @Override
    public String getServiceName() {
        return "Pickup Types";
    }

    @Override
    public String getServiceDescription() {
        return getServiceName();
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getAttributes() {
        return PickupType.getServiceAttributes();
    }

    @Override
    public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        DataSet dataSet = newEmptyDataSet();
        PickupType.find.all().forEach(type -> type.copyTo(dataSet.addNewDataSetItem()));
        return dataSet;
    }

    @Override
    public DataSetItem getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        PickupType pickupType = PickupType.find.byId(Integer.parseInt(id));
        if (pickupType == null) throw new RuntimeException("Pickup Type not found");
        return pickupType.copyTo(new DataSetItem(getAttributes()));
    }

    @Override
    public RecordActionResponse updateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        PickupType pickupType = PickupType.find.byId(Integer.parseInt(dataSetItem.getPrimaryKey()));
        if (pickupType == null) throw new RuntimeException("Pickup Type not found");
        pickupType.copyFrom(dataSetItem);
        DataSetItem updateItem = new DataSetItem(getAttributes());
        pickupType.copyTo(updateItem);
        return new RecordActionResponse.Builder()
                .withMessage("Pickup Type updated")
                .withRecord(updateItem)
                .build();
    }

    @Override
    public RecordActionResponse createRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        PickupType pickupType = new PickupType();
        pickupType.copyFrom(dataSetItem);
        DataSetItem updateItem = new DataSetItem(getAttributes());
        pickupType.copyTo(updateItem);
        return new RecordActionResponse.Builder()
                .withMessage("Pickup Type updated")
                .withRecord(updateItem)
                .build();
    }
}
