package com.vaiki.android.warehouse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.Toast;

import com.vaiki.android.warehouse.database.DirectBaseHelper;
import com.vaiki.android.warehouse.database.DirectCursorWrapper;
import com.vaiki.android.warehouse.database.DirectDbSchema.TableAll;

import java.util.*;

import static com.vaiki.android.warehouse.database.DirectDbSchema.*;

/**
 * Created by E_not on 26.07.2018.
 */

public class DirectLab {
    private static DirectLab sDirectLab;
    private static HashSet<String> dir;
    private static List<String> catName;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static DirectLab get(Context context) {
        if (sDirectLab == null) {
            sDirectLab = new DirectLab(context);
        }
        return sDirectLab;
    }

    public void add_direct(Direct d) {
        ContentValues values = getContentValues(d);
        mDatabase.insert(TableAll.NAME, null, values);
    }

    private DirectLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DirectBaseHelper(mContext).getWritableDatabase();
    }

    public void deleteDirect(Context context, Direct direct) {

    }

    public void updateDirect(Context context, Direct direct, Button button, int i) {
        String description = direct.getDescription();
        String product = direct.getName_product();
        if (direct.getQty() == 0)
            Toast.makeText(context, R.string.empty_qty, Toast.LENGTH_SHORT).show();
        else {
            try {
                DirectCursorWrapper c = queryDirects(TableAll.NAME, TableAll.Cols.DESCRIPTION + "= ? AND " + TableAll.Cols.PRODUCT + "= ?",
                        new String[]{description, product});
                if (c.moveToFirst()) {
                    switch(i){
                        case 1: direct.setQty(direct.getQty() + c.getDirect().getQty());
                            break;
                        case 0: direct.setName_directory(c.getDirect().getName_directory());
                            int result = c.getDirect().getQty()-direct.getQty();
                           if (result<0){ throw new Exception();}
                            else direct.setQty(result);
                            break;
                    }
                    ContentValues values = getContentValues(direct);
                    mDatabase.update(TableAll.NAME, values, TableAll.Cols.DESCRIPTION + "= ? AND " + TableAll.Cols.PRODUCT + "= ?",
                            new String[]{description, product});
                    Toast.makeText(context, R.string.update, Toast.LENGTH_SHORT).show();
                } else {
                    add_direct(direct);
                    Toast.makeText(context, R.string.add_direct, Toast.LENGTH_SHORT).show();
                }
                c.close();
                button.setEnabled(false);
            } catch (Exception e) {
                Toast.makeText(context,R.string.negative_qty, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private DirectCursorWrapper queryDirects(String tableName, String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                tableName, null, whereClause, whereArgs, null, null, null
        );
        return new DirectCursorWrapper(cursor);
    }

    public List<String> getDirectory_Name() {
        List<String> name_directory = new ArrayList<>();
        DirectCursorWrapper cursor = queryDirects(TableDirectory.DIRECTORY_NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                name_directory.add(cursor.getNamedirectory());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return name_directory;
    }



    public List<Direct> getDirects() {
        List<Direct> directs = new ArrayList<>();
        DirectCursorWrapper cursor = queryDirects(TableAll.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                directs.add(cursor.getDirect());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return directs;
    }

    public static List<String> getCatName(DirectLab directLab) { //возвращает уникальные названия категорий
        dir = new HashSet<>();
        for (Direct d : directLab.getDirects()) {
            dir.add(d.getName_directory());
        }
        catName = new ArrayList<>(dir);
        return catName;
    }

    public static List<Direct> getSortItem(String s, DirectLab directLab) {// возвращает позиции категории
        List<Direct> sortDirectory = new ArrayList<>();
        for (Direct d : directLab.getDirects()) {
            if (d.getName_directory().equals(s)) sortDirectory.add(d);
        }
        return sortDirectory;
    }

    private static ContentValues getContentValues(Direct direct) {
        ContentValues values = new ContentValues();
        values.put(TableAll.Cols.UUID, direct.getId().toString());
        values.put(TableAll.Cols.DIRECTORY, direct.getName_directory());
        values.put(TableAll.Cols.PRODUCT, direct.getName_product());
        values.put(TableAll.Cols.DESCRIPTION, direct.getDescription());
        values.put(TableAll.Cols.QTY, direct.getQty());
        return values;
    }
}