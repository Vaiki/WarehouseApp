package com.vaiki.android.warehouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.*;


/**
 * Created by E_not on 26.07.2018.
 */

public class Direct_fragment_list extends Fragment {
    private RecyclerView mDirectRecyclerView;
    private DirectAdapter mAdapter;
    private Direct mDirect;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.direct_fragment_list, container, false);
        mDirectRecyclerView = (RecyclerView) view.findViewById(R.id.direct_recycler_view);
        mDirectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;

    }


    private void updateUI() {
        String v = getActivity().getIntent().getStringExtra(ListDirectory.EXTRA_SORT_ITEM);
        DirectLab directLab = DirectLab.get(getActivity());

        mAdapter = new DirectAdapter(DirectLab.getSortItem(v,directLab));
        mDirectRecyclerView.setAdapter(mAdapter);
    }

    private class DirectHolder extends RecyclerView.ViewHolder {
        private TextView mDescription;
        private TextView mProduct;
        private TextView mQty;
        private Direct mDirect;

        public DirectHolder(View itemView) {
            super(itemView);
            mQty = (TextView) itemView.findViewById(R.id.qty);
            mProduct = (TextView) itemView.findViewById(R.id.product);
            mDescription = (TextView) itemView.findViewById(R.id.description);
        }
        public DirectHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.direct_item_list,parent,false));
            mDescription = (TextView) itemView.findViewById(R.id.product);
        }
        public void bind(Direct direct) { // получив direct метод обновит виджеты в соответствии с состоянием direct
            mDirect = direct;
            mProduct.setText(mDirect.getName_product());
            mQty.setText(String.valueOf(mDirect.getQty()));
            mDescription.setText(mDirect.getDescription());

        }
    }

    private class DirectAdapter extends RecyclerView.Adapter<DirectHolder> {
        private List<Direct> mDirects;

        public DirectAdapter(List<Direct> directs) {
            mDirects = directs;
        }

        @Override
        public DirectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.direct_item_list, parent, false);
            return new DirectHolder(v);


        }

        @Override
        public void onBindViewHolder(DirectHolder holder, int position) {
            Direct direct = mDirects.get(position);

            holder.bind(direct);
        }

        @Override
        public int getItemCount() {
            return mDirects.size();
        }
    }
}
