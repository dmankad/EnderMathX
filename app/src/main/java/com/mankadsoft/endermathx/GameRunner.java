package com.mankadsoft.endermathx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mankadsoft.endermathx.game.GameEvent;
import com.mankadsoft.endermathx.game.MathGame;
import com.mankadsoft.endermathx.game.MathGameFactory;
import com.mankadsoft.endermathx.game.Problem;
import com.mankadsoft.endermathx.game.Result;
import com.mankadsoft.endermathx.util.AppState;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameRunner extends AppCompatActivity {

    private static MathGame currentGame;
    private static Problem currentProblem;
    private static int points;
    private static CountDownTimer timer;
    private static int timeRemaining = 0;

    private MediaPlayer right;
    private MediaPlayer wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_runner);

        points = 0;
        currentGame = MathGameFactory.GenerateGame(AppState.getCurrentGame().getGameName());
        currentGame.init();
        currentProblem = currentGame.generateProblem();

        right = MediaPlayer.create(GameRunner.this, R.raw.punch);
        wrong = MediaPlayer.create(GameRunner.this, R.raw.whiff2);


        setupTimer(currentGame.getGameDuration().intValue()*1000);
        paintProblem(currentProblem);

        final Button buttonSimpleAddition = findViewById(R.id.answerButton);
        buttonSimpleAddition.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String answer = ((EditText)findViewById(R.id.answer)).getText().toString();
                Result result = currentGame.checkAnswer(currentProblem, answer);
                if(result.getResult() == Result.CORRECT) {
                    right.start();
                    correctAnswer(result);
                }
                else if(result.getResult() == Result.INCORRECT) {
                    wrong.start();
                    incorrectAnswer(result);
                }
                else if(result.getResult() == Result.EMPTY) {
                    blankAnswer(result);
                }
            }
        });
    }

    private void correctAnswer(Result result) {
        calcPoints(result);
        this.currentProblem = this.currentGame.generateProblem();
        AppState.getCurrentGame().logEvent(GameEvent.CORRECT_ANSWER, result.getProblem(), result);
        paintProblem(currentProblem);
        ((EditText)findViewById(R.id.answer)).setText(null);
    }

    private void incorrectAnswer(Result result) {
        calcPoints(result);
        AppState.getCurrentGame().logEvent(GameEvent.INCORRECT_ANSWER, result.getProblem(), result);
        ((EditText)findViewById(R.id.answer)).setText(null);
    }

    private void blankAnswer(Result result) {
        AppState.getCurrentGame().logEvent(GameEvent.EMPTY_ANSWER, result.getProblem(), result);
    }
    private void paintProblem(Problem problem) {
        ((TextView)findViewById(R.id.probValA)).setText(problem.getValA());
        ((TextView)findViewById(R.id.probValB)).setText(problem.getValB());
        ((TextView)findViewById(R.id.probOperator)).setText(problem.getOperator());
        AppState.getCurrentGame().logEvent(GameEvent.PRESENT_PROBLEM, problem);
    }
    private void calcPoints(Result result) {
        this.points = this.points + result.getPoints().intValue();
        ((TextView)findViewById(R.id.score)).setText("Score: " + this.points);
    }

    public void setupTimer(int countDownTime) {
        timeRemaining = countDownTime;

        if(timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(countDownTime, 100) {

            public void onTick(long millisUntilFinished) {
                timeRemaining = (int)millisUntilFinished;
                TextView timeRemainingText = (TextView) findViewById(R.id.timeRemaining);
                ProgressBar pbar = (ProgressBar) findViewById(R.id.progressBar);
                pbar.setMax(120);
                int progress = Math.round(millisUntilFinished/1000);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    pbar.setProgress(progress, true);
                    if(progress>=119) {
                        pbar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                    }
                    if(progress==60) {
                        pbar.getProgressDrawable().setColorFilter(Color.rgb(255, 165, 0), PorterDuff.Mode.SRC_IN);
                    }
                    if(progress==15) {
                        pbar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    }

                }
                timeRemainingText.setText("Time remaining: " + millisUntilFinished / 1000);

            }

            public void onFinish() {
                timerDone();

            }
        }.start();
    }

    private void timerDone() {
        AppState.completeGame(points);
        Intent openGameResults = new Intent(this, GameResultActivity.class);
        Log.d("mankad", "Forwarding to Game Results...");
        startActivity(openGameResults);
    }
}

