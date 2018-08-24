package com.vaiki.android.warehouse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.vaiki.android.warehouse.database.DirectBaseHelper;
import com.vaiki.android.warehouse.database.DirectCursorWrapper;
import com.vaiki.android.warehouse.database.DirectDbSchema;
import com.vaiki.android.warehouse.database.DirectDbSchema.DirectTable;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by E_not on 26.07.2018.
 */

public class DirectLab {
    private static DirectLab sDirectLab;
    // private List<Direct> mDirects;
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
        //      mDirects.add(d);
        ContentValues values = getContentValues(d);
        mDatabase.insert(DirectTable.NAME, null, values);
    }

    private DirectLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DirectBaseHelper(mContext).getWritableDatabase();
        //   mDirects = new ArrayList<>();
    }


    public void updateDirect(Context context,Direct direct) {

        //String uuidString = direct.getId().toString();
        String description = direct.getDescription();
        String product = direct.getName_product();

        DirectCursorWrapper c = queryDirects(DirectTable.Cols.DESCRIPTION + "= ?", new String[]{description});

        if (c.moveToFirst()) {
            direct.setQty(direct.getQty() + c.getDirect().getQty());
            ContentValues values = getContentValues(direct);
            mDatabase.update(DirectTable.NAME, values, DirectTable.Cols.DESCRIPTION + "= ?", new String[]{description});
            Toast.makeText(context,R.string.update, Toast.LENGTH_LONG).show();
        }
        //mDatabase.update(DirectTable.NAME, values, DirectTable.Cols.UUID + "= ?", new String[]{uuidString});
        else {
            ContentValues add_values = getContentValues(direct);
            mDatabase.insert(DirectTable.NAME, null, add_values);
            Toast.makeText(context,R.string.add_direct, Toast.LENGTH_LONG).show();
        }
        c.close();
        mDatabase.close();

    }

    private DirectCursorWrapper queryDirects(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DirectTable.NAME, null, whereClause, whereArgs, null, null, null
        );
        return new DirectCursorWrapper(cursor);
    }

    public List<Direct> getDirects() {
        //return mDirects;
        List<Direct> directs = new ArrayList<>();
        DirectCursorWrapper cursor = queryDirects(null, null);
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
        values.put(DirectTable.Cols.UUID, direct.getId().toString());
        values.put(DirectTable.Cols.DIRECTORY, direct.getName_directory());
        values.put(DirectTable.Cols.PRODUCT, direct.getName_product());
        values.put(DirectTable.Cols.DESCRIPTION, direct.getDescription());
        values.put(DirectTable.Cols.QTY, direct.getQty());
        return values;
    }
}