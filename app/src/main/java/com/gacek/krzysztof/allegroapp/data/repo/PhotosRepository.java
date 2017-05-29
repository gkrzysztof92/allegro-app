package com.gacek.krzysztof.allegroapp.data.repo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.gacek.krzysztof.allegroapp.data.DataContract;
import com.gacek.krzysztof.allegroapp.model.Photo;

import java.util.ArrayList;
import java.util.List;

import static com.gacek.krzysztof.allegroapp.data.DataContract.PhotosEntry.COLUMN_ITEM_ID;
import static com.gacek.krzysztof.allegroapp.data.DataContract.PhotosEntry.COLUMN_PHOTO_DATA;
import static com.gacek.krzysztof.allegroapp.data.DataContract.PhotosEntry.COLUMN_PRIMARY_IMAGE;
import static com.gacek.krzysztof.allegroapp.data.DataContract.PhotosEntry.CONTENT_URI;
import static com.gacek.krzysztof.allegroapp.data.DataContract.PhotosEntry._ID;


public class PhotosRepository {

    private static PhotosRepository sPhotosRepository;
    private ContentResolver contentResolver;

    private PhotosRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public static synchronized void initInstance(ContentResolver contentResolver) {
        if (sPhotosRepository == null) {
            sPhotosRepository = new PhotosRepository(contentResolver);
        }
    }

    public static synchronized PhotosRepository getInstance() {
        if (sPhotosRepository == null) {
            throw new IllegalStateException("CategoriesRepository is not initialized");
        }
        return sPhotosRepository;
    }

    public List<Photo> batchSave(List<Photo> photos) {
        for (Photo photo : photos) {
            photo = save(photo);
        }
        return photos;
    }

    public Photo save(Photo photo) {
        Uri uri = contentResolver.insert(CONTENT_URI, mapPhotoToContentValues(photo));
        photo.setId((int) ContentUris.parseId(uri));
        return photo;
    }

    public int update(Photo photo) {
        String selection = _ID + " = ? ";
        String[] selectionArg = {String.valueOf(photo.getId())};
        return contentResolver.update(CONTENT_URI, mapPhotoToContentValues(photo),
                selection, selectionArg);
    }

    public List<Photo> findByItemId(int itemId) {
        String[] projection = {_ID, COLUMN_ITEM_ID, COLUMN_PHOTO_DATA, COLUMN_PRIMARY_IMAGE};
        String selection = COLUMN_ITEM_ID + " = ? ";
        String[] selectionArg = {String.valueOf(itemId)};
        Cursor c = contentResolver.query(CONTENT_URI, projection, selection, selectionArg, null);
        return mapCursorToPhoto(c);
    }

    public int removeByItemId(int itemId) {
        String selection = COLUMN_ITEM_ID + " = ?";
        String[] selectionArgs = { String.valueOf(itemId) };
        int result = contentResolver.delete(CONTENT_URI, selection, selectionArgs);
        return result;
    }

    public List<Photo> findByItemIdAndPrimatyFlag(int itemId, String primaryFlag) {
        String[] projection = {_ID, COLUMN_ITEM_ID, COLUMN_PHOTO_DATA, COLUMN_PRIMARY_IMAGE};
        String selection = COLUMN_ITEM_ID + " = ? AND " + COLUMN_PRIMARY_IMAGE + " = ?";
        String[] selectionArg = {String.valueOf(itemId), primaryFlag};
        Cursor c = contentResolver.query(CONTENT_URI, projection, selection, selectionArg, null);
        return mapCursorToPhoto(c);
    }

    private List<Photo> mapCursorToPhoto(Cursor c) {
        List<Photo> photoList = new ArrayList<>();
        while (c.moveToNext()) {
            photoList.add(new Photo(
                    c.getInt(c.getColumnIndex(_ID)),
                    c.getInt(c.getColumnIndex(COLUMN_ITEM_ID)),
                    c.getBlob(c.getColumnIndex(COLUMN_PHOTO_DATA)),
                    c.getString(c.getColumnIndex(COLUMN_PRIMARY_IMAGE))
            ));
        }
        return photoList;
    }

    private ContentValues mapPhotoToContentValues(Photo photo) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_ID, photo.getItemId());
        values.put(COLUMN_PHOTO_DATA, photo.getPhotoData());
        values.put(COLUMN_PRIMARY_IMAGE, photo.getPrimaryImage());
        return values;
    }

}
