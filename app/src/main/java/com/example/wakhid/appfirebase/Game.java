package com.example.wakhid.appfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Game extends AppCompatActivity {
    DatabaseReference reff;
    FirebaseDatabase database;

    TextView vhighScore,vnama;

    String username;
    String nama;
    int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove notif
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        //init component
        vnama = (TextView) findViewById(R.id.vNama);
        vhighScore = (TextView) findViewById(R.id.vhighScore);

        //input username from intent
        username = getIntent().getStringExtra("username");

        //firebase
        database = FirebaseDatabase.getInstance();
        reff = database.getReference("user");
        getDetail(username);

    }

    private void getDetail(final String u) {
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(u).exists()){
                    Log.d("fb","child username ada");
                    if(!u.isEmpty()){
                        Log.d("fb","child username tidak kosong");
                        Value login = dataSnapshot.child(u).getValue(Value.class);
                        nama = login.getNama();
                        highscore = login.getHighScore();

                        //set text
                        vnama.setText(nama);
                        vhighScore.setText(String.valueOf(highscore));
                        Log.d("fb","set textview");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
