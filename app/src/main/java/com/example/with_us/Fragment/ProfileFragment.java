package com.example.with_us.Fragment;

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

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.with_us.Adapter.MyPotofolioAdapter;
import com.example.with_us.EditProfileActivity;
import com.example.with_us.LoginActivity;
import com.example.with_us.MainActivity;
import com.example.with_us.Model.Post;
import com.example.with_us.Model.Project;
import com.example.with_us.Model.User;
import com.example.with_us.PostPortfolioActivity;
import com.example.with_us.R;
import com.example.with_us.RegisterActivity;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment {

    ImageView portfolio;

    ImageView image_profile;
    TextView posts, projects, heart, username, subject;
    Button edit_profile;

    //프로필에 보이기 위해서
    private List<String> mySaves;
    RecyclerView recyclerView_project;
    MyPotofolioAdapter myPotofolioAdapter_project;
    List<Post> postList_project;


    RecyclerView recyclerView;
    MyPotofolioAdapter myPortofolioAdapter;
    List<Post> postList;

    FirebaseUser firebaseUser;
    String profileid;

    ImageButton my_project, my_portfolio;


    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container, false);

        portfolio = view.findViewById(R.id.portfolio);
//포트폴리오 글쓰기 페이지로 가는 버튼
        portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostPortfolioActivity.class));
            }
        });

        //여기서 부터가 12강에서 본 프로필 만드는 기능들
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        image_profile = view.findViewById(R.id.image_profile);
        posts = view.findViewById(R.id.posts);
        projects = view.findViewById(R.id.projects);
        edit_profile = view.findViewById(R.id.edit_profile);
        my_project = view.findViewById(R.id.my_project);
        my_portfolio = view.findViewById(R.id.my_portfolio);
        username = view.findViewById(R.id.username);
        subject = view.findViewById(R.id.subject);

        //야이가 포트폴리오
        recyclerView = view.findViewById(R.id.recycler_portfolio);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        myPortofolioAdapter = new MyPotofolioAdapter(getContext(), postList);
        recyclerView.setAdapter(myPortofolioAdapter);

        //프로필에 따로 보이게 하려고 하는 거(프로젝트)
        recyclerView_project = view.findViewById(R.id.recycler_project);
        recyclerView_project.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager_project= new GridLayoutManager(getContext(), 2);
        recyclerView_project.setLayoutManager(linearLayoutManager_project);
        postList_project = new ArrayList<>();
        myPotofolioAdapter_project = new MyPotofolioAdapter(getContext(), postList_project);
        recyclerView_project.setAdapter(myPotofolioAdapter_project);



        recyclerView.setVisibility(View.GONE);
        recyclerView_project.setVisibility(View.VISIBLE);

        userInfo();
        getNPosts();
        getProject();
        myPortfolio();
        mysaves();

        if(profileid.equals(firebaseUser.getUid())) {
            edit_profile.setText("프로필 수정");
        }


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn = edit_profile.getText().toString();
                Log.d("ms", image_profile.getTag().toString());

                if (btn.equals("Edit Profile")) {

                    startActivity(new Intent(getActivity(), EditProfileActivity.class));
                }
            }
        });

        //프로필에서 버튼 전환하는 거
        my_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                recyclerView_project.setVisibility(View.VISIBLE);
            }
        });

        my_portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView_project.setVisibility(View.GONE);
            }
        });

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

                Glide.with(getContext()).load(user.getImageurl()).into(image_profile);
                username.setText(user.getUsername());
                subject.setText(user.getSubject());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
/*
    private void checkFollow() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(profileid).exists()) {
                    edit_profile.setTag("following");
                } else {
                    edit_profile.setTag("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
 */
//포트폴리오 숫자 올리기
    private void getNPosts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    if (post.getPublisher().equals(profileid)){
                        i++;
                    }
                }

                posts.setText(""+i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//프로젝트 숫자 올리기
    private void getProject() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Projects");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Project project = snapshot.getValue(Project.class);
                    if (project.getPublisher().equals(profileid)){
                        i++;
                    }
                }

                projects.setText(""+i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void myPortfolio() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    if (post.getPublisher().equals(profileid)) {
                        postList.add(post);
                    }
                }
                Collections.reverse(postList);
                myPortofolioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void mysaves() {
        mySaves = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Saves")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mySaves.add(snapshot.getKey());
                }

                readSaves();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readSaves() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);

                    for (String id : mySaves) {
                        if (post.getPostid().equals(id)) {
                            postList_project.add(post);
                        }
                    }
                }
                myPotofolioAdapter_project.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
