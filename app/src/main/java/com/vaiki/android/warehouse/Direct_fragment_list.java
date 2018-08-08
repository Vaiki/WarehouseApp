package com.vaiki.android.warehouse;

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


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.direct_fragment_list, container, false);
        mDirectRecyclerView = (RecyclerView) view.findViewById(R.id.direct_recycler_view);
        mDirectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        DirectLab directLab = DirectLab.get(getActivity());
        mAdapter = new DirectAdapter(directLab.getDirects());
        mDirectRecyclerView.setAdapter(mAdapter);
    }

    private class DirectHolder extends RecyclerView.ViewHolder {
        private TextView mDirectory;
        private Direct mDirect;

        public DirectHolder(View itemView) {
            super(itemView);
            mDirectory = (TextView) itemView.findViewById(R.id.directory);
        }

        public void bind(Direct direct) { // получив crime метод обновит виджеты в соответствии с состоянием crime
            mDirect = direct;

            mDirectory.setText(mDirect.getName_directory());

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
            //crime принимает данные из списка
            holder.bind(direct); // метод отображает данные crime в списке
        }

        @Override
        public int getItemCount() {
            return mDirects.size();
        }
    }
}
