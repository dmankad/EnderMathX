package com.mankadsoft.endermathx;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mankadsoft.endermathx.entities.Kid;
import com.mankadsoft.endermathx.util.AppState;
import com.mankadsoft.endermathx.game.GameSessionManager;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private ProgressBar pgsBar;
    private static int SPLASH_SCREEN_TIME_OUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        pgsBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("EnderMathX", Context.MODE_PRIVATE);

                //Get the current user
                String id = sharedPreferences.getString(AppState.LOCAL_KID_KEY, null);
                Log.d("mankad", "ID Pulled from sharedPreferences: " + id);

                //First time setup
                if (id == null) {
                    launchAuthSetup();
                }

                //Load Local Kid
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("kids/" + id);

                Log.d("mankad", "addListenerForSingleValueEvent: " + mDatabase.toString());
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         Log.d("mankad", "Data Retrieved: " + dataSnapshot.toString());
                         AppState.setCurrentKid(dataSnapshot.getValue(Kid.class));
                         if (AppState.getCurrentKid() != null) {
                             Log.d("mankad", "onDataChange: " + AppState.getCurrentKid().toString());
                             GameSessionManager.InitMasterList();
                             launchMenu();
                         } else {
                             SharedPreferences sharedPreferences = getSharedPreferences("EnderMathX", Context.MODE_PRIVATE);
                             SharedPreferences.Editor editor = sharedPreferences.edit();
                             editor.remove(AppState.LOCAL_KID_KEY);
                             editor.commit();

                             launchAuthSetup();
                         }
                     }

                     @Override
                     public void onCancelled(DatabaseError error) {
                         // Failed to read value
                         Log.d("mankad", "Failed to load current kid: " + error.toString());
                         AppState.setCurrentKid(null);
                         launchAuthSetup();
                     }
                 });
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

    public void launchAuthSetup() {
        Log.d("mankad", "Forwarding to Auth Setup Activity...");
        Intent openAuthSetupView = new Intent(this, AuthSetupActivity.class);
        startActivity(openAuthSetupView);
    }
    public void launchMenu() {
        Log.d("mankad", "Forwarding to Menu Activity...");
        Intent openMenuView = new Intent(this, MenuActivity.class);
        startActivity(openMenuView);
    }


}
