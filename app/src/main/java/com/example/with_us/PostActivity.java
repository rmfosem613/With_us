package com.example.with_us;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class PostActivity extends AppCompatActivity {

    private static final String TAG = "PostActivity";
    private static final String REQUIRED = "Required";

    //선언
    EditText title, content;
    ImageView post_write;

    FirebaseAuth auth;
    DatabaseReference reference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_post);

        reference = FirebaseDatabase.getInstance().getReference();

        title = findViewById(R.id.title_write);
        content = findViewById(R.id.content_write);
        post_write = findViewById(R.id.post_write);
        auth = FirebaseAuth.getInstance();
    }


    //    DB보내기
    private void post_write(final String str_title, String str_content) {
        auth.createUserWithEmailAndPassword(str_title, str_content)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Board").child(userid);

                            //DB들어갈 내용 put으로 넣기
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("content", content);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(PostActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                });
    }


}




