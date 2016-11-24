package data;

import model.PickupType;
import sdk.datasources.base.CacheableList;
import sdk.list.List;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Set;

public class PickupTypeDataSource implements CacheableList {
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
}
