package com.example.with_us.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.with_us.Adapter.ViewAdapter;
import com.example.with_us.Model.Board;
import com.example.with_us.PostPortfolioActivity;
import com.example.with_us.R;
import com.example.with_us.WriteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//리스트 보이기

public class HomeFragment extends Fragment {

    ViewAdapter adapter;
    DatabaseReference database;
    ArrayList<Board> boardData1;

    FloatingActionButton fab_write;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);

        fab_write = view.findViewById(R.id.fab_write);


        fab_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WriteActivity.class));
            }
        });

        boardData1 = new ArrayList<>();
        //아이템 추가 및 어댑터 등록

        RecyclerView recyclerView1 = view.findViewById(R.id.recycler_view);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ViewAdapter(boardData1, this);

        recyclerView1.setAdapter(adapter);

        database = FirebaseDatabase.getInstance().getReference("Board");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boardData1.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Board boardData3 = item.getValue(Board.class);

                    boardData1.add(boardData3);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }




}
