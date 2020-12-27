package com.ccmcteam.ccmcteam.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.R;

public class IngOfReVH extends RecyclerView.ViewHolder {
    public TextView IngNameTv, IngAmountTv, IngUnitTv;

    View view;

    public IngOfReVH(@NonNull View itemView) {
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

        //init view with model layout
        IngNameTv = itemView.findViewById(R.id.ingredient_name);
        IngAmountTv = itemView.findViewById(R.id.ingredient_amount);
        IngUnitTv = itemView.findViewById(R.id.ingredient_unit);
    }

    private IngOfReVH.ClickListener mClickListener;
    //interface for click listener
    public interface ClickListener{
        void onItemClick (View view, int position);
        void onItemLongClick (View view, int position);
    }

    public void setOnClickListener (IngOfReVH.ClickListener clickListener) {
        mClickListener = clickListener;
    }

}
