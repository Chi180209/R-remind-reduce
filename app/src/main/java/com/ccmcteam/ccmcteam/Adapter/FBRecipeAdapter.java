package com.ccmcteam.ccmcteam.Adapter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.Model.Firebase.FBRecipe;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe.FragmentHowToCook;
import com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe.FragmentIngredient;
import com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe.FragmentRice;
import com.ccmcteam.ccmcteam.ViewHolder.RecipeViewHolder;
import com.ccmcteam.ccmcteam.activities.CookActivity;
import com.ccmcteam.ccmcteam.activities.RecipeFormInsertActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.ccmcteam.ccmcteam.activities.RecipeFormInsertActivity.user;

public class FBRecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {


    List<FBRecipe> recipeList;
    Context context;

    public FBRecipeAdapter(List<FBRecipe> recipeList, Context context) {

        this.recipeList = recipeList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout chua viewholder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_model_cardview_dish_child,parent,false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(itemView);

        //handle item click here
        viewHolder.setOnClickListener(new RecipeViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //get data
                //get data
                String id = recipeList.get(position).getRecipeId();
                String name = recipeList.get(position).getRecipeName();
                String timeCook = recipeList.get(position).getTimeCook();
                String category = recipeList.get(position).getRecipeCategory();
                String howtoCook = recipeList.get(position).getRecipeHowto();

                //view for image
                ImageView mRecipeImg = view.findViewById(R.id.recipe_img);
                //get image from view
                Drawable drawable = mRecipeImg.getDrawable();
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

                //intent to start Cook activity
                Intent intent = new Intent(context, CookActivity.class);

                //put data in intent
                intent.putExtra("pId",id);
                intent.putExtra("pName",name);
                intent.putExtra("pTimeCook",timeCook);
                intent.putExtra("pCategory",category);
                intent.putExtra("pHowtoCook",howtoCook);

                //put image in intent
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
                byte[] bytes = stream.toByteArray();
                intent.putExtra("pImage",bytes);

                //start activity
                context.startActivity(intent);

                //**send data to Fragment Ingredient, How to cook
                Bundle bundle = new Bundle();
                bundle.putString("pId", id);
//                //bundle.putString("recipeHowtoCook",howtoCook);
                FragmentIngredient fragmentIngredient = new FragmentIngredient();
//                //FragmentHowToCook fragmentHowToCook = new FragmentHowToCook();
                fragmentIngredient.setArguments(bundle);
//                //fragmentHowToCook.setArguments(bundle);














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
                            String id = recipeList.get(position).getRecipeId();
                            String name = recipeList.get(position).getRecipeName();
                            String timeCook = recipeList.get(position).getTimeCook();
                            String category = recipeList.get(position).getRecipeCategory();
                            String howtoCook = recipeList.get(position).getRecipeHowto();

                            //view for image
                            ImageView mRecipeImg = view.findViewById(R.id.recipe_img);
                            //get image from view
                            Drawable drawable = mRecipeImg.getDrawable();
                            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

                            //intent to start activity
                            Intent intent = new Intent(context, RecipeFormInsertActivity.class);

                            //put data in intent
                            intent.putExtra("pId",id);
                            intent.putExtra("pName",name);
                            intent.putExtra("pTimeCook",timeCook);
                            intent.putExtra("pCategory",category);
                            intent.putExtra("pHowtoCook",howtoCook);

                            //put image in intent
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
                            byte[] bytes = stream.toByteArray();
                            intent.putExtra("pImage",bytes);

                            //start activity
                            context.startActivity(intent);
                        }

                        if (which == 1){
                            //delete click

                            String id = recipeList.get(position).getRecipeId();
                            String category = recipeList.get(position).getRecipeCategory();
                            String name = recipeList.get(position).getRecipeName();


                            //getting reference of review
                            FirebaseAuth mAuth;
                            mAuth = FirebaseAuth.getInstance();
                            user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("recipes").child(category).child(id);


                            //remove recipe
                            dR.removeValue();
                            Toast.makeText(context, "Deleted Recipe" + name, Toast.LENGTH_LONG).show();
                        }
                    }
                }).create().show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        //bind views get/ set data
        holder.recipeNameTv.setText(recipeList.get(position).getRecipeName());
        holder.categoryTv.setText(recipeList.get(position).getRecipeCategory());
        holder.timecookTv.setText(recipeList.get(position).getTimeCook());

        String string = recipeList.get(position).getRecipeCategory();

        switch (string){
            case "Rice":
                holder.iconCategory.setImageResource(R.drawable.ic_food_rice);
                break;
            case "Soup":
                holder.iconCategory.setImageResource(R.drawable.ic_food_soup);
                break;
            case "Salad":
                holder.iconCategory.setImageResource(R.drawable.ic_food_salad);
                break;
            case "Dessert":
                holder.iconCategory.setImageResource(R.drawable.ic_food_dessert);
                break;
            case "Others":
                holder.iconCategory.setImageResource(R.drawable.ic_other);
                break;
        }




        /* bat buoc phai co hinh, khong co hinh la loi */
        String sImage = recipeList.get(position).getRecipeImage();
        byte[] bImage = Base64.decode(sImage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(bImage, 0, bImage.length);
        holder.recipeImage.setImageBitmap(decodedImage);

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
