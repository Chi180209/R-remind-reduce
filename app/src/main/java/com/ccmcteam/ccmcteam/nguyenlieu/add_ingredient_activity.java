package com.ccmcteam.ccmcteam.nguyenlieu;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ccmcteam.ccmcteam.Model.Firebase.Items;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.activities.AddIngredient;
import com.ccmcteam.ccmcteam.activities.RecipeFormInsertActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class add_ingredient_activity extends AppCompatActivity {

    //views
    EditText name,amout,expDate,before;
    ImageView ingredientImage;
    Button creat;
    Spinner spCategory,spUnit;
    //firebase
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    public static FirebaseUser user;
    //recipe image
    static int Preqcode = 1;
    static int RequesCode = 1;
    public Uri pickedImageUri;
    public Bitmap selectedBipmap;
    String categories[] = {"Meat","Fish","FruitVegetable","Drink","Others"};
    String units[] = {"Gam","Lit","ml","cc","Kg","Bag"};
    String iId,iName,iCategory,iAmount,iUnit,iExpDate,iBefore,iImage;
    //progressbar to display while registering user
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient_activity);
        //action bar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //init views
        name = findViewById(R.id.ingredient_name_add);
        before = findViewById(R.id.ingredient_before_add);
        spCategory = findViewById(R.id.spinner_ingredient_category);
        amout = findViewById(R.id.ingredient_amount_add);
        spUnit = findViewById(R.id.spinner_unit);
        expDate = findViewById(R.id.ingredient_exp_add);

        creat = findViewById(R.id.btn_ingredient_add);
        ingredientImage = findViewById(R.id.ingredient_image);
        //init Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //init database
        reference = FirebaseDatabase.getInstance().getReference("Users");
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //update data
            actionBar.setTitle("Update Ingredient");
            creat.setText("Update");

            //get data
            iId= bundle.getString("iId");
            iName = bundle.getString("iName");
            iCategory = bundle.getString("iCategory");
            iAmount = bundle.getString("iAmount");
            iUnit = bundle.getString("iUnit");
            iExpDate = bundle.getString("iExpDate");
            iBefore = bundle.getString("iBefore");

            //get image from intent
            byte[] bytes = bundle.getByteArray("iImage");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

            //set data to views
            name.setText(iName);
            before.setText(iBefore);
            amout.setText(iAmount);
            expDate.setText(iExpDate);
            // *** set data for spinner

            //set data for image
            ingredientImage.setImageBitmap(bitmap);

            //handle Add Ingredient button
            creat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(add_ingredient_activity.this, AddIngredient.class);

                    //put data to intent
                    intent.putExtra("ingreId",iId);
                    intent.putExtra("ingreName",iName);

                    //start Activity
                    startActivity(intent);
                }
            });
        }
        else {
            //new data
            actionBar.setTitle("Create New Recipe");
            creat.setText("Create");
        }
        //init progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Ingredient...");
        //handle category spinner
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(categoryAdapter);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("select Category")){
                    //do nothing
                }
                else {
                    //on selecting a spinner item
                    String item = parent.getItemAtPosition(position).toString();

                    //anything you want to do on item selected to do here
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //handle unit spinner
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, units);
        spUnit.setAdapter(unitAdapter);

        spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("select Unit")){
                    //do nothing
                }
                else {
                    //on selecting a spinner item
                    String item = parent.getItemAtPosition(position).toString();

                    //anything you want to do on item selected to do here
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //recipe image select event
        ingredientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 16){
                    checkAndRequestForPermission();
                }else {
                    openGallery();
                }
            }
        });
        //creat new ingredient
        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatIngredient();
            }
        });
    }
    private void checkAndRequestForPermission(){
        if (ContextCompat.checkSelfPermission(add_ingredient_activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(add_ingredient_activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(add_ingredient_activity.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(add_ingredient_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Preqcode);
            }
        }
        else
            openGallery();
    }
    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,RequesCode);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RequesCode && data != null){
            // the user has sucessfully picked an image
            //we need to save its reference to a Uri variable
            pickedImageUri = data.getData();
            ingredientImage.setImageURI(pickedImageUri);
        }
    }
    //    ckeck confirm button click
    public boolean restorePrefData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        Boolean beforeOpened = pref.getBoolean("saveClicked",false);
        return beforeOpened;
    }
    public void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("saveClicked", true);
        editor.commit();
    }

    //nut back tren menu bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
                default:break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void creatIngredient(){
        String iName = name.getText().toString().trim();
        String iAmount = amout.getText().toString().trim();
        String iUnit = spUnit.getSelectedItem().toString();
        String iCategory = spCategory.getSelectedItem().toString();
        String iExp = expDate.getText().toString();
        String iBefore = before.getText().toString();
        //convert bitmap to string
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            selectedBipmap = MediaStore.Images.Media.getBitmap(getContentResolver(),pickedImageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        selectedBipmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String iImage = Base64.encodeToString(byteArray,Base64.DEFAULT);

        //yeu cau bat buoc dat ten cho mon an
        if (!TextUtils.isEmpty(iName)){

            progressDialog.dismiss();

            user = mAuth.getCurrentUser();

            //get uid from auth
            String uid = user.getUid();

            DatabaseReference dataIngredient = reference.child(uid).child("ingredients").child(iCategory);
            String id = dataIngredient.push().getKey();
            Items items = new Items(iAmount,iBefore, iCategory, iExp,iImage,id,iName,iUnit);
            dataIngredient.child(id).setValue(items);

            Toast.makeText(this, "Created",Toast.LENGTH_LONG).show();


        }
        else {
            Toast.makeText(this,"You should enter a name",Toast.LENGTH_LONG).show();

        }
        finish();
    }


}
