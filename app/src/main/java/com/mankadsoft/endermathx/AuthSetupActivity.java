package com.mankadsoft.endermathx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mankadsoft.endermathx.entities.Kid;
import com.mankadsoft.endermathx.util.AppState;
import com.mankadsoft.endermathx.game.GameSessionManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class AuthSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_setup);

        final EditText tb = ((EditText)findViewById(R.id.kidName));
        tb.requestFocus();
        tb.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(tb, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 150);


        final Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String kidName = ((EditText)findViewById(R.id.kidName)).getText().toString();

                if(TextUtils.isEmpty(kidName)) {
                    ((EditText)findViewById(R.id.kidName)).setError("You must enter your name!");
                }
                else {
                    SharedPreferences sharedPreferences = getSharedPreferences("EnderMathX", Context.MODE_PRIVATE);
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("kids");

                    Log.d("mankad", "Getting Key from " + mDatabase.toString());

                    String id = mDatabase.push().getKey();
                    Log.d("mankad", "Key " + id);
                    mDatabase = FirebaseDatabase.getInstance().getReference("kids/" + id);
                    AppState.setCurrentKid(new Kid(id, kidName, new Integer(0)));
                    mDatabase.setValue(AppState.getCurrentKid());

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(AppState.LOCAL_KID_KEY, AppState.getCurrentKid().getId());
                    editor.commit();
                    GameSessionManager.InitMasterList();
                    launchMenu();
                }
            }
        });
    }

    public void launchMenu() {
        Log.d("mankad", "Forwarding to Menu activity...");
        Intent openMenuView = new Intent(this, MenuActivity.class);
        startActivity(openMenuView);
    }
}
