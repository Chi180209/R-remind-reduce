package com.ccmcteam.ccmcteam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.Model.Ingredient;
import com.ccmcteam.ccmcteam.R;

import java.util.List;

public class RecyclerViewAdapterCook extends RecyclerView.Adapter<RecyclerViewAdapterCook.MyViewHolderCook> {
    Context myContext;
    List<Ingredient> myData;

    public RecyclerViewAdapterCook(Context myContext, List<Ingredient> myData) {
        this.myContext = myContext;
        this.myData = myData;
    }

    @NonNull
    @Override
    public MyViewHolderCook onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(myContext).inflate(R.layout.recipe_model_cook_ingredient,parent,false);
        MyViewHolderCook viewHolderCook = new MyViewHolderCook(view);
        return viewHolderCook;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCook holder, int position) {
        holder.tv_ing_name.setText(myData.get(position).getIngName());
        holder.tv_ing_amount.setText(myData.get(position).getIngAmount());
        holder.tv_ing_unit.setText(myData.get(position).getIngUnit());

    }

    @Override
    public int getItemCount() {
        return myData.size();
    }


    public static class MyViewHolderCook extends RecyclerView.ViewHolder{
        private TextView tv_ing_name;
        private TextView tv_ing_amount;
        private TextView tv_ing_unit;


        public MyViewHolderCook(@NonNull View itemView) {
            super(itemView);

            tv_ing_name = itemView.findViewById(R.id.ingredient_name);
            tv_ing_amount = itemView.findViewById(R.id.ingredient_amount);
            tv_ing_unit = itemView.findViewById(R.id.ingredient_unit);
        }
    }

}
