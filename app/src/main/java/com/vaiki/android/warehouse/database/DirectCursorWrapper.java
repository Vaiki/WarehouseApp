package com.vaiki.android.warehouse.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.vaiki.android.warehouse.Direct;
import com.vaiki.android.warehouse.database.DirectDbSchema.DirectTable;

import static com.vaiki.android.warehouse.database.DirectDbSchema.DirectTable.Cols.UUID;

/**
 * Created by E_not on 21.08.2018.
 */

public class DirectCursorWrapper extends CursorWrapper {
    public DirectCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Direct getDirect(){
        String uuidString = getString(getColumnIndex(UUID));
        String directory = getString(getColumnIndex(DirectTable.Cols.DIRECTORY));
        String product = getString(getColumnIndex(DirectTable.Cols.PRODUCT));
        String description = getString(getColumnIndex(DirectTable.Cols.DESCRIPTION));
        String qty = getString(getColumnIndex(DirectTable.Cols.QTY));
        Direct direct = new Direct(product,directory,description,Integer.parseInt(qty));

        return direct;
    }
    public String getNamedirectory(){
        String dir = getString(getColumnIndex(DirectDbSchema.DirectoryTable.Colums.NAMEDIR));
        return dir ;}

}
