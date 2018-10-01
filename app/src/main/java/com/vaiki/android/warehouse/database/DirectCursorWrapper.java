package com.vaiki.android.warehouse.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.vaiki.android.warehouse.Direct;
import com.vaiki.android.warehouse.database.DirectDbSchema.TableAll;

import static com.vaiki.android.warehouse.database.DirectDbSchema.TableAll.Cols.UUID;

/**
 * Created by E_not on 21.08.2018.
 */

public class DirectCursorWrapper extends CursorWrapper {
    public DirectCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Direct getDirect(){
        String uuidString = getString(getColumnIndex(UUID));
        String directory = getString(getColumnIndex(TableAll.Cols.DIRECTORY));
        String product = getString(getColumnIndex(TableAll.Cols.PRODUCT));
        String description = getString(getColumnIndex(TableAll.Cols.DESCRIPTION));
        String qty = getString(getColumnIndex(TableAll.Cols.QTY));
        Direct direct = new Direct(product,directory,description,Integer.parseInt(qty));

        return direct;
    }
    public String getNamedirectory(){
        String dir = getString(getColumnIndex(DirectDbSchema.TableDirectory.Cols.DIRECTORY));
        return dir ;}

}
