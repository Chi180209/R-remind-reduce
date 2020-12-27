package com.ccmcteam.ccmcteam.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    public TextView recipeNameTv, categoryTv, timecookTv;
    public ImageView recipeImage, iconCategory;

    View view;


    public RecipeViewHolder(@NonNull View itemView) {
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
        recipeNameTv = itemView.findViewById(R.id.recipe_name_tv);
        categoryTv = itemView.findViewById(R.id.category_tv);
        timecookTv = itemView.findViewById(R.id.time_cook_tv);
        recipeImage = itemView.findViewById(R.id.recipe_img);
        iconCategory = itemView.findViewById(R.id.icon_category_food_iv);
    }


    private RecipeViewHolder.ClickListener mClickListener;
    //interface for click listener
    public interface ClickListener{
        void onItemClick (View view, int position);
        void onItemLongClick (View view, int position);
    }

    public void setOnClickListener (RecipeViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }

}
