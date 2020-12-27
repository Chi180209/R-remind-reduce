package com.ccmcteam.ccmcteam.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.Model.Firebase.Items;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.ViewHolder.IngredientViewHolder;
import com.ccmcteam.ccmcteam.nguyenlieu.add_ingredient_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.ccmcteam.ccmcteam.activities.RecipeFormInsertActivity.user;

public class FBIngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    Context context;
    List<Items> ingredientList;
    String expDiffDay;
    public FBIngredientAdapter(Context context, List<Items> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout chua viewholder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_model_cardview,parent,false);
        IngredientViewHolder viewHolder = new IngredientViewHolder(itemView);
        //handle item click here
        viewHolder.setOnClickListener(new IngredientViewHolder.ClickListener() {

            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(final View view, final int position) {
                //this will be called when user long click item
                //creating alertdialog
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                //options to display in dialog
                String[] options = {"Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            //update is clicked
                            //get data
                            String id = ingredientList.get(position).getIngredientId();
                            String name = ingredientList.get(position).getName();
                            String category = ingredientList.get(position).getCategory();
                            String amount = ingredientList.get(position).getAmount();
                            String unit = ingredientList.get(position).getUnit();
                            String expDate = ingredientList.get(position).getExpDate();
                            String before = ingredientList.get(position).getBefore();

                            //view for image

                            ImageView mRecipeImg = view.findViewById(R.id.circleImage);
                            //get image from view

                            Drawable drawable = mRecipeImg.getDrawable();
                            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                            //intent to start activity

                            Intent intent = new Intent(context, add_ingredient_activity.class);
                            //put data in intent
                            intent.putExtra("iId",id);
                            intent.putExtra("iName",name);
                            intent.putExtra("iCategory",category);
                            intent.putExtra("iAmount",amount);
                            intent.putExtra("iUnit",unit);
                            intent.putExtra("iExpDate",expDate);
                            intent.putExtra("iBefore",before);

                            //put image in intent
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
                            byte[] bytes = stream.toByteArray();
                            intent.putExtra("iImage",bytes);

                            //start activity
                            context.startActivity(intent);
                        }

                        if (which == 1){
                            //delete click

                            String id = ingredientList.get(position).getIngredientId();
                            String category = ingredientList.get(position).getCategory();
                            //getting reference of review
                            FirebaseAuth mAuth;
                            mAuth = FirebaseAuth.getInstance();
                            user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ingredients").child(category).child(id);


                            //remove recipe
                            dR.removeValue();
                            Toast.makeText(context, "Ingredient Deleted", Toast.LENGTH_LONG).show();
                        }
                    }
                }).create().show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientViewHolder holder, int position) {
        //bind views get/ set data
        holder.iName.setText(ingredientList.get(position).getName());
        holder.iCategory.setText(ingredientList.get(position).getCategory());
        holder.iAmount.setText(ingredientList.get(position).getAmount());
        holder.iUnit.setText(ingredientList.get(position).getUnit());
        holder.iExp.setText(ingredientList.get(position).getExpDate());

        //
        String getExpDate = ingredientList.get(position).getExpDate();
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate expDate = LocalDate.parse(getExpDate, formatter);
            LocalDate nowDate = LocalDate.now();
            Period diff = Period.between(nowDate, expDate);
            expDiffDay = String.valueOf(diff.getDays());
        }
        //
        if(Integer.parseInt(expDiffDay) > 0) {
            holder.iFinish.setText(expDiffDay);
        }else {
            holder.iFinish.setText("Expiration date has ");
            holder.iFinish.setTextColor(Color.RED);
            holder.iExpUra.setText("expired");
            holder.iExpUra.setTextColor(Color.RED);
        }

        String sImage = ingredientList.get(position).getImage();
        byte[] bImage = Base64.decode(sImage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(bImage, 0, bImage.length);
        holder.iImage.setImageBitmap(decodedImage);
        //span
        holder.btn_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.expandableView_moredetail.getVisibility()==View.GONE){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(holder.cardView_container, new AutoTransition());
                    }
                    holder.expandableView_moredetail.setVisibility(View.VISIBLE);
                    holder.btn_expand.setBackgroundResource(R.drawable.ic_arrow_up_black);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(holder.cardView_container, new AutoTransition());
                    }
                    holder.expandableView_moredetail.setVisibility(View.GONE);
                    holder.btn_expand.setBackgroundResource(R.drawable.ic_arrow_down_black);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
}
