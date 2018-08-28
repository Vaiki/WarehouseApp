package com.vaiki.android.warehouse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vaiki.android.warehouse.database.DirectDbSchema.DirectTable;

import static com.vaiki.android.warehouse.database.DirectDbSchema.*;

/**
 * Created by E_not on 21.08.2018.
 */

public class DirectBaseHelper extends SQLiteOpenHelper {
    private static  final  int VERSION = 1;
    private static final String DATABASE_NAME = "directBase.db";
   // private static final String DATABASE_DIRECTORY = "directoryBase.db";
    public DirectBaseHelper(Context context){
        super(context, DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table "+ DirectTable.NAME + "("+
    "_id integer primary key autoincrement, " +
            DirectTable.Cols.UUID + ", " +
            DirectTable.Cols.DIRECTORY + " not null, " +
            DirectTable.Cols.PRODUCT + " not null, " +
            DirectTable.Cols.DESCRIPTION + " not null, " +
            DirectTable.Cols.QTY + " not null)"
    );
        db.execSQL("create table "+ DirectoryTable.DIRECTORY_NAME + "("+
                "_id integer primary key autoincrement, "+
                DirectoryTable.Colums.NAMEDIR+ ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
