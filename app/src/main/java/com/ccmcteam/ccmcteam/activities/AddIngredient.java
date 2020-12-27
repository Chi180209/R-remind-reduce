package com.ccmcteam.ccmcteam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ccmcteam.ccmcteam.Adapter.FBIngOfReAdapter;
import com.ccmcteam.ccmcteam.Adapter.FBIngredientAdapter;
import com.ccmcteam.ccmcteam.Adapter.FBRecipeAdapter;
import com.ccmcteam.ccmcteam.Model.Firebase.FBIngredientOfRecipe;
import com.ccmcteam.ccmcteam.Model.Firebase.FBRecipe;
import com.ccmcteam.ccmcteam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddIngredient extends AppCompatActivity {
    //views
    TextView tv_recipe_name, tv_ing_push_list;
    EditText et_ing_name, et_ing_amount, et_ing_unit;
    Button btn_add;
    RecyclerView rv_ing_list;

    //model data
    List<FBIngredientOfRecipe> ingList;

    //recyclerview adapter
    FBIngOfReAdapter adapter;


    //firebase
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    public static FirebaseUser user;

    //progressbar to display while registering user
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        //action bar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Ingredient to Recipe");

        //init views
        tv_recipe_name = findViewById(R.id.rcNameTV);
        tv_ing_push_list = findViewById(R.id.ingpushlist);
        et_ing_name = findViewById(R.id.ingNameEt);
        et_ing_amount = findViewById(R.id.ingAmountEt);
        et_ing_unit = findViewById(R.id.ingUnitEt);
        rv_ing_list = findViewById(R.id.ingListRV);
        btn_add = findViewById(R.id.addIngBtn);

        //recieve data from intent bundle
        final Bundle bundle = getIntent().getExtras();
        //String rcId = bundle.getString("recipeId");
        String rcName = bundle.getString("recipeName");

        //set recipe name to TextView form recieved intent data
        tv_recipe_name.setText(rcName);

        //init Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //init database
        reference = FirebaseDatabase.getInstance().getReference("Users");

        //click on add button
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add ingredient to recipe
                addIngredient();
            }
        });


        //set recyclerview
        rv_ing_list.setLayoutManager(new LinearLayoutManager(this));

        //init recipe list
        ingList = new ArrayList<>();

        tv_ing_push_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get recipe frome firebase
                getIngredient();

            }
        });




    }

    private void getIngredient() {
        //get id Recipe from intent
        final Bundle bundle = getIntent().getExtras();
        String idRc = bundle.getString("recipeId");

        //init Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//        user = mAuth.getCurrentUser();
        //get uid from auth
        String uid = user.getUid();

        //init reference database
        DatabaseReference db = reference.child(uid).child("ingredients of recipes");
        db.orderByChild("idRc").equalTo(idRc).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ingList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    FBIngredientOfRecipe ingOfRecipe = ds.getValue(FBIngredientOfRecipe.class);
                    ingList.add(ingOfRecipe);
                }
                //adapter
                adapter = new FBIngOfReAdapter(ingList, AddIngredient.this);
                //set adapter to recycler view
                rv_ing_list.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddIngredient.this, databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

    private void addIngredient() {

        //set title of progressDialog
        //progressDialog.setMessage("Adding Ingredient...");
        //show progress dialog when user click add button
        //progressDialog.show();

        //get id recipe from intent
        final Bundle bundle = getIntent().getExtras();
        String idRc = bundle.getString("recipeId");

        //get uid from auth
        String uid = user.getUid();

        DatabaseReference dataIngOfRecipe = reference.child(uid).child("ingredients of recipes");

        String id = dataIngOfRecipe.push().getKey();

        String name = et_ing_name.getText().toString().trim();
        String amount = et_ing_amount.getText().toString().trim();
        String unit = et_ing_unit.getText().toString().trim();

        final FBIngredientOfRecipe ingredient = new FBIngredientOfRecipe(idRc, id, name, amount, unit);
        dataIngOfRecipe.child(id).setValue(ingredient)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //this will be called when data is added successfully
                //progressDialog.dismiss();
                Toast.makeText(AddIngredient.this, "Added",Toast.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //this will be called if there is any error while updating
                        //progressDialog.dismiss();
                        //get and show error message
                        Toast.makeText(AddIngredient.this, e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

    }




}
