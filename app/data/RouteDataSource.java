package data;

import model.Route;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
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

    //    enum Action {
//        CREATE, UPDATE, DELETE
//    }
//
//    @Override
//    public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
//        return getDatabase().withConnection(connection -> {
//            DataSet dataSet = newEmptyDataSet();
//            fetch(params, dataSet, connection);
//            return dataSet;
//        });
//    }
//
//    @Override
//    public DataSetItem getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
//        return getDatabase().withConnection(connection -> {
//            DataSet dataSet = newEmptyDataSet();
//            fetchByID(id, parameters, dataSet, connection);
//            return dataSet.getDataSetItems().get(0);
//        });
//    }
//
//    @Override
//    public String getServiceDescription() {
//        return "Route";
//    }
//
//    @Override
//    public Collection<ServiceConfigurationAttribute> getAttributes() {
//        return Route.getAttributes();
//    }
//
//    @Override
//    public RecordActionResponse createRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
//        return getDatabase().withConnection(connection -> {
//            executeRouteAction(Action.CREATE,dataSetItem, params, connection);
//            return new RecordActionResponse.Builder()
//                    .withMessage("Route has been created.")
//                    .showAsAlert(true)
//                    .build();
//        });
//    }
//
//    @Override
//    public RecordActionResponse updateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
//        return getDatabase().withConnection(connection -> {
//            executeRouteAction(Action.UPDATE,dataSetItem, params, connection);
//            DataSetItem updateItem = getRecord(dataSetItem.getPrimaryKey(), authenticationInfo, params);
//            return new RecordActionResponse.Builder()
//                    .withMessage("Route has been updated.")
//                    .withRecord(updateItem)
//                    .showAsAlert(true)
//                    .build();
//        });
//    }
//
//    void executeRouteAction(Action action,DataSetItem dataSetItem, Parameters params, Connection connection) {
//        try {
//            connection.setAutoCommit(false);
//            if (action == Action.CREATE) {
//                createRoute(dataSetItem, params, connection);
//            } else if (action == Action.UPDATE) {
//                updateRoute(dataSetItem, params, connection);
//            }
//            List<DataSetItem> routeStops = dataSetItem.getDataSetItemsAtIndex(Route.RouteStops.getAttributeIndex());
//            if (routeStops != null) {
//                for (DataSetItem route : routeStops) {
//                    if (route.getCRUDStatus() == Create) {
//                        createRouteStop(route, params, connection);
//                    } else if (route.getCRUDStatus() == Update) {
//                        updateRouteStop(route, params, connection);
//                    } else if (route.getCRUDStatus() == Delete) {
//                        deleteRouteStop(route, params, connection);
//                    }
//                }
//            }
//            connection.commit();
//        } catch (Exception exception) {
//            throw new RuntimeException(exception);
//        }
//    }
//
//    void createRoute(DataSetItem dataSetItem, Parameters params, Connection connection) {
//        String SQL = "INSERT INTO GRR_ROUTE (ROUTE_ID,ROUTE_DESC,CITY,STATE,ZIP,ENTER_DATE) VALUES (nextval('route_id_seq'),?,?,?,?,?)";
//        try {
//            PreparedStatement statement = connection.prepareStatement(SQL);
//            statement.setString(1, dataSetItem.getStringAttributeAtIndex(Route.RouteDescription.getAttributeIndex()));
//            statement.setString(2, dataSetItem.getStringAttributeAtIndex(Route.City.getAttributeIndex()));
//            statement.setString(3, dataSetItem.getStringAttributeAtIndex(Route.State.getAttributeIndex()));
//            statement.setString(4, dataSetItem.getStringAttributeAtIndex(Route.Zip.getAttributeIndex()));
//            statement.setTimestamp(5, DataUtils.currentSqlDateTime());
//            statement.execute();
//            statement.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    void updateRoute(DataSetItem dataSetItem, Parameters params, Connection connection) {
//        String SQL = "UPDATE GRR_ROUTE SET ROUTE_DESC = ?, CITY = ?,STATE = ?,ZIP = ?,MODIFY_DATE = ? WHERE ROUTE_ID= ?";
//        try {
//            PreparedStatement statement = connection.prepareStatement(SQL);
//            statement.setString(1, dataSetItem.getStringAttributeAtIndex(Route.RouteDescription.getAttributeIndex()));
//            statement.setString(2, dataSetItem.getStringAttributeAtIndex(Route.City.getAttributeIndex()));
//            statement.setString(3, dataSetItem.getStringAttributeAtIndex(Route.State.getAttributeIndex()));
//            statement.setString(4, dataSetItem.getStringAttributeAtIndex(Route.Zip.getAttributeIndex()));
//            statement.setTimestamp(5, DataUtils.currentSqlDateTime());
//            statement.setInt(6, Integer.parseInt(dataSetItem.getPrimaryKey()));
//            statement.executeUpdate();
//            statement.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    void createRouteStop(DataSetItem dataSetItem, Parameters params, Connection connection) {
//        String SQL = "INSERT INTO GRR_ROUTE_STOP " +
//                "(ROUTE_STOP_ID,ROUTE_ID,ROUTE_STOP_ORDER,ROUTE_STOP_NAME,STREET_ADDRESS1,STREET_ADDRESS2," +
//                "CITY,STATE,ZIP,CONTACT_NAME,CONTACT_EMAIL,NOTIFY_CONTACT_ON_NEXT," +
//                "NOTIFY_CONTACT_ON_EXCEPTION,ROUTE_STOP_LAT,ROUTE_STOP_LON,ENTER_DATE) " +
//                "VALUES " +
//                "(nextval('route_stop_id_seq'),?,?,?,?,?," +
//                "?,?,?,?,?,?," +
//                "?,?,?,?)";
//        try {
//            PreparedStatement statement = connection.prepareStatement(SQL);
//            statement.setInt(1, dataSetItem.getIntAttributeAtIndex(RouteStop.RouteID.getAttributeIndex()));
//            statement.setInt(2, dataSetItem.getIntAttributeAtIndex(RouteStop.RouteStopOrder.getAttributeIndex()));
//            statement.setString(3, dataSetItem.getStringAttributeAtIndex(RouteStop.RouteStopName.getAttributeIndex()));
//            statement.setString(4, dataSetItem.getStringAttributeAtIndex(RouteStop.StreetAddress1.getAttributeIndex()));
//            statement.setString(5, dataSetItem.getStringAttributeAtIndex(RouteStop.StreetAddress2.getAttributeIndex()));
//
//            statement.setString(6, dataSetItem.getStringAttributeAtIndex(RouteStop.City.getAttributeIndex()));
//            statement.setString(7, dataSetItem.getStringAttributeAtIndex(RouteStop.State.getAttributeIndex()));
//            statement.setString(8, dataSetItem.getStringAttributeAtIndex(RouteStop.Zip.getAttributeIndex()));
//            statement.setString(9, dataSetItem.getStringAttributeAtIndex(RouteStop.ContactName.getAttributeIndex()));
//            statement.setString(10, dataSetItem.getStringAttributeAtIndex(RouteStop.ContactEmail.getAttributeIndex()));
//            statement.setString(11, dataSetItem.getBoolValueAtIndex(RouteStop.NotifyContactOnNext.getAttributeIndex()) ? "Y" : "N");
//
//            statement.setString(12, dataSetItem.getBoolValueAtIndex(RouteStop.NotifyContactOnException.getAttributeIndex()) ? "Y" : "N");
//            dataSetItem.getOptionalLocationAtIndex(RouteStop.Coordinate.getAttributeIndex())
//                    .ifPresent(location -> {
//                        try {
//                            statement.setDouble(13, location.getLatitude());
//                            statement.setDouble(14, location.getLongitude());
//                        } catch (SQLException e) {
//                            throw new RuntimeException(e);
//                        }
//                    });
//            statement.setTimestamp(15, DataUtils.currentSqlDateTime());
//            statement.execute();
//            statement.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    void updateRouteStop(DataSetItem dataSetItem, Parameters params, Connection connection) {
//        String SQL =
//                "UPDATE GRR_ROUTE_STOP " +
//                        "SET ROUTE_ID = ? ," +
//                        "ROUTE_STOP_ORDER = ? ," +
//                        "ROUTE_STOP_NAME = ? ," +
//                        "STREET_ADDRESS1 = ? ," +
//                        "STREET_ADDRESS2 = ? ," +
//                        "CITY = ? ," +
//                        "STATE = ? ," +
//                        "ZIP = ? ," +
//                        "CONTACT_NAME = ? ," +
//                        "CONTACT_EMAIL = ? ," +
//                        "NOTIFY_CONTACT_ON_NEXT = ? ," +
//                        "NOTIFY_CONTACT_ON_EXCEPTION = ? ," +
//                        "ROUTE_STOP_LAT = ? ," +
//                        "ROUTE_STOP_LON = ? ," +
//                        "MODIFY_DATE = ? " +
//                        "WHERE ROUTE_STOP_ID= ?";
//
//        try {
//            PreparedStatement statement = connection.prepareStatement(SQL);
//            statement.setInt(1, dataSetItem.getIntAttributeAtIndex(RouteStop.RouteID.getAttributeIndex()));
//            statement.setInt(2, dataSetItem.getIntAttributeAtIndex(RouteStop.RouteStopOrder.getAttributeIndex()));
//            statement.setString(3, dataSetItem.getStringAttributeAtIndex(RouteStop.RouteStopName.getAttributeIndex()));
//            statement.setString(4, dataSetItem.getStringAttributeAtIndex(RouteStop.StreetAddress1.getAttributeIndex()));
//            statement.setString(5, dataSetItem.getStringAttributeAtIndex(RouteStop.StreetAddress2.getAttributeIndex()));
//
//            statement.setString(6, dataSetItem.getStringAttributeAtIndex(RouteStop.City.getAttributeIndex()));
//            statement.setString(7, dataSetItem.getStringAttributeAtIndex(RouteStop.State.getAttributeIndex()));
//            statement.setString(8, dataSetItem.getStringAttributeAtIndex(RouteStop.Zip.getAttributeIndex()));
//            statement.setString(9, dataSetItem.getStringAttributeAtIndex(RouteStop.ContactName.getAttributeIndex()));
//            statement.setString(10, dataSetItem.getStringAttributeAtIndex(RouteStop.ContactEmail.getAttributeIndex()));
//            statement.setString(11, dataSetItem.getBoolValueAtIndex(RouteStop.NotifyContactOnNext.getAttributeIndex()) ? "Y" : "N");
//
//            statement.setString(12, dataSetItem.getBoolValueAtIndex(RouteStop.NotifyContactOnException.getAttributeIndex()) ? "Y" : "N");
//            dataSetItem.getOptionalLocationAtIndex(RouteStop.Coordinate.getAttributeIndex())
//                    .ifPresent(location -> {
//                        try {
//                            statement.setDouble(13, location.getLatitude());
//                            statement.setDouble(14, location.getLongitude());
//                        } catch (SQLException e) {
//                            throw new RuntimeException(e);
//                        }
//                    });
//            statement.setTimestamp(15, DataUtils.currentSqlDateTime());
//            statement.setInt(16, dataSetItem.getIntAttributeAtIndex(RouteStop.RouteStopID.getAttributeIndex()));
//            statement.executeUpdate();
//            statement.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    void deleteRouteStop(DataSetItem dataSetItem, Parameters params, Connection connection) {
//        String SQL = "DELETE GRR_ROUTE_STOP WHERE ROUTE_STIP_ID = ? ";
//        try {
//            PreparedStatement statement = connection.prepareStatement(SQL);
//            statement.setInt(1, dataSetItem.getIntAttributeAtIndex(RouteStop.RouteStopID.getAttributeIndex()));
//            statement.execute();
//            statement.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    void fetch(Parameters params, DataSet dataSet, Connection connection) {
//        String sqlRoute = "SELECT * FROM GRR_ROUTE";
//        String sqlRouteStop = " SELECT * FROM GRR_ROUTE_STOP WHERE ROUTE_ID IN(SELECT ROUTE_ID FROM GRR_ROUTE)";
//        try {
//            //Route
//            PreparedStatement statementRoute = connection.prepareStatement(sqlRoute);
//            ResultSet routeResultSet = statementRoute.executeQuery();
//            //Route Stop
//            PreparedStatement statementRouteStop = connection.prepareStatement(sqlRouteStop);
//            ResultSet routeStopResultSet = statementRouteStop.executeQuery();
//            parseResults(dataSet, routeResultSet, routeStopResultSet);
//            statementRoute.close();
//            statementRouteStop.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    void fetchByID(String id, Parameters params, DataSet dataSet, Connection connection) {
//        String sqlRoute = "SELECT * FROM GRR_ROUTE WHERE ROUTE_ID = ?";
//        String sqlRouteStop = "SELECT * FROM GRR_ROUTE_STOP WHERE ROUTE_ID = ?";
//        try {
//            //Route
//            PreparedStatement statementRoute = connection.prepareStatement(sqlRoute);
//            statementRoute.setInt(1, Integer.parseInt(id));
//            ResultSet routeResultSet = statementRoute.executeQuery();
//            //Route Stop
//            PreparedStatement statementRouteStop = connection.prepareStatement(sqlRouteStop);
//            statementRouteStop.setInt(1, Integer.parseInt(id));
//            ResultSet routeStopResultSet = statementRouteStop.executeQuery();
//            parseResults(dataSet, routeResultSet, routeStopResultSet);
//            statementRoute.close();
//            statementRouteStop.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    void parseResults(DataSet dataSet, ResultSet routeResultSet, ResultSet routeStopResultSet) throws SQLException {
//        HashMap<String, DataSetItem> routeIDMap = new HashMap<>();
//
//        while (routeResultSet.next()) {
//
//            DataSetItem routeDataSetItem = dataSet.addNewDataSetItem();
//            Route.updateDataSetItemFromResultSet(routeDataSetItem, routeResultSet);
//            String routeID = routeDataSetItem.getPrimaryKey();
//            routeIDMap.put(routeID, routeDataSetItem);
//        }
//        while (routeStopResultSet.next()) {
//            String parentID = routeStopResultSet.getString(RouteStop.RouteID.getColumnName());
//            DataSetItem parentRoute = routeIDMap.get(parentID);
//            if (parentRoute == null) continue;
//            DataSetItem dataSetItem = parentRoute.addNewDataSetItemForAttributeIndex(Route.RouteStops.getAttributeIndex());
//
//            RouteStop.updateDataSetItemFromResultSet(dataSetItem, routeStopResultSet);
//        }
//    }

}
