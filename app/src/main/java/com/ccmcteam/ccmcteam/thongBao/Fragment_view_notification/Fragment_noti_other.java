package com.ccmcteam.ccmcteam.thongBao.Fragment_view_notification;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ccmcteam.ccmcteam.Adapter.FBNotificationAdapter;
import com.ccmcteam.ccmcteam.Model.Firebase.Notification;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_noti_other extends Fragment {


    View v;
    RecyclerView recyclerView;
    FBNotificationAdapter adapter;
    Notification notification;
    //lay mau
    List<Notification> notificationList;
    //pregress dialog
    ProgressDialog progressDialog;
    //Firebase
    DatabaseReference reference;
    FirebaseAuth mAuth;
    public static FirebaseUser user;
    public String uid;

    public Fragment_noti_other() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.noti_other,container,false);
        recyclerView = v.findViewById(R.id.noti_other_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        //init
        notificationList = new ArrayList<>();
        //read data
        getNotification();
        return v;
    }

    private void getNotification() {
        //init Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        //get uid from auth
        uid = user.getUid();
        //init reference database
        reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference db = reference.child(uid).child("Notification").child("Others");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notificationList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    notification = ds.getValue(Notification.class);
                    notificationList.add(notification);
                    //adapter
                    adapter = new FBNotificationAdapter(getActivity(), notificationList);
                    //set adapter to recycler view
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
