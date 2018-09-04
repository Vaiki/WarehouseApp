package com.vaiki.android.warehouse;

import android.app.Activity;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.vaiki.android.warehouse.database.DirectBaseHelper;
import static com.vaiki.android.warehouse.database.DirectDbSchema.*;


/**
 * Created by e.cherednik on 28.08.2018.
 */

public class MovingActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    SimpleCursorAdapter scAdapter,mAdapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moving);
        db = new DirectBaseHelper(this).getWritableDatabase();

        final Spinner spinner = (Spinner) findViewById(R.id.spinner_product);
        String[] from = new String[]{DirectTable.Cols.PRODUCT};
        int[] to = new int[]{android.R.id.text1};
        scAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, null, from, to, 0);
        spinner.setAdapter(scAdapter);
        getSupportLoaderManager().initLoader(0, null, this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner sp = (Spinner) findViewById(R.id.spinner_decription);
        String[] froms = new String[]{DirectTable.Cols.DESCRIPTION};
        int[] to0 = new int[]{android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, null, froms, to0, 0);
        spinner.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(1, null, this);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, db,id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {
        SQLiteDatabase db;
        public MyCursorLoader(Context context, SQLiteDatabase db, int id) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.query(DirectTable.NAME, new String[]{"_id",DirectTable.Cols.PRODUCT}, null, null,DirectTable.Cols.PRODUCT, null, null);
            return cursor;
        }
    }

}
