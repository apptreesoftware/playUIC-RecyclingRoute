package data;

import model.PickupType;
import model.QuantityType;
import sdk.datasources.base.CacheableList;
import sdk.list.List;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Set;

public class QuantityTypeDataSource implements CacheableList {
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
}
