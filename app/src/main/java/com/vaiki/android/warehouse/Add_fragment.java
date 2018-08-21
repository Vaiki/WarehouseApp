package com.vaiki.android.warehouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;

import static android.R.attr.data;

/**
 * Created by E_not on 27.07.2018.
 */

public class Add_fragment extends Fragment {
    private Direct mDirect;
    private TextView mTitle;
    private EditText mName_product;
    private EditText mCatalog;
    private EditText mDescription;
    private EditText mQty;
    private Button mAdd;
    private List<String> directories = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDirect = new Direct();
    directories.add("Метизы");
    directories.add("Сантехника");
    directories.add("ГСМ");
    directories.add("Электрика");
    directories.add("КИПиА");
    directories.add("Материалы");
    directories.add("Другое");
    directories.add("Выберите категорию");



    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_inventory_form,container,false);
        mTitle = (TextView)v.findViewById(R.id.title_text);
        mTitle.setText("Приход ТМЦ");
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,directories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        // выделяем элемент
        spinner.setSelection(7);
        // устанавливаем обработчик нажатия
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

        mName_product =(EditText)v.findViewById(R.id.name_text);
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

//        mCatalog = (EditText)v.findViewById(R.id.catalog_text);
//        mCatalog.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            mDirect.setName_directory(s.toString());
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        mDescription = (EditText)v.findViewById(R.id.discription_text);
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
        mQty = (EditText)v.findViewById(R.id.qty_edit);
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
        mAdd=(Button)v.findViewById(R.id.add_button);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

DirectLab.get(getActivity()).add_direct(mDirect);

Toast.makeText(getActivity(),R.string.add_direct,Toast.LENGTH_LONG).show();
                mAdd.setEnabled(false);
        }
        });

        return v;
    }
}
