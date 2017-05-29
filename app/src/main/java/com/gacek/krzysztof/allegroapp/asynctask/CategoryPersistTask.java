package com.gacek.krzysztof.allegroapp.asynctask;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.gacek.krzysztof.allegroapp.data.DataContract;
import com.gacek.krzysztof.allegroapp.data.DatabaseHelper;
import com.gacek.krzysztof.allegroapp.dto.CategoryDto;
import com.gacek.krzysztof.allegroapp.dto.DoGetCatsDataResponseEnvelope;


public class CategoryPersistTask  extends AsyncTask<Void, Integer, Long> {

    private DatabaseHelper databaseHelper;
    private DoGetCatsDataResponseEnvelope data;

    public CategoryPersistTask(DatabaseHelper databaseHelper, DoGetCatsDataResponseEnvelope data) {
        this.databaseHelper = databaseHelper;
        this.data = data;
    }

    @Override
    protected Long doInBackground(Void... params) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.beginTransaction();
        for (CategoryDto cats : data.getCatsList()) {
            ContentValues values = new ContentValues();
            values.put(DataContract.CategoriesEntry._ID, cats.getCatId());
            values.put(DataContract.CategoriesEntry.COLUMN_NAME, cats.getCatName());
            values.put(DataContract.CategoriesEntry.COLUMN_PARENT, cats.getCatParent());
            db.insert(DataContract.CategoriesEntry.TABLE_NAME, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return 1L;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        Log.d("Persist Async Task", "Categories Persisted Successfully");
    }

}