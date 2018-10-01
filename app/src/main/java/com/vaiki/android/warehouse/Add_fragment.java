package com.vaiki.android.warehouse;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vaiki.android.warehouse.database.DirectBaseHelper;

import java.util.*;

import static com.vaiki.android.warehouse.database.DirectDbSchema.*;

/**
 * Created by E_not on 27.07.2018.
 */

public class Add_fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private Direct mDirect;
    private TextView mTitle;
    private EditText mName_product;
    private EditText mDescription;
    private EditText mQty;
    private Button mAdd;
    private ImageButton mEditDirectory,mEditProduct,mEditDescription;
    private List<String> directories = new ArrayList<>();
    private SQLiteDatabase db;
    private ArrayAdapter<String> adapter;
    private static String where_prod = "";
    private static String where_des = "";

    SimpleCursorAdapter diradapter,prod_adapter,des_adapter;
    static final int directory = 0;
    static final int product = 1;
    static final int des = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDirect = new Direct();
        directories = DirectLab.get(getActivity()).getDirectory_Name();
        db = new DirectBaseHelper(getActivity().getApplicationContext()).getWritableDatabase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_inventory_form, container, false);
        mTitle = (TextView) v.findViewById(R.id.title_text);
        mTitle.setText("Приход ТМЦ");

      // SPINNER DIRECTORY
        getLoaderManager().initLoader(directory, null, this);
        final Spinner spinner = (Spinner) v.findViewById(R.id.spadd_directory);
        String[] from = new String[]{TableDirectory.Cols.DIRECTORY};
        int[] to = new int[]{android.R.id.text1};
        diradapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_spinner_item,
                null, from, to, 0);
        spinner.setAdapter(diradapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Cursor cursor = (Cursor) spinner.getSelectedItem();
                where_prod = cursor.getString(cursor.getColumnIndex
                        (TableDirectory.Cols.DIRECTORY));
                mDirect.setName_directory(where_prod);
                getLoaderManager().restartLoader(product,null,Add_fragment.this);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // Spinner PRODUCT
        getLoaderManager().initLoader(product, null, this);
        final Spinner spProduct = (Spinner) v.findViewById(R.id.spadd_product);
        String[] from_prod = new String[]{TableAll.Cols.PRODUCT};
        int[] to_prod = new int[]{android.R.id.text1};
        prod_adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_spinner_item,
                null, from_prod, to_prod, 1);
        spProduct.setAdapter(prod_adapter);
        spProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Cursor cursor = (Cursor) spProduct.getSelectedItem();
                where_des = cursor.getString(cursor.getColumnIndex
                        (TableAll.Cols.PRODUCT));
                mDirect.setName_product(where_des);
                getLoaderManager().restartLoader(des,null,Add_fragment.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // SPINNER DESCRIPTION
        getLoaderManager().initLoader(des, null, this);
        final Spinner spDescriptoin = (Spinner) v.findViewById(R.id.spadd_description);
        String[] from_des = new String[]{TableAll.Cols.DESCRIPTION};
        int[] to_des = new int[]{android.R.id.text1};
       des_adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_spinner_item, null, from_des, to_des, 2);
        spDescriptoin.setAdapter(des_adapter);
        spProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Cursor cursor = (Cursor) spDescriptoin.getSelectedItem();

                mDirect.setDescription(cursor.getString(cursor.getColumnIndex
                        (TableAll.Cols.DESCRIPTION)));
                            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mQty = (EditText) v.findViewById(R.id.qty_edit);
        mQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDirect.setQty(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditDirectory = (ImageButton) v.findViewById(R.id.editDirectory);
        mEditDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View editView = layoutInflater.inflate(R.layout.editactivity, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getActivity());

                mDialogBuilder.setView(editView);
                final EditText userInput = (EditText) editView.findViewById(R.id.input_text);
                mDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db = new DirectBaseHelper(getActivity().getApplicationContext()).getWritableDatabase();
                        ContentValues values = new ContentValues();
                        if (userInput.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Категория не создана", Toast.LENGTH_SHORT).show();
                        } else {

                            values.put(TableDirectory.Cols.DIRECTORY, userInput.getText().toString());
                            db.insert(TableDirectory.DIRECTORY_NAME, null, values);
                            getLoaderManager().restartLoader(directory,null,Add_fragment.this);
//                            adapter.add(userInput.getText().toString());
//                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "Новая категория создана", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                mDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {//обработчик закрытия окна
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                    }
                });

                AlertDialog alertDialog = mDialogBuilder.create();

                alertDialog.show();

            }
        });
        mEditProduct = (ImageButton) v.findViewById(R.id.editProduct);
        mEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View editView = layoutInflater.inflate(R.layout.editactivity, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getActivity());

                mDialogBuilder.setView(editView);
                final EditText userInput = (EditText) editView.findViewById(R.id.input_text);
                mDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db = new DirectBaseHelper(getActivity().getApplicationContext()).getWritableDatabase();
                        ContentValues values = new ContentValues();
                        if (userInput.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Товар не добавлен", Toast.LENGTH_SHORT).show();
                        } else {

                            values.put(TableProduct.Cols.PRODUCT, userInput.getText().toString());
                            db.insert(TableProduct.PRODUCT_NAME, null, values);
                            getLoaderManager().restartLoader(product,null,Add_fragment.this);

//                            adapter.add(userInput.getText().toString());
//                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "Новый товар добавлен", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                mDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {//обработчик закрытия окна
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                    }
                });

                AlertDialog alertDialog = mDialogBuilder.create();

                alertDialog.show();

            }
        });
         mEditDescription = (ImageButton) v.findViewById(R.id.editDescription);
        mEditDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View editView = layoutInflater.inflate(R.layout.editactivity, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getActivity());

                mDialogBuilder.setView(editView);
                final EditText userInput = (EditText) editView.findViewById(R.id.input_text);
                mDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db = new DirectBaseHelper(getActivity().getApplicationContext()).getWritableDatabase();
                        ContentValues values = new ContentValues();
                        if (userInput.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Описание не добавлено", Toast.LENGTH_SHORT).show();
                        } else {

                            values.put(TableDescription.Cols.DESCRIPTION, userInput.getText().toString());
                            db.insert(TableDescription.DESCRIPTION_NAME, null, values);
                            getLoaderManager().restartLoader(product,null,Add_fragment.this);

//                            adapter.add(userInput.getText().toString());
//                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "Новое описание добавлено", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                mDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {//обработчик закрытия окна
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                    }
                });

                AlertDialog alertDialog = mDialogBuilder.create();

                alertDialog.show();

            }
        });





        mAdd = (Button) v.findViewById(R.id.add_button);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DirectLab.get(getActivity()).updateDirect(getActivity(), mDirect, mAdd, 1);


            }
        });

        return v;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyLoader(getContext(), db, id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case directory:
                diradapter.swapCursor(data);
                break;
            case product:
                prod_adapter.swapCursor(data);
                break;
            case des:
                des_adapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    static class MyLoader extends CursorLoader {
        SQLiteDatabase db;
        Cursor cursor;
        int LOADER_ID;

        public MyLoader(Context context, SQLiteDatabase db, int id) {
            super(context);
            this.db = db;
            LOADER_ID = id;
        }

        @Override
        public Cursor loadInBackground() {
            switch (LOADER_ID) {
                case product:
//                    cursor = db.query(TableProduct.PRODUCT_NAME,
//                            new String[]{"_id", TableProduct.Cols.PRODUCT }, TableProduct.Cols.PRODUCT + "= ?",
//                            new String[]{"(SELECT "+TableAll.Cols.PRODUCT + " FROM "+TableAll.NAME+" WHERE "+
//                            TableAll.Cols.DIRECTORY+ " = "+ TableDirectory.Cols.DIRECTORY+")"}, null,
//                            null, null);
                    cursor = db.query(TableProduct.PRODUCT_NAME,new String[]{"_id", TableProduct.Cols.PRODUCT}, null, null,
                            TableProduct.Cols.PRODUCT, null, null);
                    break;
                case des:
                    cursor = db.query(TableAll.NAME,
                            new String[]{"_id", TableAll.Cols.DESCRIPTION},
                            TableAll.Cols.PRODUCT + "= ?",
                            new String[]{where_des}, null, null, null);
                    break;
                case directory:
                    cursor = db.query(TableDirectory.DIRECTORY_NAME, new String[]{"_id", TableDirectory.Cols.DIRECTORY}, null, null,
                            TableDirectory.Cols.DIRECTORY, null, null);
                    break;
            }
            return cursor;
        }
    }
}
