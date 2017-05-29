package com.gacek.krzysztof.allegroapp.data.repo;

import android.content.ContentResolver;
import android.database.Cursor;

import com.gacek.krzysztof.allegroapp.model.Category;

import java.util.ArrayList;
import java.util.List;

import static com.gacek.krzysztof.allegroapp.data.DataContract.*;


public class CategoriesRepository {

    private static CategoriesRepository sCategoriesRepository;
    private ContentResolver contentResolver;

    private CategoriesRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public static synchronized void initInstance(ContentResolver contentResolver) {
        if (sCategoriesRepository == null) {
            sCategoriesRepository = new CategoriesRepository(contentResolver);
        }
    }

    public static synchronized CategoriesRepository getInstance() {
        if (sCategoriesRepository == null) {
            throw new IllegalStateException("CategoriesRepository is not initialized");
        }
        return sCategoriesRepository;
    }

    public Category findOne(int id) {
        String selection = CategoriesEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor c = contentResolver.query(CategoriesEntry.CONTENT_URI, CategoriesEntry.PROJECTION,
                selection, selectionArgs, null);
        List<Category> cats = mapCursorToCategory(c);
        if (cats.isEmpty()) {
            return null;
        } else {
            return cats.get(0);
        }
    }

    public List<Category> findByParentId(int parentId) {
        String selection = CategoriesEntry.COLUMN_PARENT + " = ?";
        String[] selectionArgs = { String.valueOf(parentId) };
        Cursor c = contentResolver.query(CategoriesEntry.CONTENT_URI, CategoriesEntry.PROJECTION,
                selection, selectionArgs, null);
        return mapCursorToCategory(c) ;
    }

    private List<Category> mapCursorToCategory(Cursor c) {
        List<Category> categoryList = new ArrayList<>();
        while (c.moveToNext()) {
            categoryList.add(new Category(
                    c.getInt(c.getColumnIndex(CategoriesEntry._ID)),
                    c.getString(c.getColumnIndex(CategoriesEntry.COLUMN_NAME)),
                    c.getInt(c.getColumnIndex(CategoriesEntry.COLUMN_PARENT))
            ));
        }
        return categoryList;
    }


}
