package com.example.with_us;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class PostPortfolioActivity extends AppCompatActivity {

    Uri imageUri;
    String myUri = "";
    StorageTask uploadTask;
    StorageReference storageReference;

    ImageView image_added;
    TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_portfolio);
    }
}
