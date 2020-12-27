package com.ccmcteam.ccmcteam.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ccmcteam.ccmcteam.Model.Firebase.userInfo;
import com.ccmcteam.ccmcteam.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class update_user_infomation extends AppCompatActivity {

//    views
    public Button update,cancle;
    public ImageView avatar;
    public EditText name,mail,age,weight,height,pass;


//    firebase libary
    private FirebaseDatabase saveDatabase;
    private DatabaseReference readDatabase;
    FirebaseAuth firebaseAuth;
    String uId;

    //    class
    private userInfo user;
    private int id;
//    set avatar
    static int Preqcode = 1;
    static int RequesCode = 1;
    private Uri updateImageUri;
    Bitmap updateBipmap;
    String avatarTo64String;
    byte[] upImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_infomation);

//        init view
        update = findViewById(R.id.btn_update_user_info);
        cancle = findViewById(R.id.btn_cancle);
        avatar = findViewById(R.id.img_avt_update_v2);

        name = findViewById(R.id.edt_user_update);
        mail = findViewById(R.id.edt_gmail_update);
        pass = findViewById(R.id.edt_pass_update);
        age = findViewById(R.id.edt_age_update);
        weight = findViewById(R.id.edt_weight_update);
        height = findViewById(R.id.edt_height_update);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser fireUser = firebaseAuth.getCurrentUser();
        uId = fireUser.getUid();

        saveDatabase = FirebaseDatabase.getInstance();
        readDatabase = saveDatabase.getReference("Users/" + uId);

        readDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                takeData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        even cancle button click
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleBtn();
            }
        });

//        even update button click
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBtn();
            }
        });


//        image avata click listener
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= 16){
                    checkAndRequestForPermission();
                }else {
                    openGallery();
                }



            }
        });


    }
//update button click method
    private void updateBtn() {
        String userName = name.getText().toString();
        String userMail = mail.getText().toString();
        String userAge = age.getText().toString();
        String userweight = weight.getText().toString();
        String userHeight = height.getText().toString();

        //        dua hinh ve dang base64String
        if (updateImageUri != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                updateBipmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), updateImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateBipmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            avatarTo64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
            readDatabase.child("infomation").child("image").setValue(avatarTo64String);
        }

//        update even
        if (userName.equals("") != true || userName.equals(user.getName()) != true) {
            readDatabase.child("infomation").child("name").setValue(userName);
        }
        if (userMail.equals("") != true || userMail != user.getEmail()){
            readDatabase.child("infomation").child("email").setValue(userMail);
        }
        if(userAge.equals("") != true || userAge.equals(user.getAge()) != true) {
            readDatabase.child("infomation").child("age").setValue(userAge);
        }
        if(userweight.equals("") != true || userweight.equals(user.getWeight())) {
            readDatabase.child("infomation").child("weight").setValue(userweight);
        }
        if(userHeight.equals("") != true || userHeight.equals(user.getHeight()) != true) {
            readDatabase.child("infomation").child("height").setValue(userHeight);
        }

        finish();
    }

    //cancle button click method
    private void cancleBtn() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    //    read data method
    private void takeData(DataSnapshot dataSnapshot) {
//        xuat du lieu tu firebase ra
        user = new userInfo();
        user = dataSnapshot.child("infomation").getValue(userInfo.class);
//        convert string to image
        if (dataSnapshot.hasChild("infomation")) {
            String sImage = user.getImage();
            upImage = Base64.decode(sImage, Base64.DEFAULT);

            Bitmap decodedImage = BitmapFactory.decodeByteArray(upImage, 0, upImage.length);
            avatar.setImageBitmap(decodedImage);
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
    }
    //open gallery contrust
    private void openGallery() {
        //        TODO: open gallery intent and wait for user to pick an image
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery,RequesCode);
    }

    private void checkAndRequestForPermission() {

        if(ContextCompat.checkSelfPermission(update_user_infomation.this, Manifest.permission.READ_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(update_user_infomation.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(update_user_infomation.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();
            }else {
                ActivityCompat.requestPermissions(update_user_infomation.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Preqcode);
            }
        }else {
            openGallery();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == RequesCode && data != null) {

            updateImageUri = data.getData() ;
            avatar.setImageURI(updateImageUri);
        }
    }
}

