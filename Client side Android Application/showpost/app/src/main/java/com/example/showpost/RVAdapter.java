package com.example.showpost;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolderClass> {
    private Context mContext;
    private String table1;
    private int row_id;

    public ArrayList<com.example.showpost.ModelClass> objectModelClassList;

    public RVAdapter(Context context,ArrayList<ModelClass> objectModelClassList,int row_id){
        this.objectModelClassList = objectModelClassList;
        mContext=context;
        this.row_id = row_id;
    }
    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_row,viewGroup,false);
        RVViewHolderClass holder = new RVViewHolderClass(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolderClass holder, int i) {
        com.example.showpost.ModelClass objectModelClass = objectModelClassList.get(i);

            holder.imageNameTV.setText(objectModelClass.getImageName());
            holder.objectImageView.setImageBitmap(objectModelClass.getImage());
            final int k = objectModelClass.getpostid();


            holder.imageNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, display.class);
                    intent.putExtra("text_row", k);
                    mContext.startActivity(intent);

                }
            });

            holder.objectImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(mContext, com.example.showpost.ImageEffect.class);
                    in.putExtra("img_row", k );
                    mContext.startActivity(in);
                }
            });

        }

    @Override
    public int getItemCount() {

        return objectModelClassList.size();
    }


    public static class RVViewHolderClass extends RecyclerView.ViewHolder

    {
        TextView imageNameTV;
        ImageView objectImageView;
        LinearLayout linearLayout;

        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            imageNameTV = itemView.findViewById(R.id.sr_imageDetailsTV);
            objectImageView = itemView.findViewById(R.id.sr_imageIV);
            linearLayout=itemView.findViewById(R.id.linear);
        }
    }

}
