package com.ccmcteam.ccmcteam.activities.signInUp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    //views
    public static EditText mEmailEt, mPasswordEt,name,age,weight,height;
    public ImageView avatar;
    public Button mRegisterBtn;
    public TextView mHaveAccountTv;

    //avatar
    static int Preqcode = 1;
    static int RequesCode = 1;
    public Uri pickedImageUri;
    public Bitmap selectedBipmap;

    //progressbar to display while registering user
    ProgressDialog progressDialog;

    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    public static FirebaseUser user;
    //register ok check
    public boolean registerOk = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //init
        mEmailEt = findViewById(R.id.registerEmail);
        mPasswordEt = findViewById(R.id.passwordEt);
        name = findViewById(R.id.registerName);
        age = findViewById(R.id.registerAge);
        weight = findViewById(R.id.registerWeight);
        height = findViewById(R.id.registerHeight);
        avatar = findViewById(R.id.registerAvatar);
        mRegisterBtn = findViewById(R.id.RegisterBtn);
        mHaveAccountTv = findViewById(R.id.have_accountTv);

        //In the onCreate() method, initialize the FirebaseAuth instance
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        //avatar select event
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


        //handle register btn click
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input email, password
                String email = mEmailEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();

                //validate
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //set error and focuss to email edittext
                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);
                }
                else if (password.length()<6){
                    //set error and focuss to password edittext
                    mPasswordEt.setError("Password length at least 6 characters");
                    mPasswordEt.setFocusable(true);
                }
                else {
                    registerUser(email, password); //register the user
                }


            }
        });

        //handle login textview click listener
        mHaveAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();

            }
        });
    }

//    avatar select event
    private void openGallery() {
    //        TODO: open gallery intent and wait for user to pick an image
    Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
    gallery.setType("image/*");
    startActivityForResult(gallery,RequesCode);
}
    private void checkAndRequestForPermission() {

        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(RegisterActivity.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();
            }else {
                ActivityCompat.requestPermissions(RegisterActivity.this,
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

            pickedImageUri = data.getData() ;
            avatar.setImageURI(pickedImageUri);
        }
    }

//    ckeck confirm button click
    public boolean restorePrefData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        Boolean beforeOpened = pref.getBoolean("saveClicked",false);
        return beforeOpened;
    }
    public void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("saveClicked",true);
        editor.commit();

    }

//    register event
    private void registerUser(String email, String password)  {
        //email and password pattern is valid, show progress dialog and start registering user
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, dismiss dialog and start register activity
                            progressDialog.dismiss();

                            user = mAuth.getCurrentUser();

                            //get user email and uid from auth
                            String email = user.getEmail();
                            String uid = user.getUid();

                            //get infomation
                            String name = RegisterActivity.name.getText().toString();
                            String age = RegisterActivity.age.getText().toString();
                            String Weight = RegisterActivity.weight.getText().toString();
                            String Height = RegisterActivity.height.getText().toString();


                            //convert bitmap to string
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            try {
                                selectedBipmap = MediaStore.Images.Media.getBitmap(getContentResolver(),pickedImageUri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            selectedBipmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            String avatarTo64String = Base64.encodeToString(byteArray,Base64.DEFAULT);


                            //when user is registered store user info in firebase realtime database too
                            //using HashMap
                            HashMap<Object, String> hashMap = new HashMap<>();
                            //put info in hasmap
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);


                            hashMap.put("name", name); // wil add later (e.g. edit profile)
                            hashMap.put("image", avatarTo64String);
                            hashMap.put("age",age);
                            hashMap.put("weight", Weight);
                            hashMap.put("height", Height);

                            //firebase database instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //path to store user data named "Users"
                            DatabaseReference reference = database.getReference("Users");
                            //put data within hashmap in database
                            reference.child(uid+"/infomation").setValue(hashMap);


                            Toast.makeText(RegisterActivity.this,"Registered...¥n" + user.getEmail(),Toast.LENGTH_SHORT).show();
                            //registered ckeck
                            registerOk = true;
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //error, dismiss progress dialog and get and show the error message
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this,"" + e.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // go previous activity
        return super.onSupportNavigateUp();
    }
}
