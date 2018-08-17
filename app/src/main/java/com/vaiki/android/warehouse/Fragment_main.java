package com.vaiki.android.warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by E_not on 25.07.2018.
 */

public class Fragment_main extends Fragment {
    private Button mAdd_Form;
    private Button mDirect;
    private Button mMove;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mAdd_Form = (Button) v.findViewById(R.id.add_product);
        mAdd_Form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Add_activity.class);
                startActivity(intent);
            }
        });
        mDirect = (Button) v.findViewById(R.id.direct);
        mDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListDirectory.class);
                startActivity(intent);
            }
        });
        mMove = (Button) v.findViewById(R.id.moving_product);
        mMove.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent = new Intent(getActivity(), ListDirectory.class);
                                         startActivity(intent);
                                     }
                                 }
        );

        return v;
    }


}
