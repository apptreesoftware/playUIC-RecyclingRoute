package model;

/**
 * Created by eddie on 11/21/16.
 */
public interface ModelAttribute {
    String getColumnName();
    int getAttributeIndex();
    int NON_ATTRIBUTE_INDEX = -1;
}