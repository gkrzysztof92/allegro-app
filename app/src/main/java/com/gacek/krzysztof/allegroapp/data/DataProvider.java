package com.gacek.krzysztof.allegroapp.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gacek.krzysztof.allegroapp.util.Utils;

import static com.gacek.krzysztof.allegroapp.data.DataContract.*;
import static com.gacek.krzysztof.allegroapp.data.DataContract.ItemsEntry.TABLE_NAME;
import static com.gacek.krzysztof.allegroapp.data.DataContract.ItemsEntry._ID;

public class DataProvider extends ContentProvider {

    private static final int CATEGORIES = 1;
    private static final int CATEGORIES_ID = 2;
    private static final int ITEMS = 3;
    private static final int ITEMS_ID = 4;
    private static final int PHOTOS = 5;
    private static final int PHOTOS_ID = 6;
    private static final int ITEM_THUMB = 7;
    private static final int ITEMS_ATTRIBUTE = 8;
    private static final int ITEMS_ATTRIBUTE_ID = 9;

    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CATEGORIES, CATEGORIES);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CATEGORIES + "/#", CATEGORIES_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_ITEMS, ITEMS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_ITEMS + "/#", ITEMS_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PHOTOS, PHOTOS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PHOTOS + "/#", PHOTOS_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_THUMB, ITEM_THUMB);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_ITEM_ATTRIBUTE, ITEMS_ATTRIBUTE);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_ITEM_ATTRIBUTE + "/#", ITEMS_ATTRIBUTE_ID);
    }

    private DatabaseHelper helper;

    @Override
    public boolean onCreate() {
        helper = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match) {
            case CATEGORIES:
                cursor = queryAll(projection, selection, selectionArgs, sortOrder,
                            CategoriesEntry.TABLE_NAME);
                break;
            case ITEMS:
                cursor = queryAll(projection, selection, selectionArgs, sortOrder,
                            ItemsEntry.TABLE_NAME);
                break;
            case PHOTOS:
                cursor = queryAll(projection, selection, selectionArgs, sortOrder,
                        PhotosEntry.TABLE_NAME);
                break;
            case ITEM_THUMB:
                cursor = queryAll(projection, selection, selectionArgs, sortOrder,
                        ItemsEntry.TABLE_NAME + " LEFT OUTER JOIN " + PhotosEntry.TABLE_NAME +
                        " ON " + Utils.concat(TABLE_NAME, _ID, ".") + " = " + Utils.concat(PhotosEntry.TABLE_NAME, PhotosEntry.COLUMN_ITEM_ID, "."));
                break;
            case ITEMS_ATTRIBUTE:
                cursor = queryAll(projection, selection, selectionArgs, sortOrder,
                        ItemsAttributeEntry.TABLE_NAME);
                break;
            case CATEGORIES_ID:
                cursor = queryById(uri, projection, selection, selectionArgs, sortOrder,
                            CategoriesEntry.TABLE_NAME);
                break;
            case ITEMS_ID:
                cursor = queryById(uri, projection, selection, selectionArgs, sortOrder,
                            ItemsEntry.TABLE_NAME);
                break;
            case PHOTOS_ID:
                cursor = queryById(uri, projection, selection, selectionArgs, sortOrder,
                        PhotosEntry.TABLE_NAME);
                break;
            case ITEMS_ATTRIBUTE_ID:
                cursor = queryById(uri, projection, selection, selectionArgs, sortOrder,
                        ItemsAttributeEntry.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri");
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case CATEGORIES:
                return insertRecord(uri, values, CategoriesEntry.TABLE_NAME);
            case  ITEMS:
                return insertRecord(uri, values, ItemsEntry.TABLE_NAME);
            case  PHOTOS:
                return insertRecord(uri, values, PhotosEntry.TABLE_NAME);
            case  ITEMS_ATTRIBUTE:
                return insertRecord(uri, values, ItemsAttributeEntry.TABLE_NAME);
            default:
                throw new IllegalArgumentException("Unknown Uri");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case CATEGORIES:
                return deleteRecord(uri, selection, selectionArgs,
                        CategoriesEntry.TABLE_NAME);
            case ITEMS:
                return deleteRecord(uri, selection, selectionArgs,
                        ItemsEntry.TABLE_NAME);
            case PHOTOS:
                return deleteRecord(uri, selection, selectionArgs,
                        PhotosEntry.TABLE_NAME);
            case ITEMS_ATTRIBUTE:
                return deleteRecord(uri, selection, selectionArgs,
                        ItemsAttributeEntry.TABLE_NAME);
            case CATEGORIES_ID:
                selection = CategoriesEntry._ID + " = ?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return deleteRecord(uri, selection, selectionArgs,
                        CategoriesEntry.TABLE_NAME);
            case ITEMS_ID:
                selection = ItemsEntry._ID + " = ?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return deleteRecord(uri, selection, selectionArgs,
                        ItemsEntry.TABLE_NAME);
            case PHOTOS_ID:
                selection = PhotosEntry._ID + " = ?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return deleteRecord(uri, selection, selectionArgs,
                        PhotosEntry.TABLE_NAME);
            case ITEMS_ATTRIBUTE_ID:
                selection = ItemsAttributeEntry._ID + " = ?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return deleteRecord(uri, selection, selectionArgs,
                        ItemsAttributeEntry.TABLE_NAME);
            default:
                throw new IllegalArgumentException("Unknown Uri");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case CATEGORIES:
                return updateRecord(uri, values, selection, selectionArgs,
                        CategoriesEntry.TABLE_NAME);
            case ITEMS:
                return updateRecord(uri, values, selection, selectionArgs,
                        ItemsEntry.TABLE_NAME);
            case PHOTOS:
                return updateRecord(uri, values, selection, selectionArgs,
                        PhotosEntry.TABLE_NAME);
            case ITEMS_ATTRIBUTE:
                return updateRecord(uri, values, selection, selectionArgs,
                        ItemsAttributeEntry.TABLE_NAME);
            case  CATEGORIES_ID:
                selection = CategoriesEntry._ID + " = ?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateRecord(uri, values, selection, selectionArgs,
                        CategoriesEntry.TABLE_NAME);
            case  ITEMS_ID:
                selection = ItemsEntry._ID + " = ?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateRecord(uri, values, selection, selectionArgs,
                        ItemsEntry.TABLE_NAME);
            case  PHOTOS_ID:
                selection = PhotosEntry._ID + " = ?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateRecord(uri, values, selection, selectionArgs,
                        PhotosEntry.TABLE_NAME);
            case  ITEMS_ATTRIBUTE_ID:
                selection = ItemsAttributeEntry._ID + " = ?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateRecord(uri, values, selection, selectionArgs,
                        ItemsAttributeEntry.TABLE_NAME);
            default:
                throw new IllegalArgumentException("Unknown Uri");
        }
    }

    private Cursor queryAll(String[] projection, String selection, String[] selectionArgs,
                            String sortOrder, String tableName) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(tableName, projection, selection,
                selectionArgs, null, null, sortOrder);
        return cursor;
    }

    private Cursor queryById(Uri uri, String[] projection, String selection, String[] selectionArgs,
                             String sortOrder, String tableName) {
        selection = CategoriesEntry._ID + " = ?";
        selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
        return queryAll(projection, selection, selectionArgs, sortOrder, tableName);
    }

    private Uri insertRecord(Uri uri, ContentValues values, String tableName) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insert(tableName, null, values);
        if (id == -1) {
            Log.e("DataProvider", "Error insert " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    private int updateRecord(Uri uri, ContentValues values, String selection,
                             String[] selectionArg, String tableName) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int id = db.update(tableName, values, selection, selectionArg);
        if (id == 0) {
            Log.e("Error", "Update error for uri " + uri);
            return -1;
        }
        return id;
    }

    private int deleteRecord(Uri uri, String selection, String[] selectionArgs,
                             String tableName) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int id = db.delete(tableName, selection, selectionArgs);
        if (id == 0) {
            Log.d("Error", "Delete error for uri " + uri);
            return -1;
        }
        return id;
    }

}
