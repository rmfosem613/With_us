package com.example.with_us.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.with_us.PostActivity;
import com.example.with_us.PostEventActivity;
import com.example.with_us.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventFragment extends Fragment {

    FloatingActionButton fab_event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event2, container, false);

        fab_event = view.findViewById(R.id.fab_event);

        fab_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostEventActivity.class));
            }
        });

        return view;
    }


}