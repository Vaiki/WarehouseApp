package com.vaiki.android.warehouse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vaiki.android.warehouse.database.DirectDbSchema.TableAll;

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
    db.execSQL("create table "+ TableAll.NAME + "("+
    "_id integer primary key autoincrement, " +
            TableAll.Cols.UUID + ", " +
            TableAll.Cols.DIRECTORY + ", " +
            TableAll.Cols.PRODUCT + " , " +
            TableAll.Cols.DESCRIPTION + " , " +
            TableAll.Cols.QTY + " )"
    );
        db.execSQL("create table "+ TableDirectory.DIRECTORY_NAME + "("+
                "_id integer primary key autoincrement, "+
                TableDirectory.Cols.DIRECTORY + ")");
        db.execSQL("create table "+ TableProduct.PRODUCT_NAME + "("+
                "_id integer primary key autoincrement, "+
                TableProduct.Cols.PRODUCT+ ")");
        db.execSQL("create table "+ TableDescription.DESCRIPTION_NAME + "("+
                "_id integer primary key autoincrement, "+
                TableDescription.Cols.DESCRIPTION+ ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
