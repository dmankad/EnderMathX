package com.mankadsoft.endermathx;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.mankadsoft.endermathx.entities.KidLevel;
import com.mankadsoft.endermathx.game.ComplexAdditionGame;
import com.mankadsoft.endermathx.game.SimpleMultiplication;
import com.mankadsoft.endermathx.game.SimpleAdditionGame;
import com.mankadsoft.endermathx.game.SimpleSubtractionGame;
import com.mankadsoft.endermathx.util.AppState;
import com.mankadsoft.endermathx.util.KidLevelUtil;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GamesListActivity extends AppCompatActivity {

    private MediaPlayer selectSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        selectSound = MediaPlayer.create(GamesListActivity.this, R.raw.sword);

        ((TextView)findViewById(R.id.textViewTitle)).setText("Welcome back, Ninja " + AppState.getCurrentKid().getName());

        KidLevel thisLevel = KidLevelUtil.GetLevelByScore(AppState.getCurrentKid());

        if(thisLevel==null) {
            throw new RuntimeException("Cannot determine kid level.");
        }

        KidLevel nextLevel = KidLevelUtil.GetNextLevel(thisLevel);
        int remainingPoints = nextLevel.getScore() - AppState.getCurrentKid().getPoints();
        ((TextView)findViewById(R.id.ninjaLevel)).setText(thisLevel.getLevelName());
        ((TextView)findViewById(R.id.ninjaLevelDesc)).setText(thisLevel.getLevelDescription());
        ((TextView)findViewById(R.id.nextLevel)).setText(remainingPoints + " pts.");

        final Button buttonSimpleAddition = findViewById(R.id.gameSimpleAddition);
        buttonSimpleAddition.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppState.initGame(SimpleAdditionGame.GAME_NAME);
                launchGameRunner();
            }
        });

        final Button buttonComplexAddition = findViewById(R.id.gameComplexAddition);
        buttonComplexAddition.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppState.initGame(ComplexAdditionGame.GAME_NAME);
                launchGameRunner();
            }
        });

        final Button buttonSimpleSubtraction = findViewById(R.id.gameSimpleSubtraction);
        buttonSimpleSubtraction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppState.initGame(SimpleSubtractionGame.GAME_NAME);
                launchGameRunner();
            }
        });

        final Button buttonTimesTwo = findViewById(R.id.gameSimpleMultiplication);
        buttonTimesTwo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppState.initGame(SimpleMultiplication.GAME_NAME);
                launchGameRunner();
            }
        });

    }

    public void launchGameRunner() {
        Log.d("mankad", "Forwarding to Game Runner...");
        selectSound.start();
        Intent openGameRunner = new Intent(this, GameRunner.class);
        startActivity(openGameRunner);
    }
}
