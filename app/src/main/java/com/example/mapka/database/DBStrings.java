package com.example.mapka.database;


 //TODO figure out better way to do that

public class DBStrings {
    //DATABASE
    public static final String DEBUG_TAG = "SqLiteTodoManager";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MapkaDB.db";

    //TABLES
    public static final String LOCALIZATION_HISTORY_TABLE_NAME = "localization_history";
    public static final String LOCALIZATION_HISTORY_COLUMN_ID = "id";
    public static final int LOCALIZATION_HISTORY_COLUMN_ID_NO = 0;
    public static final String LOCALIZATION_HISTORY_COLUMN_ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String LOCALIZATION_HISTORY_COLUMN_DATE = "date";
    public static final int LOCALIZATION_HISTORY_COLUMN_DATE_NO = 1;
    public static final String LOCALIZATION_HISTORY_COLUMN_DATE_OPTIONS = "TEXT NOT NULL";
    public static final String LOCALIZATION_HISTORY_COLUMN_TIME = "time";
    public static final int LOCALIZATION_HISTORY_COLUMN_TIME_NO = 2;
    public static final String LOCALIZATION_HISTORY_COLUMN_TIME_OPTIONS = "TEXT NOT NULL";
    public static final String LOCALIZATION_HISTORY_COLUMN_LATITUDE = "latitude";
    public static final int LOCALIZATION_HISTORY_COLUMN_LATITUDE_NO = 3;
    public static final String LOCALIZATION_HISTORY_COLUMN_LATITUDE_OPTIONS = "TEXT NOT NULL";
    public static final String LOCALIZATION_HISTORY_COLUMN_LONGITUDE = "longitude";
    public static final int LOCALIZATION_HISTORY_COLUMN_LONGITUDE_NO = 4;
    public static final String LOCALIZATION_HISTORY_COLUMN_LONGITUDE_OPTIONS = "TEXT NOT NULL";
    public static final String LOCALIZATION_HISTORY_COLUMN_NAME = "name";
    public static final int LOCALIZATION_HISTORY_COLUMN_NAME_NO = 5;
    public static final String LOCALIZATION_HISTORY_COLUMN_NAME_OPTIONS = "TEXT NOT NULL";


//    public static final String LOCALIZATION_HISTORY_COLUMN_COUNTRY = "country";
//    public static final String LOCALIZATION_HISTORY_COLUMN_CITY = "city";
//    public static final String LOCALIZATION_HISTORY_COLUMN_STREET = "street";
//    public static final String LOCALIZATION_HISTORY_COLUMN_POSTCODE = "postcode";
//    public static final String LOCALIZATION_HISTORY_COLUMN_HOUSE_NUMBER = "house_number";

    //SQL QUERIES
    public static final String DB_CREATE_LOCALIZATION_HISTORY_TABLE =
            "CREATE TABLE " + LOCALIZATION_HISTORY_TABLE_NAME + "( " +
                    LOCALIZATION_HISTORY_COLUMN_ID + " "  +
                    LOCALIZATION_HISTORY_COLUMN_ID_OPTIONS + ", " +
                    LOCALIZATION_HISTORY_COLUMN_DATE + " " +
                    LOCALIZATION_HISTORY_COLUMN_DATE_OPTIONS  + ", " +
                    LOCALIZATION_HISTORY_COLUMN_TIME + " " +
                    LOCALIZATION_HISTORY_COLUMN_TIME_OPTIONS + ", " +
                    LOCALIZATION_HISTORY_COLUMN_LATITUDE + " " +
                    LOCALIZATION_HISTORY_COLUMN_LATITUDE_OPTIONS + ", " +
                    LOCALIZATION_HISTORY_COLUMN_LONGITUDE + " " +
                    LOCALIZATION_HISTORY_COLUMN_LONGITUDE_OPTIONS + ", " +
                    LOCALIZATION_HISTORY_COLUMN_NAME + " " +
                    LOCALIZATION_HISTORY_COLUMN_NAME_OPTIONS + ");";

    public static final String DB_DROP_LOCALIZATION_HISTORY_TABLE = "DROP TABLE IF EXISTS " + LOCALIZATION_HISTORY_TABLE_NAME;

}
