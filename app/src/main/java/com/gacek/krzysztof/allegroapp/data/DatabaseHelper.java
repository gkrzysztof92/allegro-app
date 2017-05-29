package com.gacek.krzysztof.allegroapp.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.gacek.krzysztof.allegroapp.data.DataContract.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "allegroapp.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_CATEGORIES_CREATE =
            "CREATE TABLE " + CategoriesEntry.TABLE_NAME + " ( " +
                    CategoriesEntry._ID + " INTEGER PRIMARY KEY, " +
                    CategoriesEntry.COLUMN_NAME + " TEXT, " +
                    CategoriesEntry.COLUMN_PARENT + " INTEGER, " +
                    CategoriesEntry.COLUMN_POSITION + " INTEGER " +
                    ")";
    private static final String TABLE_ITEMS_CREATE =
            "CREATE TABLE " + ItemsEntry.TABLE_NAME + " ( " +
                    ItemsEntry._ID + " INTEGER PRIMARY KEY, " +
                    ItemsEntry.COLUMN_NAME + " TEXT, " +
                    ItemsEntry.COLUMN_CATEGORY_ID + " INTEGER, " +
                    ItemsEntry.COLUMN_CATEGORY_PATH + " TEXT, " +
                    ItemsEntry.COLUMN_DESCRIPTION + " TEXT, " +
                    ItemsEntry.COLUMN_QUANTITY + " INTEGER, " +
                    ItemsEntry.COLUMN_QUANTITY_TYPE + " TEXT, " +
                    ItemsEntry.COLUMN_QUANTITY_TYPE_ID + " INTEGER, " +
                    ItemsEntry.COLUMN_CREATION_DATE + " TEXT " +
                    ")";
    private static final String TABLE_PHOTOS_CREATE =
            "CREATE TABLE " + PhotosEntry.TABLE_NAME + " ( " +
                    PhotosEntry._ID + " INTEGER PRIMARY KEY, " +
                    PhotosEntry.COLUMN_ITEM_ID + " INTEGER, " +
                    PhotosEntry.COLUMN_PHOTO_DATA + " BLOB, " +
                    PhotosEntry.COLUMN_PRIMARY_IMAGE + " TEXT " +
                    ")";
    private static final String TABLE_ITEMS_ATTRIBUTE_CREATE =
            "CREATE TABLE " + ItemsAttributeEntry.TABLE_NAME + " ( " +
                    ItemsAttributeEntry._ID + " INTEGER PRIMARY KEY, " +
                    ItemsAttributeEntry.COLUMN_ITEM_ID + " INTEGER, " +
                    ItemsAttributeEntry.COLUMN_ATTRIBUTE_ID + " INTEGER, " +
                    ItemsAttributeEntry.COLUMN_ATTRIBUTE_NAME + " TEXT, " +
                    ItemsAttributeEntry.COLUMN_ATTRIBUTE_TYPE + " INTEGER, " +
                    ItemsAttributeEntry.COLUMN_ATTRIBUTE_VALUE + " TEXT, " +
                    ItemsAttributeEntry.COLUMN_ATTRIBUTE_VALUE_TEXT + " TEXT " +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CATEGORIES_CREATE);
        db.execSQL(TABLE_ITEMS_CREATE);
        db.execSQL(TABLE_PHOTOS_CREATE);
        db.execSQL(TABLE_ITEMS_ATTRIBUTE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PhotosEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemsAttributeEntry.TABLE_NAME);
        onCreate(db);
    }

}
