package com.vaiki.android.warehouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.*;

/**
 * Created by E_not on 16.08.2018.
 */

public class ListDirectory extends Activity {
    private ListView mListViews;
    private List<String> array (){ DirectLab mDirectLab = DirectLab.get(this);
        return DirectLab.getCatName(mDirectLab);}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_view);
        mListViews = (ListView) findViewById(R.id.lvMain);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, array());
        mListViews.setAdapter(adapter);
        mListViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListDirectory.this, Direct_activity_list.class);
                startActivity(intent);
            }
        });
  }
}
