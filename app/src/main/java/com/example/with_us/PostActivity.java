package com.example.with_us;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

    //선언
    EditText title, content;
    Button post_write;

    FirebaseAuth auth;
    DatabaseReference reference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_post);


        title = findViewById(R.id.title);
        content = findViewById(R.id.content);

        post_write = findViewById(R.id.post_write);

        auth = FirebaseAuth.getInstance();



//        저장 버튼 클릭 시 들어갈 내용
        post_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_title = title.getText().toString();
                String str_content = content.getText().toString();

                if(TextUtils.isEmpty(str_title) || TextUtils.isEmpty(str_content)) {
                    Toast.makeText(PostActivity.this, "모든 항목을 입력해주세요!",Toast.LENGTH_SHORT).show();
                }  else {
                    post_write(str_title, str_content);
                }

//                SimpleDateFormat dataform = new SimpleDateFormat("yyyy-MM-dd");
//                Date date = new Date();
//                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//                WriteData writeData = new WriteData(textTitle().getText().toString(), dateform.format(date).toString, textContent.getText().toString(),1);
//                database.child("message").push().setValue(writeData);
//                textTitle.setText("");
//                textContent.setText("");
            }
        });

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

//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.purposeButton:
//
//                    break;
//            }
//        }
//    };

//    //설정 화면 이동
//    public void more_write(View view) {
//        Intent intent = new Intent(this, SettingActivity.class);
//        startActivity(intent);
//    }



}




