package controllers;

import data.RouteInspectionDataSource;
import model.Route;
import model.RouteCollection;
import model.Vehicle;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Matthew Smith on 12/2/16.
 * Copyright AppTree Software, Inc.
 */
public class TestController extends Controller {
    public Result createRouteCollection(int routeId, int vehicleId) {
        Route route = Route.find.byId(routeId);
        Vehicle vehicle = Vehicle.find.byId(vehicleId);
        if ( route == null || vehicle == null) {
            badRequest("A valid route or vehicle were not found");
        }
        RouteCollection routeCollection = RouteInspectionDataSource.newRouteCollectionFromRoute(route, vehicle, "Test User");
        return ok(Json.toJson(routeCollection));
    }
}
