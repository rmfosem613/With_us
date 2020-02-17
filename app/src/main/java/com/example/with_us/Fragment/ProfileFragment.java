package com.example.with_us.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.with_us.LoginActivity;
import com.example.with_us.MainActivity;
import com.example.with_us.PostPortfolioActivity;
import com.example.with_us.R;
import com.example.with_us.RegisterActivity;

public class ProfileFragment extends Fragment {

    Button portfolio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container, false);

        portfolio = view.findViewById(R.id.portfolio);


        portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostPortfolioActivity.class));
            }
        });



        return view;


    }



}
