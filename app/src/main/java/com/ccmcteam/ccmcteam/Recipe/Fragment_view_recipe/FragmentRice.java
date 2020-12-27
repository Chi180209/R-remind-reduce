package com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.Adapter.FBRecipeAdapter;
import com.ccmcteam.ccmcteam.Model.Firebase.FBRecipe;
import com.ccmcteam.ccmcteam.R;
import com.ccmcteam.ccmcteam.activities.RecipeFormInsertActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.invalidateOptionsMenu;

public class FragmentRice extends Fragment {

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

    public FragmentRice() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.recipe_fragment_child,container,false);
        //recyclerview
        re = v.findViewById(R.id.recipeList_recyclerview);
        re.setLayoutManager(new GridLayoutManager(getContext(),2));

        //init recipe list
        recipeList = new ArrayList<>();
        //get recipe frome firebase
        getRecipe();

        setHasOptionsMenu(true);
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
        DatabaseReference db = reference.child(uid).child("recipes").child("Rice");
        if (db != null){
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    recipeList.clear();
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        FBRecipe recipe = ds.getValue(FBRecipe.class);
                        recipeList.add(recipe);
                        //adapter
                        adapter = new FBRecipeAdapter(recipeList, getContext());
                        //set adapter to recycler view
                        re.setAdapter(adapter);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), databaseError.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);
    }



//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//        if (isVisibleToUser)
//        {
//            search_bar.setOnQueryTextListener(this);
//            if(listAdapter!=null)
//                listAdapter.getFilter().filter(search_bar.getQuery());
//        }
//    }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.recipe_fragment_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.btn_search_recipe);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Search");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });


        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.add_recipe:
                intent = new Intent(getActivity(), RecipeFormInsertActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

        @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
            if(Build.VERSION.SDK_INT > 11) {
                menu.findItem(R.id.btn_search_recipe).setVisible(false);
                menu.findItem(R.id.add_recipe).setVisible(false);
            }
        super.onPrepareOptionsMenu(menu);
    }


    private void search (String str){
        ArrayList<FBRecipe> myList = new ArrayList<>();
        for (FBRecipe object : recipeList){
            if (object.getRecipeName().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        FBRecipeAdapter fbRecipeAdapter = new FBRecipeAdapter(myList,getContext());
        re.setAdapter(fbRecipeAdapter);
    }

}
