package com.ccmcteam.ccmcteam.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.Model.Firebase.Notification;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.ViewHolder.notificationViewHoder;
import com.ccmcteam.ccmcteam.nguyenlieu.Fragment_view_ingredient.FragmentDrink;
import com.ccmcteam.ccmcteam.nguyenlieu.Fragment_view_ingredient.FragmentFish;
import com.ccmcteam.ccmcteam.nguyenlieu.Fragment_view_ingredient.FragmentFruitVegetable;
import com.ccmcteam.ccmcteam.nguyenlieu.Fragment_view_ingredient.FragmentMeat;
import com.ccmcteam.ccmcteam.nguyenlieu.Fragment_view_ingredient.FragmentOther;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.ccmcteam.ccmcteam.activities.RecipeFormInsertActivity.user;

public class FBNotificationAdapter extends RecyclerView.Adapter<notificationViewHoder> {
    Context context;
    List<Notification> notificationList;
    public String nameNoti;

    public FBNotificationAdapter() {
    }

    public FBNotificationAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public notificationViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout chua viewholder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noti_model_cardview,parent,false);
        notificationViewHoder viewHolder = new notificationViewHoder(itemView);
        //handle item click here
        viewHolder.setOnClickListener(new notificationViewHoder.ClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                nameNoti = notificationList.get(position).getNotifiName();
                FragmentManager mfFragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                osusumePopup osusume = new osusumePopup();
//                osusume.show(mfFragmentManager,"osusumeDialog");
            }

            @Override
            public void onItemLongClick(final View view, final int position) {
                //this will be called when user long click item
                //creating alertdialog
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                //options to display in dialog
                String[] options = {"Check", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            if(notificationList.get(position).getNotiCategory().equals("Meat") == true){
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                FragmentMeat meatFr= new FragmentMeat();
                                activity.getSupportFragmentManager().
                                        beginTransaction().replace(R.id.frame_layout, meatFr).addToBackStack(null).commit();
                            }else if(notificationList.get(position).getNotiCategory().equals("Fish") == true){
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                FragmentFish fishFr= new FragmentFish();
                                activity.getSupportFragmentManager().
                                        beginTransaction().replace(R.id.frame_layout, fishFr).addToBackStack(null).commit();
                            }else if(notificationList.get(position).getNotiCategory().equals("Drink") == true) {
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                FragmentDrink drinkFr= new FragmentDrink();
                                activity.getSupportFragmentManager().
                                        beginTransaction().replace(R.id.frame_layout, drinkFr).addToBackStack(null).commit();
                            }else if(notificationList.get(position).getNotiCategory().equals("FruitVegetable") == true) {
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                FragmentFruitVegetable fruitVegetableFr= new FragmentFruitVegetable();
                                activity.getSupportFragmentManager().
                                        beginTransaction().replace(R.id.frame_layout, fruitVegetableFr).addToBackStack(null).commit();
                            }else if (notificationList.get(position).getNotiCategory().equals("Others") == true) {
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                FragmentOther otherFr= new FragmentOther();
                                activity.getSupportFragmentManager().
                                        beginTransaction().replace(R.id.frame_layout, otherFr).addToBackStack(null).commit();
                            }
                        }
                        if (which == 1){
                            //delete click
                            String id = notificationList.get(position).getThisId();
                            String category = notificationList.get(position).getNotiCategory();
                            //getting reference of review
                            FirebaseAuth mAuth;
                            mAuth = FirebaseAuth.getInstance();
                            user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Notification").child(category).child(id);


                            //remove recipe
                            dR.removeValue();
                            Toast.makeText(context, "Notification Deleted", Toast.LENGTH_LONG).show();
                        }
                    }
                }).create().show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull notificationViewHoder holder, int position) {
        //bind views get/ set data
        holder.notiName.setText(notificationList.get(position).getNotifiName());
        if(Integer.parseInt(notificationList.get(position).getNotifiFinish()) > 0) {
            holder.notiExpNote.setText(notificationList.get(position).getNotifiFinish() + " days left to expire");
        }else {
            holder.notiExpNote.setText("Expiration date has expired");
            holder.notiExpNote.setTextColor(Color.RED);
        }


        String sImage = notificationList.get(position).getNotifiImage();
        byte[] bImage = Base64.decode(sImage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(bImage, 0, bImage.length);
        holder.notiImage.setImageBitmap(decodedImage);
    }

    public int getItemCount() {
        return notificationList.size();
    }

}
