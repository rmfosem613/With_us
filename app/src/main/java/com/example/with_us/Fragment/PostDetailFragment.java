package com.example.with_us.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.with_us.Adapter.PostAdapter;
import com.example.with_us.Model.Post;
import com.example.with_us.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PostDetailFragment extends Fragment {

    String postid;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        SharedPreferences preferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        postid = preferences.getString("postid", "none");

        recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);


        readPost();



        return view;
    }

    private void readPost() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Posts").getRef();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // https://stackoverflow.com/questions/46429529/firebase-get-the-reference-of-the-child-of-a-child
                boolean bOffset = true;
                int offset = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //here is your every post
                    String key = snapshot.getKey();
                    Post value = snapshot.getValue(Post.class);

                    postList.add(value);

                    if (bOffset) {
                        offset++;
                    }

                    if (postid.equals(value.getPostid())) {
                        bOffset = false;
                        Log.d("ddd", "height: " + recyclerView.computeHorizontalScrollOffset());
                    }
                }

                postAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(offset - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
