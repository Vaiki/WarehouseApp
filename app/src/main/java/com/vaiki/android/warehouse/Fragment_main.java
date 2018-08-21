package com.vaiki.android.warehouse;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by E_not on 25.07.2018.
 */

public class Fragment_main extends Fragment {
    private Button mAdd_Form;
    private Button mDirect;
    private Button mMove;
    private ImageView mImageView;


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
                                         Intent intent = new Intent(getActivity(), Direct_activity_list.class);
                                         startActivity(intent);
                                     }
                                 }
        );
        mImageView = (ImageView) v.findViewById(R.id.sk_link);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.slavkaliy.com"));
                startActivity(intent);
            }
        });

        return v;
    }


}
