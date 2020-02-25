package com.example.with_us.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.with_us.Fragment.EventDetailFragment;
import com.example.with_us.Fragment.EventFragment;
import com.example.with_us.Fragment.ProfileFragment;
import com.example.with_us.Model.Board;
import com.example.with_us.Model.Event;
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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context mContext1;
    private List<Event> mEvent;

    private FirebaseUser firebaseUser;

    public EventAdapter(Context context, List<Event> events) {
        mContext1 = context;
        mEvent = events;

    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext1).inflate(R.layout.event_item, parent, false);
        return new EventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Event event = mEvent.get(position);

        Glide
                .with(mContext1)
                .load(event.getEventimage())
                .into(holder.event_image);


        if (event.getEventtitle().equals("")) {
            holder.eventtitle.setVisibility(View.GONE);
        } else {
            holder.eventtitle.setVisibility(View.VISIBLE);
            holder.eventtitle.setText(event.getEventtitle());
        }

        if (event.getEventdate().equals("")) {
            holder.eventdate.setVisibility(View.GONE);
        } else {
            holder.eventdate.setVisibility(View.VISIBLE);
            holder.eventdate.setText(event.getEventdate());
        }

        publisherInfo(holder.image_profile_event, holder.username, event.getPublisher());
        isSaved(event.getPostid(), holder.save);

        //프로필에서 사진 누르면 화면 이동
        holder.image_profile_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext1.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("profiled", event.getPublisher());
                editor.apply();

                ((FragmentActivity) mContext1).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext1.getSharedPreferences("PREFS", MODE_PRIVATE).edit();

                editor.putString("profileid", event.getPublisher());
                editor.apply();

                ((FragmentActivity) mContext1).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });

        holder.event_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext1.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("postid", event.getPostid());
                editor.apply();

                ((FragmentActivity) mContext1).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventDetailFragment()).commit();
            }
        });

        holder.eventtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext1.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("postid", event.getPostid());
                editor.apply();

                ((FragmentActivity) mContext1).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventDetailFragment()).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEvent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile_event, event_image, save;
        public TextView username, eventtitle, publisher, eventdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile_event = itemView.findViewById(R.id.image_profile_event);
            username = itemView.findViewById(R.id.username);
            eventtitle = itemView.findViewById(R.id.eventtitle);
            publisher = itemView.findViewById(R.id.publisher);
            event_image = itemView.findViewById(R.id.event_image);
            eventdate = itemView.findViewById(R.id.eventdate);
        }
    }

    private void publisherInfo(final ImageView image_profile_event, final TextView username, String userid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

//                Log.d(TAG, user);
                Glide.with(mContext1).
                        load(user.getImageurl()).
                        into(image_profile_event);
                username.setText(user.getUsername());
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
