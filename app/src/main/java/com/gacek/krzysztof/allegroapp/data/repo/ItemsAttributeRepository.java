package com.gacek.krzysztof.allegroapp.data.repo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.gacek.krzysztof.allegroapp.data.DataContract;
import com.gacek.krzysztof.allegroapp.model.Category;
import com.gacek.krzysztof.allegroapp.model.ItemAttribute;

import java.util.ArrayList;
import java.util.List;

import static com.gacek.krzysztof.allegroapp.data.DataContract.ItemsAttributeEntry.*;



public class ItemsAttributeRepository {

    private static ItemsAttributeRepository sItemsAttributeRepository;
    private ContentResolver contentResolver;

    private ItemsAttributeRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public static synchronized void initInstance(ContentResolver contentResolver) {
        if (sItemsAttributeRepository == null) {
            sItemsAttributeRepository = new ItemsAttributeRepository(contentResolver);
        }
    }

    public static synchronized ItemsAttributeRepository getInstance() {
        if (sItemsAttributeRepository == null) {
            throw new IllegalStateException("CategoriesRepository is not initialized");
        }
        return sItemsAttributeRepository;
    }

    public List<ItemAttribute> findByItemId(int itemId) {
        String selection = COLUMN_ITEM_ID + " = ?";
        String[] selectionArgs = { String.valueOf(itemId) };
        Cursor c = contentResolver.query(CONTENT_URI, PROJECTION, selection, selectionArgs, null);
        return mapCursorToItemAttribute(c);
    }

    public int removeByItemId(int itemId) {
        String selection = COLUMN_ITEM_ID + " = ?";
        String[] selectionArgs = { String.valueOf(itemId) };
        int result = contentResolver.delete(CONTENT_URI, selection, selectionArgs);
        return result;
    }

    public ItemAttribute save(ItemAttribute itemAttribute) {
        Uri uri = contentResolver.insert(CONTENT_URI, mapItemAttributeToContentValues(itemAttribute));
        itemAttribute.setItemId((int) ContentUris.parseId(uri));
        return itemAttribute;
    }

    public int update(ItemAttribute attribute) {
        String selection = _ID + " = ?";
        String[] selectionArgs = { String.valueOf(attribute.getId()) };
        return contentResolver.update(CONTENT_URI, mapItemAttributeToContentValues(attribute),
                selection, selectionArgs);
    }

    private ContentValues mapItemAttributeToContentValues(ItemAttribute attribute) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_ID, attribute.getItemId());
        contentValues.put(COLUMN_ATTRIBUTE_ID, attribute.getAttributeId());
        contentValues.put(COLUMN_ATTRIBUTE_NAME, attribute.getAttributeName());
        contentValues.put(COLUMN_ATTRIBUTE_TYPE, attribute.getAttributeType());
        contentValues.put(COLUMN_ATTRIBUTE_VALUE, attribute.getAttributeValue());
        contentValues.put(COLUMN_ATTRIBUTE_VALUE_TEXT, attribute.getAttributeValueStr());
        return contentValues;
    }

    private List<ItemAttribute> mapCursorToItemAttribute(Cursor c) {
        List<ItemAttribute> itemAttributeList = new ArrayList<>();
        while (c.moveToNext()) {
            itemAttributeList.add(new ItemAttribute(
               c.getInt(c.getColumnIndex(_ID)),
               c.getInt(c.getColumnIndex(COLUMN_ITEM_ID)),
               c.getInt(c.getColumnIndex(COLUMN_ATTRIBUTE_ID)),
               c.getString(c.getColumnIndex(COLUMN_ATTRIBUTE_NAME)),
               c.getInt(c.getColumnIndex(COLUMN_ATTRIBUTE_TYPE)),
               c.getString(c.getColumnIndex(COLUMN_ATTRIBUTE_VALUE)),
               c.getString(c.getColumnIndex(COLUMN_ATTRIBUTE_VALUE_TEXT))
            ));
        }
        return itemAttributeList;
    }

}
