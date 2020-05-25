package com.example.showpost;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class mainadapter extends RecyclerView.Adapter<mainadapter.ViewHolderClass> {
    private Context m1Context;

    public ArrayList<com.example.showpost.modelc1> objectModelClassList1;

    public mainadapter(Context context,ArrayList<modelc1> objectModelClassList){
        this.objectModelClassList1 = objectModelClassList;
        this.m1Context=context;

    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mainadap,viewGroup,false);
        ViewHolderClass holder1 = new ViewHolderClass(view);
        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder1, int i) {
        com.example.showpost.modelc1 objectModel = objectModelClassList1.get(i);
        holder1.topicname.setText(objectModel.getTopicname());
        holder1.topicdet.setText(objectModel.getTopicinfo());
        final int k1=i+1;

        holder1.topicdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(m1Context,postListing.class);
                intent.putExtra("text_row",k1);
                m1Context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return objectModelClassList1.size();
    }

    public static class ViewHolderClass extends RecyclerView.ViewHolder
    {
        TextView topicname;
        TextView topicdet;
        LinearLayout linearLayout;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            topicname = itemView.findViewById(R.id.topic);
            topicdet = itemView.findViewById(R.id.topicdet);
            linearLayout = itemView.findViewById(R.id.linear);
        }
    }

}
