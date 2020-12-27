package com.ccmcteam.ccmcteam.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    public TextView iName, iCategory, iAmount,iUnit,iExp,iFinish,iExpUra;
    public ImageView iImage;
    public Button btn_expand;
    public ConstraintLayout expandableView_moredetail;
    public CardView cardView_container;

    View view;

    public IngredientViewHolder(@NonNull View itemView) {
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
        iName = itemView.findViewById(R.id.ing_name);
        iCategory = itemView.findViewById(R.id.group_ingredient_detail);
        iAmount = itemView.findViewById(R.id.ingredient_storage_amount);
        iUnit = itemView.findViewById(R.id.ingredient_storage_unit);
        iExp = itemView.findViewById(R.id.exp_date);
        iFinish = itemView.findViewById(R.id.ingredient_status_date);

        iExpUra = itemView.findViewById(R.id.exp_ura);

        iImage = itemView.findViewById(R.id.circleImage);

        btn_expand = itemView.findViewById(R.id.arrowBtn);
        expandableView_moredetail = itemView.findViewById(R.id.expandableView);
        cardView_container = itemView.findViewById(R.id.cardView);


    }

    private IngredientViewHolder.ClickListener mClickListener;
    //interface for click listener
    public interface ClickListener{
        void onItemClick (View view, int position);
        void onItemLongClick (View view, int position);
    }

    public void setOnClickListener (IngredientViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
