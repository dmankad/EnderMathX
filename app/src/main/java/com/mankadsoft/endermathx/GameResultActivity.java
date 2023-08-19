package com.mankadsoft.endermathx;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mankadsoft.endermathx.entities.Kid;
import com.mankadsoft.endermathx.game.Problem;
import com.mankadsoft.endermathx.util.AppState;
import com.mankadsoft.endermathx.game.GameSessionManager;
import com.mankadsoft.endermathx.util.InsightCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class GameResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        Log.d("mankad", "GameSession: " + AppState.getCurrentGame());

        GameSessionManager.RegisterCompletedGameSession(AppState.getCurrentGame());

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("kids/"+AppState.getCurrentKid().getId());
        Kid currentKid = AppState.getCurrentKid();
        currentKid.setPoints(currentKid.getPoints().intValue() + AppState.getCurrentGame().getFinalScore());
        AppState.setCurrentKid(currentKid);
        mDatabase.setValue(AppState.getCurrentKid());


        InsightCalculator insights = new InsightCalculator(AppState.getCurrentGame());
        String averageProblemTime = insights.getAverageProblemTime().toString();
        List<Problem> hardestProblems = insights.getHardestProblems(3);
        String totalProblems = insights.getTotalProblemsPresented().toString();
        String totalSolved = insights.getTotalProblemsSolved().toString();

        Log.d("mankad", "Points Earned: " + AppState.getCurrentGame().getFinalScore());
        ((TextView)findViewById(R.id.pointsEarnedValue)).setText(Integer.toString(AppState.getCurrentGame().getFinalScore()));

        Log.d("mankad", "Average Problem Time: " + averageProblemTime);
        ((TextView)findViewById(R.id.averageTimeValue)).setText(averageProblemTime);

        Log.d("mankad", "Hardest Problems: " + hardestProblems);

        String hardestString="";
        for(Problem p : (Iterable<Problem>) hardestProblems) {
            hardestString = hardestString + p.toFriendlyString() + System.getProperty("line.separator");
        }
        ((TextView)findViewById(R.id.hardestProblemsValue)).setText(hardestString);

        Log.d("mankad", "totalSolved: " + totalSolved);
        ((TextView)findViewById(R.id.problemsSolvedValue)).setText(totalSolved);

        final Button buttonBackToList = findViewById(R.id.backToList);
        buttonBackToList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchMenu();
            }
        });
    }
    public void launchMenu() {
        Log.d("mankad", "Forwarding to Menu activity...");
        Intent openMenuView = new Intent(this, MenuActivity.class);
        startActivity(openMenuView);
    }

}
