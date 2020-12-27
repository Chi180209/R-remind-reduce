package com.ccmcteam.ccmcteam.nguyenlieu.Fragment_view_ingredient;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.ccmcteam.ccmcteam.Adapter.FBIngredientAdapter;
import com.ccmcteam.ccmcteam.Model.Firebase.Items;
import com.ccmcteam.ccmcteam.Model.Firebase.Notification;
import com.ccmcteam.ccmcteam.nguyenlieu.add_ingredient_activity;
import com.ccmcteam.ccmcteam.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FragmentMeat extends Fragment {

    View v;
    RecyclerView re;
    FBIngredientAdapter adapter;
    //lay mau
    List<Items> ingredientList;
    //pregress dialog
    ProgressDialog progressDialog;
    //Firebase
    DatabaseReference reference;
    FirebaseAuth mAuth;
    Items items;
    public static FirebaseUser user;
    public String uid;

    public String expDiffDay = "";

    Notification checkNoti;
    public ArrayList<String> notiIds;


    public FragmentMeat() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.ingredient_fragment_meat,container,false);
        //recyclerview
        re = v.findViewById(R.id.meatView);
        re.setLayoutManager(new GridLayoutManager(getActivity(),1));
        //init recipe list
        ingredientList = new ArrayList<>();
        checkNoti = new Notification();
        //search
        //get recipe frome firebase
        notiIds = new ArrayList<>();
        check();
        getIngredient();
        return v;
    }
    private void getIngredient() {
        //init Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        //get uid from auth
        uid = user.getUid();
        //init reference database
        reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference db = reference.child(uid).child("ingredients").child("Meat");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ingredientList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        boolean checkHas = false;
                        items = ds.getValue(Items.class);
                        ingredientList.add(items);
                        //adapter
                        adapter = new FBIngredientAdapter(getActivity(), ingredientList);
                        for (int i = 0; i < notiIds.size(); i++) {
                            if (notiIds.get(i).equals(items.getName())) {
                                checkHas = true;
                                break;
                            }
                        }
                        if (checkHas == false) {
//                    caculer finish day
                            String getExpDate = items.getExpDate();
                            DateTimeFormatter formatter = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate expDate = LocalDate.parse(getExpDate, formatter);
                                LocalDate nowDate = LocalDate.now();
                                Period diff = Period.between(nowDate, expDate);
                                expDiffDay = String.valueOf(diff.getDays());
                            }
                            //set finish day to intification
                            int before = Integer.parseInt(items.getBefore());
                            if (Integer.parseInt(expDiffDay) <= before) {
                                addNotification(items);
                            }
                        }
                        //set adapter to recycler view
                        re.setAdapter(adapter);
                    }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void addNotification(Items items) {
        String notifiName = items.getName();
        String notifiId = items.getIngredientId();
        String notifiImage = items.getImage();
        String notifiCategory = items.getCategory();
        String notifiFinish = expDiffDay;
        //firebase init
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String uId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference notifiDb = reference.child(uId).child("Notification").child("Meat");
        String id = notifiDb.push().getKey();
        Notification notification = new Notification(notifiCategory,notifiFinish, notifiId, notifiImage, notifiName,id);
        notifiDb.child(id).setValue(notification);
        Toast.makeText(getActivity(), "Notification setting...", Toast.LENGTH_LONG).show();
    }

    private void check (){
        //init Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        //get uid from auth
        uid = user.getUid();
        //init reference database
        reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference dbNoti = reference.child(uid).child("Notification").child("Meat");
        dbNoti.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        checkNoti = dataSnapshot1.getValue(Notification.class);
                        notiIds.add(checkNoti.getNotifiName());
                    }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
////        inflater.inflate(R.menu.nguyenlieu_fragment_menu, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.btn_search_nguyenlieu);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//        searchView.setQueryHint("Search");
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//        if (searchView != null){
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    search(newText);
//                    return true;
//                }
//            });
//        }
//    }
//
//    private void search (String str){
//        ArrayList<Items> myList = new ArrayList<>();
//        for (Items object : ingredientList){
//            if (object.getName().toLowerCase().contains(str.toLowerCase())){
//                myList.add(object);
//            }
//        }
//        FBIngredientAdapter fbIngredientAdapter = new FBIngredientAdapter(getContext(), myList);
//        re.setAdapter(fbIngredientAdapter);
//    }

        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.add_nguyenlieu :
                intent = new Intent(getActivity(),add_ingredient_activity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
