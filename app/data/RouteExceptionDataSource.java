package data;

import model.RouteException;
import sdk.datasources.base.CacheableList;
import sdk.list.List;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Set;

public class RouteExceptionDataSource implements CacheableList {
    @Override
    public List getList(AuthenticationInfo authenticationInfo, Parameters params) {
        List list = new List();
        RouteException.find.all().forEach(exception -> list.addListItem(exception.toListItem()));
        return list;
    }

    @Override
    public boolean isListContentGlobal() {
        return true;
    }

    @Override
    public Set<ListServiceConfigurationAttribute> getListServiceAttributes() {
        return RouteException.getListAttributes();
    }

    @Override
    public String getServiceName() {
        return "Route Exceptions";
    }
}
