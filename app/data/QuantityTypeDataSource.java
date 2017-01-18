package data;

import model.PickupType;
import model.QuantityType;
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

public class QuantityTypeDataSource implements CacheableList, DataSource {
    @Override
    public List getList(AuthenticationInfo authenticationInfo, Parameters params) {
        List list = new List();
        QuantityType.find.all().forEach(item -> list.addListItem(item.toListItem()));
        return list;
    }

    @Override
    public boolean isListContentGlobal() {
        return true;
    }

    @Override
    public Set<ListServiceConfigurationAttribute> getListServiceAttributes() {
        return QuantityType.getListAttributes();
    }

    @Override
    public String getServiceName() {
        return "Quantity Type";
    }

    @Override
    public String getServiceDescription() {
        return getServiceName();
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getAttributes() {
            return QuantityType.getServiceAttributes();
    }

    @Override
    public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        DataSet dataSet = newEmptyDataSet();
        QuantityType.find.all().forEach(type -> type.copyTo(dataSet.addNewDataSetItem()));
        return dataSet;
    }

    @Override
    public DataSetItem getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        QuantityType quantityType = QuantityType.find.byId(Integer.parseInt(id));
        if (quantityType == null) throw new RuntimeException("Quantity Type not found");
        return quantityType.copyTo(new DataSetItem(getAttributes()));
    }

    @Override
    public RecordActionResponse updateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        QuantityType quantityType = QuantityType.find.byId(Integer.parseInt(dataSetItem.getPrimaryKey()));
        if (quantityType == null) throw new RuntimeException("Quantity Type not found");
        quantityType.copyFrom(dataSetItem);
        DataSetItem updateItem = new DataSetItem(getAttributes());
        quantityType.copyTo(updateItem);
        return new RecordActionResponse.Builder()
                .withMessage("Quantity Type updated")
                .withRecord(updateItem)
                .build();
    }

    @Override
    public RecordActionResponse createRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        QuantityType quantityType = new QuantityType();
        quantityType.copyFrom(dataSetItem);
        DataSetItem updateItem = new DataSetItem(getAttributes());
        quantityType.copyTo(updateItem);
        return new RecordActionResponse.Builder()
                .withMessage("Quantity Type updated")
                .withRecord(updateItem)
                .build();
    }
}

