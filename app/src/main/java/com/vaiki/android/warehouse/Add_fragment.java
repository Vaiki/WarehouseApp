package com.vaiki.android.warehouse;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.vaiki.android.warehouse.database.DirectDbSchema;

import java.util.*;

import static android.R.attr.data;
import static com.vaiki.android.warehouse.R.id.spinner;
import static com.vaiki.android.warehouse.database.DirectDbSchema.*;

/**
 * Created by E_not on 27.07.2018.
 */

public class Add_fragment extends Fragment {
    private Direct mDirect;
    private TextView mTitle;
    private EditText mName_product;
    private EditText mDescription;
    private EditText mQty;
    private Button mAdd;
    private ImageButton mEditDirectory;
    private List<String> directories = new ArrayList<>();
    private SQLiteDatabase mDatabaseDirectory;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDirect = new Direct();
        directories = DirectLab.get(getActivity()).getDirectory_Name();

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_inventory_form, container, false);
        mTitle = (TextView) v.findViewById(R.id.title_text);
        mTitle.setText("Приход ТМЦ");
        // адаптер
        // Spinner

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, directories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner);

        // выделяем элемент
        //spinner.setSelection(7);
        // устанавливаем обработчик нажатия
//        mDatabaseDirectory = new DirectBaseHelper(getActivity().getApplicationContext()).getWritableDatabase();
//        Cursor cursor = mDatabaseDirectory.query(DirectoryTable.DIRECTORY_NAME,new String[]{"_id", DirectoryTable.Colums.NAMEDIR},
//                                                     null,null,null,null,null);
//
//        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),android.R.layout.simple_spinner_item,cursor
//                ,new String[]{DirectoryTable.Colums.NAMEDIR},new int[]{android.R.id.text1});
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(18);
                mDirect.setName_directory(spinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
//        cursor.close();

        mName_product = (EditText) v.findViewById(R.id.name_text);
        mName_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDirect.setName_product(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDescription = (EditText) v.findViewById(R.id.discription_text);
        mDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDirect.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                        mDatabaseDirectory = new DirectBaseHelper(getActivity().getApplicationContext()).getWritableDatabase();
                        ContentValues values = new ContentValues();
                        if(userInput.getText().toString().isEmpty()){Toast.makeText(getActivity(), "Категория не создана", Toast.LENGTH_SHORT).show();}
                      else{  values.put(DirectoryTable.Colums.NAMEDIR, userInput.getText().toString());
                        mDatabaseDirectory.insert(DirectoryTable.DIRECTORY_NAME, null, values);
                        Toast.makeText(getActivity(), "Новая категория создана", Toast.LENGTH_SHORT).show();}
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
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

                DirectLab.get(getActivity()).updateDirect(getActivity(), mDirect,mAdd);


            }
        });

        return v;
    }
}
