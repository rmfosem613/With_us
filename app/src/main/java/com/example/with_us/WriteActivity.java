package com.example.with_us;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.with_us.Model.Board;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriteActivity extends AppCompatActivity {
    ImageView saveButton;
    EditText writeTitle;
    EditText writeContent;
    EditText writePurpose;
    EditText writeDate;

    DatabaseReference mDatabaseReference;
    FirebaseDatabase mFirebaseDatabase;
    ChildEventListener mChildEventListener;
    FirebaseAuth auth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        auth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Board");

        saveButton = (ImageView) findViewById(R.id.saveButton);
        writePurpose = (EditText) findViewById(R.id.writePurpose);
        writeDate = (EditText) findViewById(R.id.writeDate);
        writeTitle = (EditText) findViewById(R.id.writeTitle);
        writeContent = (EditText) findViewById(R.id.writeContent);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_purpose = writePurpose.getText().toString();
                String str_date = writeDate.getText().toString();
                String str_title = writeTitle.getText().toString();
                String str_content = writeContent.getText().toString();

                if (TextUtils.isEmpty(str_title) || TextUtils.isEmpty(str_content)) {
                    Toast.makeText(WriteActivity.this, "모든 항목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    String postid = mDatabaseReference.push().getKey();
                    Board boardData = new Board(writePurpose.getText().toString(),writeDate.getText().toString(),writeTitle.getText().toString(), writeContent.getText().toString(), postid);
                    database.child("Board").push().setValue(boardData);

                    Intent intent = new Intent(WriteActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}

