package com.example.bda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView type, name, email, idNumber, phoneNumber, bloodGroup;
    private CircleImageView profileImage;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        type = findViewById(R.id.type);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        idNumber = findViewById(R.id.idNumber);
        phoneNumber = findViewById(R.id.phoneNumber);
        bloodGroup = findViewById(R.id.bloodGroup);
        profileImage = findViewById(R.id.profileImage);
        backButton = findViewById(R.id.backButton);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    type.setText(snapshot.child("type").getValue().toString());
                    name.setText("Name: " + snapshot.child("name").getValue().toString());
                    idNumber.setText("ID number: " + snapshot.child("idnumber").getValue().toString());
                    phoneNumber.setText("Phone Number: "+ snapshot.child("phonenumber").getValue().toString());
                    bloodGroup.setText("Blood Group: " + snapshot.child("bloodgroup").getValue().toString());
                    email.setText("Email: " + snapshot.child("email").getValue().toString());
                    if (snapshot.hasChild("profilepictureurl")){
                        String imageUrl = snapshot.child("profilepictureurl").getValue().toString();
                        Glide.with(getApplicationContext()).load(imageUrl).into(profileImage);
                    }else {
                        profileImage.setImageResource(R.drawable.profile);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}