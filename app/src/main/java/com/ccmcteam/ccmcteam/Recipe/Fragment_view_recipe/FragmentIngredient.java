package com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.Adapter.FBIngOfReAdapter;
import com.ccmcteam.ccmcteam.Adapter.FBRecipeAdapter;
import com.ccmcteam.ccmcteam.Adapter.RecyclerViewAdapterCook;
import com.ccmcteam.ccmcteam.Model.Firebase.FBIngredientOfRecipe;
import com.ccmcteam.ccmcteam.Model.Firebase.FBRecipe;
import com.ccmcteam.ccmcteam.Model.Ingredient;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.activities.AddIngredient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentIngredient extends Fragment {

    View view;
    //recyclerview
    RecyclerView RE;

    //recyclerview adapter
    FBIngOfReAdapter adapter;
    //lay mau
    List<FBIngredientOfRecipe> ingList;

    //pregress dialog
    //ProgressDialog progressDialog;

    //Firebase
    DatabaseReference reference;
    FirebaseAuth mAuth;
    public static FirebaseUser user;



    public FragmentIngredient() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recipe_fragment_cook_ingredient,container,false);
        RE = view.findViewById(R.id.ingredient_recyclerview);
        RE.setLayoutManager(new LinearLayoutManager(getActivity()));
        //get ingredient from firebase for menu
        getIngredient();
        return view;
    }

    private void getIngredient() {

        //get id Recipe from intent
        Bundle bundle = this.getArguments();
        if (bundle != null){

            String idRc = bundle.getString("pId");

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
                    adapter = new FBIngOfReAdapter(ingList, getContext());
                    //set adapter to recycler view
                    RE.setAdapter(adapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), databaseError.getMessage(),Toast.LENGTH_LONG).show();

                }
            });
        }
        else {
            adapter = new FBIngOfReAdapter(ingList,getContext());
            RE.setAdapter(adapter);
        }


    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ingList = new ArrayList<>();
        ingList.add(new FBIngredientOfRecipe("1","1","sườn cốt lết cắt dày","1", "kg"));
        ingList.add(new FBIngredientOfRecipe("2","2","Xà lách","2", "búp"));
        ingList.add(new FBIngredientOfRecipe("3","3","cà chua ","3", "trái"));
        ingList.add(new FBIngredientOfRecipe("3","3","mật ong ","4", "muỗng"));
        ingList.add(new FBIngredientOfRecipe("3","3","nước cốt chanh ","1", "muỗng"));
        ingList.add(new FBIngredientOfRecipe("3","3","nước tương","3", "muỗng"));
        ingList.add(new FBIngredientOfRecipe("3","3","tỏi băm nhuyễn","1", "muỗng"));
        ingList.add(new FBIngredientOfRecipe("3","3","dầu hào","2", "muỗng"));
        ingList.add(new FBIngredientOfRecipe("3","3","hành tím băm nhuyễn","1", "muỗng"));
        ingList.add(new FBIngredientOfRecipe("3","3","đường","1", "muỗng"));
        ingList.add(new FBIngredientOfRecipe("3","3","tương ớt ","2", "muỗng"));
        ingList.add(new FBIngredientOfRecipe("3","3","dầu ăn ","2", "muỗng"));




//        lstIngredient.add(new Ingredient("玉ねぎ", "1/2", "個"));
//        lstIngredient.add(new Ingredient("卵", "2", "個"));
//        lstIngredient.add(new Ingredient("水", "100", "cc"));
//        lstIngredient.add(new Ingredient("砂糖", "1", "小さじ"));
//        lstIngredient.add(new Ingredient("醤油", "2", "大さじ"));
//        lstIngredient.add(new Ingredient("だしの素", "1", "小さじ"));
//        lstIngredient.add(new Ingredient("もも肉", "300", "g"));
//        lstIngredient.add(new Ingredient("玉ねぎ", "1/2", "個"));
//        lstIngredient.add(new Ingredient("卵", "2", "個"));
//        lstIngredient.add(new Ingredient("水", "100", "cc"));
//        lstIngredient.add(new Ingredient("砂糖", "1", "小さじ"));
//        lstIngredient.add(new Ingredient("醤油", "2", "大さじ"));
//        lstIngredient.add(new Ingredient("だしの素", "1", "小さじ"));
//        lstIngredient.add(new Ingredient("もも肉", "300", "g"));
//        lstIngredient.add(new Ingredient("玉ねぎ", "1/2", "個"));
//        lstIngredient.add(new Ingredient("卵", "2", "個"));
//        lstIngredient.add(new Ingredient("水", "100", "cc"));
//        lstIngredient.add(new Ingredient("砂糖", "1", "小さじ"));
//        lstIngredient.add(new Ingredient("醤油", "2", "大さじ"));
//        lstIngredient.add(new Ingredient("だしの素", "1", "小さじ"));
//        lstIngredient.add(new Ingredient("もも肉", "300", "g"));
//        lstIngredient.add(new Ingredient("玉ねぎ", "1/2", "個"));
//        lstIngredient.add(new Ingredient("卵", "2", "個"));
//        lstIngredient.add(new Ingredient("水", "100", "cc"));
//        lstIngredient.add(new Ingredient("砂糖", "1", "小さじ"));
//        lstIngredient.add(new Ingredient("醤油", "2", "大さじ"));
//        lstIngredient.add(new Ingredient("だしの素", "1", "小さじ"));小さじ
    }
}
