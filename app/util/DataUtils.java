package util;

import model.ModelAttribute;
import org.joda.time.DateTime;
import sdk.data.DataSetItem;
import sdk.models.Location;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by eddie on 11/21/16.
 */
public class DataUtils {

    public static DateTime dateTimeFromSQLDate(Date date) {
        if ( date == null ) return null;
        return new DateTime(date.getTime());
    }

    public static void setDoubleForDataSetItem(DataSetItem dataSetItem, ResultSet resultSet, ModelAttribute modelAttribute) throws SQLException {
        double doubleValue = resultSet.getDouble(modelAttribute.getColumnName());
        if (!resultSet.wasNull()) {
            dataSetItem.setDoubleForAttributeIndex(doubleValue, modelAttribute.getAttributeIndex());
        }
    }

    public static void setIntForDataSetItem(DataSetItem dataSetItem, ResultSet resultSet, ModelAttribute modelAttribute) throws SQLException {
        int intValue = resultSet.getInt(modelAttribute.getColumnName());
        if (!resultSet.wasNull()) {
            dataSetItem.setDoubleForAttributeIndex(intValue, modelAttribute.getAttributeIndex());
        }
    }

    public static Timestamp currentSqlDateTime(){
        Calendar calendar = Calendar.getInstance();
        return new java.sql.Timestamp(calendar.getTime().getTime());
    }

    public static Date currentSqlDate(){
        java.util.Date myDate = new java.util.Date();
        return new java.sql.Date(myDate.getTime());
    }

}
