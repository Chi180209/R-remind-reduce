package com.ccmcteam.ccmcteam.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ccmcteam.ccmcteam.Model.Firebase.FBRecipe;
import com.ccmcteam.ccmcteam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RecipeFormInsertActivity extends AppCompatActivity {

    EditText et_recipe_name, et_time_cook, et_howto_cook;
    Spinner spn_categories;
    Button btn_create_recipe, btn_add_ingredient;
    ImageView img_recipe_image;

    //firebase
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    public static FirebaseUser user;


    //recipe image
    static int Preqcode = 1;
    static int RequesCode = 1;
    public Uri pickedImageUri;
    public Bitmap selectedBipmap;

    //progressbar to display while registering user
    ProgressDialog progressDialog;

    //key to recive data intent
    String pId, pName, pTimeCook, pCategory, pImage, pHowtoCook;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity_form_insert);

        //action bar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init
        et_recipe_name = findViewById(R.id.RecipeNameEt);
        et_time_cook = findViewById(R.id.TimeCookEt);
        et_howto_cook = findViewById(R.id.howtoEt);
        spn_categories = findViewById(R.id.spinner_dropdown_category);

        btn_create_recipe = findViewById(R.id.CreateRecipeBtn);
        btn_add_ingredient = findViewById(R.id.addIngredientBtn);

        img_recipe_image = findViewById(R.id.recipe_form_img);

        //init Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //init database
        reference = FirebaseDatabase.getInstance().getReference("Users");

        //comeback here to update data after click Update option (from AlertDialog)
        //then get data from intent and set to EditText
        //Change title of Action bar and Save btn
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //update data
            actionBar.setTitle("Update Recipe");
            btn_create_recipe.setText("Update");
            btn_add_ingredient.setText("Update Ingredients");
            btn_add_ingredient.setVisibility(View.VISIBLE);

            //get data
            pId = bundle.getString("pId");
            pName = bundle.getString("pName");
            pTimeCook = bundle.getString("pTimeCook");
            pCategory = bundle.getString("pCategory");
            pHowtoCook = bundle.getString("pHowtoCook");
            pImage = bundle.getString("pImage");

            //get image from intent
            byte[] bytes = bundle.getByteArray("pImage");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

            //set data to views
            et_recipe_name.setText(pName);
            et_time_cook.setText(pTimeCook);
            et_howto_cook.setText(pHowtoCook);
            // *** set data for spinner

            //set data for image
            img_recipe_image.setImageBitmap(bitmap);

            //handle Add Ingredient button
            btn_add_ingredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(RecipeFormInsertActivity.this, AddIngredient.class);

                    //put data to intent
                    intent.putExtra("recipeId",pId);
                    intent.putExtra("recipeName",pName);

                    //start Activity
                    startActivity(intent);
                }
            });
        }
        else {
            //new data
            actionBar.setTitle("Create New Recipe");
            btn_create_recipe.setText("Create");
            btn_add_ingredient.setText("Add Ingredients");
        }

        //handle Create button
        btn_create_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null){
                    //updating
                    String id = pId;
                    String name = et_recipe_name.getText().toString().trim();
                    String time = et_time_cook.getText().toString().trim();
                    String category = spn_categories.getSelectedItem().toString();
                    String howto = et_howto_cook.getText().toString().trim();

                    //*hinh khong biet update the nao
                    //lay hinh tu view
                    ImageView imageView = v.findViewById(R.id.recipe_form_img);

                    Drawable drawable = imageView.getDrawable();
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
                    byte[] bytes = stream.toByteArray();

                    //convert bitmap to string
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    try {
//                        selectedBipmap = MediaStore.Images.Media.getBitmap(getContentResolver(),pickedImageUri);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    selectedBipmap.compress(Bitmap.CompressFormat.PNG,1000,byteArrayOutputStream);
                    //byte[] byteArray = byteArrayOutputStream.toByteArray();

                    String avatarTo64String = Base64.encodeToString(bytes,Base64.DEFAULT);

                    updateData( id, name, time, category, avatarTo64String, howto );

                }
                else {
                    createRecipe();

                }
            }
        });



        //init progressDialog
        progressDialog = new ProgressDialog(this);


        //recipe image select event
        img_recipe_image.setOnClickListener(new View.OnClickListener() {
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

    //recipe image select event
    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,RequesCode);

    }

    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(RecipeFormInsertActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(RecipeFormInsertActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(RecipeFormInsertActivity.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(RecipeFormInsertActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Preqcode);
            }
        }
        else
            openGallery();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RequesCode && data != null){


            // the user has sucessfully picked an image
            //we need to save its reference to a Uri variable
            pickedImageUri = data.getData();
            img_recipe_image.setImageURI(pickedImageUri);
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

    //method update recipe
    private void updateData(String id, String name, String time, String category, String avatarTo64String, String howto) {

        //set title of progressDialog
        progressDialog.setMessage("Updating Recipe...");
        //show progress dialog when user click save button
        progressDialog.show();

        String uid = user.getUid();

        DatabaseReference dataRecipe = reference.child(uid).child("recipes").child(category);

        FBRecipe recipe = new FBRecipe(id, name, time, category, avatarTo64String, howto);
        dataRecipe.child(id).setValue(recipe)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //this will be called when data is added successfully
                        progressDialog.dismiss();

                        //hien nut add ingredient
                        btn_add_ingredient.setVisibility(View.VISIBLE);

                        Toast.makeText(RecipeFormInsertActivity.this, "Updated",Toast.LENGTH_LONG).show();




                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //this will be called if there is any error while updating
                        progressDialog.dismiss();
                        //get and show error message
                        Toast.makeText(RecipeFormInsertActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

    }

    //method create recipe
    private void createRecipe(){
        //set title of progressDialog
        progressDialog.setMessage("Creating Recipe...");
        //show progress dialog when user click save button
        progressDialog.show();

        String name = et_recipe_name.getText().toString().trim();
        String time = et_time_cook.getText().toString().trim();
        String category = spn_categories.getSelectedItem().toString();
        String howto = et_howto_cook.getText().toString().trim();


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

        //yeu cau bat buoc dat ten cho mon an
        if (!TextUtils.isEmpty(name)){
            //get uid from auth
            String uid = user.getUid();

            DatabaseReference dataRecipe = reference.child(uid).child("recipes").child(category);

            String id = dataRecipe.push().getKey();


            final FBRecipe recipe = new FBRecipe(id, name, time, category, avatarTo64String, howto);
            dataRecipe.child(id).setValue(recipe)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //this will be called when data is added successfully
                            progressDialog.dismiss();
                            Toast.makeText(RecipeFormInsertActivity.this, "Created",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //this will be called if there is any error while updating
                            progressDialog.dismiss();
                            //get and show error message
                            Toast.makeText(RecipeFormInsertActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });

            //hien nut add ingredient
            btn_add_ingredient.setVisibility(View.VISIBLE);

            //handle Add Ingredient button
            btn_add_ingredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecipeFormInsertActivity.this, AddIngredient.class);

                    //put data to intent
                    intent.putExtra("recipeId",recipe.getRecipeId());
                    intent.putExtra("recipeName",recipe.getRecipeName());

                    //start Activity
                    startActivity(intent);
                }
            });
        }
        else {
            Toast.makeText(this,"You should enter a name",Toast.LENGTH_LONG).show();
        }

    }


    //nut quay lai
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
}
