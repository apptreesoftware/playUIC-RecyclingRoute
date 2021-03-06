import com.google.inject.AbstractModule;
import data.*;
import sdk.AppTree;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure()
    {
        AppTree.registerDataSourceWithName("route",new RouteDataSource());
        AppTree.registerDataSourceWithName("vehicles", new VehicleDataSource());
        AppTree.registerListDataSourceWithName("pickuptypes", new PickupTypeDataSource());
        AppTree.registerListDataSourceWithName("quantitytypes", new QuantityTypeDataSource());
        AppTree.registerDataSourceWithName("Quantity", new QuantityTypeDataSource());
        AppTree.registerDataSourceWithName("types", new PickupTypeDataSource());
        AppTree.registerListDataSourceWithName("routeexceptions", new RouteExceptionDataSource());
        AppTree.registerListDataSourceWithName("routeslist" , new RouteDataSource());
        AppTree.registerListDataSourceWithName("vehiclelist", new VehicleDataSource());
        AppTree.registerInspectionSource("refuseroutes", new RouteInspectionDataSource());
    }

}
