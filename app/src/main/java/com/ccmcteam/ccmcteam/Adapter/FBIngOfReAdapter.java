package com.ccmcteam.ccmcteam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.Model.Firebase.FBIngredientOfRecipe;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.ViewHolder.IngOfReVH;


import java.util.List;

public class FBIngOfReAdapter extends RecyclerView.Adapter<IngOfReVH> {

    List<FBIngredientOfRecipe> ingList;
    Context context;

    public FBIngOfReAdapter(List<FBIngredientOfRecipe> ingList, Context context) {
        this.ingList = ingList;
        this.context = context;
    }

    @NonNull
    @Override
    public IngOfReVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout chua viewholder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_model_cook_ingredient,parent,false);
        IngOfReVH viewHolder = new IngOfReVH(itemView);
        viewHolder.setOnClickListener(new IngOfReVH.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngOfReVH holder, int position) {
        //bind views get/ set data
        holder.IngNameTv.setText(ingList.get(position).getIngName());
        holder.IngAmountTv.setText(ingList.get(position).getIngAmount());
        holder.IngUnitTv.setText(ingList.get(position).getIngUnit());

    }

    @Override
    public int getItemCount() {
        return ingList.size();
    }
}
