package com.example.with_us.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.with_us.Fragment.PostDetailFragment;
import com.example.with_us.Fragment.ProfileFragment;
import com.example.with_us.Model.Post;
import com.example.with_us.Model.Project;
import com.example.with_us.Model.User;
import com.example.with_us.R;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private Context mContext;
    private List<Project> mProject;

    private FirebaseUser firebaseUser;

    public ProjectAdapter(Context context, List<Project> projects) {
        mContext = context;
        mProject = projects;
    }

    @NonNull
    @Override
    public ProjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.project_item, parent, false);
        return new ProjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Project project = mProject.get(position);
//        Log.d("ms", project.getProjectimage().toString());

        //에러
//        Glide.with(mContext).load(project.getPostimage()).into(holder.post_image);

        if (project.getContent_write().equals("")) {
            holder.content_write.setVisibility(View.GONE);
        } else {
            holder.content_write.setVisibility(View.VISIBLE);
            holder.content_write.setText(project.getContent_write());
        }

        if (project.getData_write().equals("")) {
            holder.date.setVisibility(View.GONE);
        } else {
            holder.date.setVisibility(View.VISIBLE);
            holder.date.setText(project.getData_write());
        }

        if (project.getPurpose_write().equals("")) {
            holder.purpose.setVisibility(View.GONE);
        } else {
            holder.purpose.setVisibility(View.VISIBLE);
            holder.purpose.setText(project.getPurpose_write());
        }

        publisherInfo(holder.image_profile, holder.publisher, holder.subject, project.getPublisher());
        isSaved(project.getPostid(), holder.save);

        //프로필에서 사진 누르면 화면 이동
        holder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("profiled", project.getPublisher());
                editor.apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });


        holder.publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("profileid", project.getPublisher());
                editor.apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });

        holder.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("profileid", project.getSubject());
                editor.apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProject.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile, save;
        public TextView purpose, date, title, content_write, publisher, subject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            purpose = itemView.findViewById(R.id.purpose_write);
            image_profile = itemView.findViewById(R.id.image_profile);
            publisher = itemView.findViewById(R.id.publisher);
            date = itemView.findViewById(R.id.data_write);
            title = itemView.findViewById(R.id.title_write);
            content_write = itemView.findViewById(R.id.content_write);


        }
    }

    private void publisherInfo(final ImageView image_profile, final TextView subject, final TextView publisher, String userid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageurl()).into(image_profile);
                subject.setText(user.getUsername());
                publisher.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void isSaved(final String postid, final ImageView imageView) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saves")
                .child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(postid).exists()) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}