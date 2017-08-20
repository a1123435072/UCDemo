package com.example.yangg.uc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yangg on 2017/7/6.
 */

public class MyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv  = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent,false);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "点击了", Toast.LENGTH_SHORT).show();
                    }
                });
                MyViewHolder holder=new MyViewHolder(itemView);
                return holder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyViewHolder mVH= (MyViewHolder) holder;
                mVH.tv.setText("条目"+position);
            }

            @Override
            public int getItemCount() {
                return 30;
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
