package com.example.with_us.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.with_us.Adapter.EventAdapter;
import com.example.with_us.Adapter.MyEventAdapter;
import com.example.with_us.Model.Event;
import com.example.with_us.Model.User;
import com.example.with_us.PostEventActivity;
import com.example.with_us.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventFragment extends Fragment {

    FloatingActionButton fab_event;



    ImageView image_profile_event, event_image;
    TextView eventtitle, eventdate, username;

    //프로필에 보이기 위해서

    RecyclerView recyclerView;
    List<Event> eventList;
    MyEventAdapter myEventAdapter;

    FirebaseUser firebaseUser;
    String profileid;
//    List<Event> eventList_event;


    private User user;
    private Object Uri;

    @SuppressLint("RestrictedApi")
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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");


        image_profile_event = view.findViewById(R.id.image_profile_event);
        event_image = view.findViewById(R.id.event_image);
        eventtitle= view.findViewById(R.id.eventtitle);
        eventdate = view.findViewById(R.id.eventdate);
        username = view.findViewById(R.id.username);

/*
        recyclerView = view.findViewById(R.id.recycler_view_event);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this.getContext(), 2);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        eventList = new ArrayList<>();
        myEventAdapter = new MyEventAdapter(getContext(), eventList);
        recyclerView.setAdapter(myEventAdapter);
*/
        userInfo();
        readEvents();

        return view;
    }

    private void userInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getContext() == null) {
                    return;
                }

                User user = dataSnapshot.getValue(User.class);

                Glide
                        .with(getContext())
                        .load(user.getImageurl());
//                        .into(image_profile_event);
//                username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void readEvents() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    eventList.add(snapshot.getValue(Event.class));

                }
//                myEventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}