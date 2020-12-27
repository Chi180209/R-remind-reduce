package com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.Adapter.FBRecipeAdapter;
import com.ccmcteam.ccmcteam.Model.Firebase.FBRecipe;
import com.ccmcteam.ccmcteam.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentDessert extends Fragment {

    View v;
    RecyclerView re;
    FBRecipeAdapter adapter;

    //lay mau
    List<FBRecipe> recipeList;


    //pregress dialog
    ProgressDialog progressDialog;

    //Firebase
    DatabaseReference reference;
    FirebaseAuth mAuth;
    public static FirebaseUser user;

    public FragmentDessert() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.recipe_fragment_child,container,false);

        //recyclerview
        re = v.findViewById(R.id.recipeList_recyclerview);
        re.setLayoutManager(new GridLayoutManager(getActivity(),2));

        //init recipe list
        recipeList = new ArrayList<>();

        //get recipe frome firebase
        getRecipe();

        return v;
    }

    private void getRecipe() {
        //init Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //get uid from auth
        String uid = user.getUid();

        //init reference database
        reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference db = reference.child(uid).child("recipes").child("Dessert");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    FBRecipe recipe = ds.getValue(FBRecipe.class);
                    recipeList.add(recipe);

                    //adapter
                    adapter = new FBRecipeAdapter(recipeList,getContext());
                    //set adapter to recycler view
                    re.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }







}
