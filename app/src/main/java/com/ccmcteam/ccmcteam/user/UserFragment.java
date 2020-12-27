package com.ccmcteam.ccmcteam.user;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.activities.signInUp.BeginActivity;
import com.ccmcteam.ccmcteam.activities.update_user_infomation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ccmcteam.ccmcteam.Model.Firebase.userInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

//    views
    private ImageView avatar_user_fragment ;
    private TextView name,mail,age,weight,height,BMI, BMIresult;

//    firebase libary
    private FirebaseDatabase saveDatabase;
    private DatabaseReference readDatabase;
    FirebaseAuth firebaseAuth;
    public String uId;
    private byte[] bImage;
//    class
    public userInfo user;
    private int id;

    public UserFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        getActivity().setTitle(R.string.menu_suckhoe);
        View v = inflater.inflate(R.layout.fragment_user, container, false);
//      ini views
        avatar_user_fragment =  v.findViewById(R.id.img_avata_user_fragment);
        name = v.findViewById(R.id.suckhoe_userName);
        mail = v.findViewById(R.id.suckhoe_userGmail);
        age = v.findViewById(R.id.suckhoe_userAge);
        weight = v.findViewById(R.id.suckhoe_UserWeight);
        height = v.findViewById(R.id.suckhoe_UserHeight);
        BMI = v.findViewById(R.id.tv_BMI);
        BMIresult = v.findViewById(R.id.BMIresultTv);

        saveDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser fireUser = firebaseAuth.getCurrentUser();
        uId = fireUser.getUid();
        readDatabase = saveDatabase.getReference("Users/" + uId);


//        lay thong tin tu firebase ve
        readDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                takeData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

    private void takeData(DataSnapshot dataSnapshot) {
//        xuat du lieu tu firebase ra
        user = new userInfo();
        user = dataSnapshot.child("infomation").getValue(userInfo.class);
//        convert string to image
        if (dataSnapshot.hasChild("infomation")) {
            String sImage = user.getImage();
            bImage = Base64.decode(sImage, Base64.DEFAULT);

            Bitmap decodedImage = BitmapFactory.decodeByteArray(bImage, 0, bImage.length);
            avatar_user_fragment.setImageBitmap(decodedImage);

        }else {
            BMI.setText(R.string.setInfoPlease);
        }


//        set text for text view
        if (dataSnapshot.hasChild("infomation")) {
            name.setText(user.getName());
        }
        if (dataSnapshot.hasChild("infomation")) {
            mail.setText(user.getEmail());
        }
        if (dataSnapshot.hasChild("infomation")) {
            age.setText(user.getAge());
        }
        if (dataSnapshot.hasChild("infomation")) {
            weight.setText(user.getWeight());
        }
        if (dataSnapshot.hasChild("infomation")) {
            height.setText(user.getHeight());
        }

        //set ket qua BMI
        int weight = Integer.parseInt(user.getWeight());
        int height = Integer.parseInt(user.getHeight());

        double bmi = weight*10000/(height*height);

        BMIresult.setText(String.valueOf(bmi));

        if ( bmi <= 18.5){
            BMI.setText("Your weight is below normal and you need to gain some weight to mantain you health. ");
        }
        else if (18.5 < bmi && bmi <= 24.9){
            BMI.setText("You have a healthy weight. Keep going like this to decrease health problems.");
        }
        else if (25 <= bmi && bmi<= 29.9){
            BMI.setText("You are overweight now. You need to lose some weight by balance you daily meal, avoid fast foods, sweets and fried foods and increase exercises everyday.");
        }
        else {
            BMI.setText("It is very dangerous to have this high BMI. Your health is at alert rate. You need to see doctor, discuss about how to regain your healthy weight, having safe diet plan and suitable exercises.");
        }

    }

    //set option menu in to user fragment
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
//    set menu item click event
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.update_user_infomation :
                intent = new Intent(getActivity(),update_user_infomation.class );
                startActivity(intent);
                break;
            case R.id.logout:
                firebaseAuth.signOut();
                checkUserStatus();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void checkUserStatus(){
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Intent intent;
        if (user == null){
            //user not signed in, go to begin activity
            intent = new Intent(getActivity(),BeginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        //check on start of app
        checkUserStatus();
        super.onStart();
    }
}
