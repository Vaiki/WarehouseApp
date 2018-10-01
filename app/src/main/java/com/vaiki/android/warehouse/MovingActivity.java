package com.vaiki.android.warehouse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.vaiki.android.warehouse.database.DirectBaseHelper;

import static com.vaiki.android.warehouse.database.DirectDbSchema.*;


/**
 * Created by e.cherednik on 28.08.2018.
 */

public class MovingActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    SimpleCursorAdapter scAdapter, mAdapter;
    private Direct mDirect;
    static final int product = 0;
    static final int description = 1;
    SQLiteDatabase db;
    public TextView text;
    static String where = "test";
    private EditText qty;
    private Button moving;

    private String descript;
    private int qtys;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moving);
        db = new DirectBaseHelper(this).getWritableDatabase();
        text = (TextView) findViewById(R.id.test);
        qty = (EditText) findViewById(R.id.qty2);
        mDirect = new Direct();
        moving = (Button) findViewById(R.id.moving_button);

        final Spinner spinner = (Spinner) findViewById(R.id.spadd_product);
        String[] from = new String[]{TableAll.Cols.PRODUCT};
        int[] to = new int[]{android.R.id.text1};
        scAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, null,
                from, to, 0);
        spinner.setAdapter(scAdapter);
        getSupportLoaderManager().initLoader(product, null, this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(18);
                Cursor cursor = (Cursor) spinner.getSelectedItem();
                where = cursor.getString(cursor.getColumnIndex
                        (TableAll.Cols.PRODUCT));
                mDirect.setName_product(where);
                getSupportLoaderManager().restartLoader(description, null, MovingActivity.this);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getSupportLoaderManager().initLoader(description, null, this);

        final Spinner sp = (Spinner) findViewById(R.id.spinner_decription);
        String[] fromDescript = new String[]{TableAll.Cols.DESCRIPTION};
        int[] toDescript = new int[]{android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, null,
                fromDescript, toDescript, 1);
        sp.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(description, null, this);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cur = (Cursor) sp.getSelectedItem();
                mDirect.setDescription(cur.getString(cur.getColumnIndex
                        (TableAll.Cols.DESCRIPTION)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mDirect.setQty(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        moving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DirectLab.get(getApplicationContext()).updateDirect(getApplicationContext(),
                        mDirect, moving, 0);
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
        return new MyCursorLoader(this, db, id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case product:
                scAdapter.swapCursor(data);
                break;
            case description:
                mAdapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {
        SQLiteDatabase db;
        Cursor cursor;
        int LOADER_ID;

        public MyCursorLoader(Context context, SQLiteDatabase db, int id) {
            super(context);
            this.db = db;
            LOADER_ID = id;
        }

        @Override
        public Cursor loadInBackground() {
            switch (LOADER_ID) {
                case product:
                    cursor = db.query(TableAll.NAME,
                            new String[]{"_id", TableAll.Cols.PRODUCT}, null, null,
                            TableAll.Cols.PRODUCT, null, null);
                    break;
                case description:
                    cursor = db.query(TableAll.NAME,
                            new String[]{"_id", TableAll.Cols.DESCRIPTION},
                            TableAll.Cols.PRODUCT + "= ?",
                            new String[]{where}, null, null, null);
            }
            return cursor;
        }
    }

}
