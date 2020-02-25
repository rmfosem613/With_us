package com.example.with_us.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.with_us.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DTO.BoardData;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


//게시판 어댑터
public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
    ArrayList<BoardData> mBoardData;
    Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//    private ListItemClickListener listItemClickListener;

    private FirebaseUser firebaseUser;

    public ViewAdapter(ArrayList<BoardData> list, Context context) {
        this.mBoardData = list;
        this.context = context;
//        this.listItemClickListener = listItemClickListener;
    }

    public ViewAdapter(Context context, ArrayList<BoardData> mBoarddata) {
        this.context = context;
        this.mBoardData = mBoarddata;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final BoardData item = mBoardData.get(position);

        holder.title.setText(item.getTitle());
        holder.content.setText(String.valueOf(item.getContent()));

    }

    @Override
    public int getItemCount() {
        return mBoardData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.TitleView);
            this.content = itemView.findViewById(R.id.ContentView);

        }

    }

}

