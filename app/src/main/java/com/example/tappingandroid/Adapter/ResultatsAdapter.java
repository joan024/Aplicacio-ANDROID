package com.example.tappingandroid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.R;

import java.util.List;

public class ResultatsAdapter extends RecyclerView.Adapter<ResultatsAdapter.MyViewHolder> {
        private List<String> mDataset;

        public ResultatsAdapter(List<String> dataset) {
            mDataset = dataset;
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;

            public MyViewHolder(View v) {
                super(v);
                mTextView = v.findViewById(R.id.text_view);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultats_rv, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mTextView.setText(mDataset.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
