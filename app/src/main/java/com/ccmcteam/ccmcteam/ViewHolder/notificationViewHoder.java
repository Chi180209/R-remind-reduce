package com.ccmcteam.ccmcteam.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.R;

public class notificationViewHoder extends RecyclerView.ViewHolder {

    public TextView notiName,notiExpNote;
    public ImageView notiImage;
    //osusumepopup
    View view;


    public notificationViewHoder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });

        //init view
        notiName = itemView.findViewById(R.id.noti_gredient_name);
        notiExpNote = itemView.findViewById(R.id.noti_exp_note);
        notiImage = itemView.findViewById(R.id.noti_gredient_img);

    }

    private notificationViewHoder.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener (notificationViewHoder.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
