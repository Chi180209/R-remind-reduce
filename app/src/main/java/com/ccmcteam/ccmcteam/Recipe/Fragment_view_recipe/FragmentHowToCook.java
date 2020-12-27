package com.ccmcteam.ccmcteam.Recipe.Fragment_view_recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ccmcteam.ccmcteam.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class FragmentHowToCook extends Fragment {

    //views
    View v;
    TextView howtocookTv;

    //firebase
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    public static FirebaseUser user;


    public FragmentHowToCook() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.recipe_fragment_howtocook,container,false);

        //init views
        howtocookTv = v.findViewById(R.id.howtocookTv);

        //get data from bundle
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            String howToCook = bundle.getString("recipeHowtoCook");

            howtocookTv.setText(howToCook);
        }
        else {
            howtocookTv.setText("Sườn rửa sạch, ướp gia vị, các nguyên liệu khác, rửa sạch, để ráo. Cho thịt nướng chín, vàng đều. Sau đó dọn ra dùng nóng với cơm");
        }

        return v;
    }
}
