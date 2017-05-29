package com.gacek.krzysztof.allegroapp.data;

import android.net.Uri;
import android.provider.BaseColumns;


public class DataContract {

    public static final String CONTENT_AUTHORITY = "com.gacek.krzysztof.allegroapp";
    public static final String PATH_CATEGORIES = "categories";
    public static final String PATH_ITEMS = "items";
    public static final String PATH_PHOTOS = "photos";
    public static final String PATH_ITEM_ATTRIBUTE = "item-attribute";
    public static final String PATH_THUMB = "thumbs";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class CategoriesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CATEGORIES);

        public static final String TABLE_NAME = "categories";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PARENT = "parent";
        public static final String COLUMN_POSITION = "position";

        public static final String[] PROJECTION = {
                _ID, COLUMN_NAME, COLUMN_PARENT, COLUMN_POSITION
        };

    }

    public static final class ItemsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);

        public static final String TABLE_NAME = "Items";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_CATEGORY_PATH = "category_path";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_QUANTITY_TYPE = "quantity_type";
        public static final String COLUMN_QUANTITY_TYPE_ID = "quantity_type_id";
        public static final String COLUMN_CREATION_DATE =   "creation_date";

        public static final String[] PROJECTION = {
            _ID, COLUMN_NAME, COLUMN_CATEGORY_ID, COLUMN_CATEGORY_PATH, COLUMN_DESCRIPTION,
                COLUMN_QUANTITY, COLUMN_QUANTITY_TYPE, COLUMN_QUANTITY_TYPE_ID, COLUMN_CREATION_DATE
        };

    }

    public static final class PhotosEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PHOTOS);

        public static final String TABLE_NAME = "Photos";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ITEM_ID = "item_id";
        public static final String COLUMN_PHOTO_DATA = "photo_data";
        public static final String COLUMN_PRIMARY_IMAGE = "primary_image";

        public static final String[] PROJECTION = {
                _ID, COLUMN_ITEM_ID, COLUMN_PHOTO_DATA, COLUMN_PRIMARY_IMAGE
        };

    }

    public static final class ItemsAttributeEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEM_ATTRIBUTE);

        public static final String TABLE_NAME = "ItemsAttribute";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ITEM_ID = "item_id";
        public static final String COLUMN_ATTRIBUTE_ID = "attribute_id";
        public static final String COLUMN_ATTRIBUTE_NAME = "attribute_name";
        public static final String COLUMN_ATTRIBUTE_TYPE = "attribute_type";
        public static final String COLUMN_ATTRIBUTE_VALUE = "attribute_value";
        public static final String COLUMN_ATTRIBUTE_VALUE_TEXT = "attribute_value_text";

        public static final String[] PROJECTION = {
                _ID, COLUMN_ITEM_ID, COLUMN_ATTRIBUTE_ID, COLUMN_ATTRIBUTE_NAME,
                COLUMN_ATTRIBUTE_TYPE, COLUMN_ATTRIBUTE_VALUE, COLUMN_ATTRIBUTE_VALUE_TEXT
        };

    }

    public static final class DeliveryOptionsEntry implements BaseColumns {

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_IS_ENABLED = "is_enabled";
        public static final String COLUMN_FIRST_ITEM_PRICE_ID = "first_item_price_id";
        public static final String COLUMN_FIRST_ITEM_PRICE_VALUE = "first_item_price_value";
        public static final String COLUMN_NEXT_ITEM_PRICE_ID = "next_item_price_id";
        public static final String COLUMN_NEXT_ITEM_PRICE_VALUE = "next_item_price_value";
        public static final String COLUMN_QTY_IN_PACKAGE_ID = "qty_in_package_id";
        public static final String COLUMN_QTY_IN_PACKAGE_VALUE = "qty_in_package_value";
        public static final String COLUMN_TYPE = "type";

        public static final String[] PROJECTION = {
                _ID, COLUMN_IS_ENABLED, COLUMN_FIRST_ITEM_PRICE_ID, COLUMN_FIRST_ITEM_PRICE_VALUE,
                COLUMN_NEXT_ITEM_PRICE_ID, COLUMN_NEXT_ITEM_PRICE_VALUE, COLUMN_QTY_IN_PACKAGE_ID,
                COLUMN_QTY_IN_PACKAGE_VALUE, COLUMN_TYPE
        };

    }

    public static final class ThumbView {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_THUMB);

    }

}
