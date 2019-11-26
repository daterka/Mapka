package com.example.mapka.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.mapka.models.LocalizationModel;

import static com.example.mapka.database.DBStrings.*;


public class DataBaseAdapter {

    private SQLiteDatabase db;
    private Context context;
    private DataBaseHelper dataBaseHelper;

    public DataBaseAdapter(Context context) {
        this.context = context;
    }

    public DataBaseAdapter open(){
        this.dataBaseHelper = new DataBaseHelper(this.context, DATABASE_NAME, null, DATABASE_VERSION);
        try{
            this.db = this.dataBaseHelper.getWritableDatabase();
        } catch (SQLException e){
            this.db = this.dataBaseHelper.getReadableDatabase();
        }
        return this;
    }

    //TODO exception hendling correctness ?
    public boolean close(){
        try{
            this.dataBaseHelper.close();
            return true;
        } catch(SQLException e){
            return false;
        }
    }

    //TODO how to handle date & time ? nullColumnHack - null ??
    //return ID of the last inserted record or -1 if insert failure
    public long insertLocalization(String date, String time, String latitude, String longitude){
        ContentValues newLocalizationValues = new ContentValues();
        newLocalizationValues.put(LOCALIZATION_HISTORY_COLUMN_DATE, date);
        newLocalizationValues.put(LOCALIZATION_HISTORY_COLUMN_TIME, time);
        newLocalizationValues.put(LOCALIZATION_HISTORY_COLUMN_LATITUDE, latitude);
        newLocalizationValues.put(LOCALIZATION_HISTORY_COLUMN_LONGITUDE, longitude);
        return db.insert(LOCALIZATION_HISTORY_TABLE_NAME, null, newLocalizationValues);
    }

    public boolean updateLocalization(LocalizationModel localization){
        long id = localization.getId();
        String date = localization.getDate();
        String time = localization.getTime();
        String latitude = localization.getLatitude();
        String longitude = localization.getLongitude();
        return updateLocalization(id, date, time, latitude, longitude);
    }

    public boolean updateLocalization(long id, String date, String time, String latitude, String longitude) {
        String where = LOCALIZATION_HISTORY_COLUMN_ID + "=" + id;
        ContentValues updateLocalizationValues = new ContentValues();
        updateLocalizationValues.put(LOCALIZATION_HISTORY_COLUMN_DATE, date);
        updateLocalizationValues.put(LOCALIZATION_HISTORY_COLUMN_TIME, time);
        updateLocalizationValues.put(LOCALIZATION_HISTORY_COLUMN_LATITUDE, latitude);
        updateLocalizationValues.put(LOCALIZATION_HISTORY_COLUMN_LONGITUDE, longitude);
        return db.update(LOCALIZATION_HISTORY_TABLE_NAME, updateLocalizationValues, where,null) > 0;
    }

    //TODO deleting using parameters other than id
    public boolean deleteLocalization(long id){
        String where = LOCALIZATION_HISTORY_COLUMN_ID + "=" + id;
        return db.delete(LOCALIZATION_HISTORY_TABLE_NAME, where, null) > 0;
    }

    public Cursor getAllLocalizations(){
        String[] columns = {
                LOCALIZATION_HISTORY_COLUMN_ID,
                LOCALIZATION_HISTORY_COLUMN_DATE,
                LOCALIZATION_HISTORY_COLUMN_TIME,
                LOCALIZATION_HISTORY_COLUMN_LATITUDE,
                LOCALIZATION_HISTORY_COLUMN_LONGITUDE};
        //TODO add orderBy oprion
        return db.query(LOCALIZATION_HISTORY_TABLE_NAME, columns, null, null, null, null, null);
    }

    public LocalizationModel getLocalization(long id){
        String[] columns = {
                LOCALIZATION_HISTORY_COLUMN_ID,
                LOCALIZATION_HISTORY_COLUMN_DATE,
                LOCALIZATION_HISTORY_COLUMN_TIME,
                LOCALIZATION_HISTORY_COLUMN_LATITUDE,
                LOCALIZATION_HISTORY_COLUMN_LONGITUDE};
        String where = LOCALIZATION_HISTORY_COLUMN_ID + "=" + id;
        Cursor cursor = db.query(LOCALIZATION_HISTORY_TABLE_NAME, columns, where, null, null, null, null);
        LocalizationModel localization = null;

        //TODO add exception handling
        if(cursor!= null && cursor.moveToFirst()){
            String date = cursor.getString(LOCALIZATION_HISTORY_COLUMN_DATE_NO);
            String time = cursor.getString(LOCALIZATION_HISTORY_COLUMN_TIME_NO);
            String latitude = cursor.getString(LOCALIZATION_HISTORY_COLUMN_LATITUDE_NO);
            String longitude = cursor.getString(LOCALIZATION_HISTORY_COLUMN_LATITUDE_NO);
            localization = new LocalizationModel(id, date, time, latitude, longitude);
        }
        return localization;
    }

    //TODO try to pull it off the DataBaseAdapter calss
    private static class DataBaseHelper extends SQLiteOpenHelper{
        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
            super(context, name, cursorFactory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBStrings.DB_CREATE_LOCALIZATION_HISTORY_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + LOCALIZATION_HISTORY_TABLE_NAME + " ver." + DATABASE_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DB_DROP_LOCALIZATION_HISTORY_TABLE);

            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + LOCALIZATION_HISTORY_TABLE_NAME + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");
        }
    }
}
