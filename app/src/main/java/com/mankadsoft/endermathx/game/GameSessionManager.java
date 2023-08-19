package com.mankadsoft.endermathx.game;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mankadsoft.endermathx.util.AppState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class GameSessionManager {

    private static List<GameSession> masterSessionList;

    public static void InitMasterList() {
        masterSessionList = new ArrayList<GameSession>();

        Log.d("mankad", "Lazy Loading Master List.");
        //Get List from Firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query mQuery = mDatabase.child("gameStates").orderByChild("kidId").equalTo(AppState.getCurrentKid().getId());

        Log.d("mankad", "Query: " + mQuery);

        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("mankad", "Master ListData Retrieved: " + dataSnapshot.toString());
                Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();

                masterSessionList = new ArrayList<GameSession>();

                while(i.hasNext()) {
                    DataSnapshot m = i.next();
                    GameSession g = m.getValue(GameSession.class);
                    masterSessionList.add(g);
                }
                Log.d("mankad", "Master Session List Populated: " + masterSessionList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.d("mankad", "Failed to load Master ListData: " + error.toString());

            }
        });


    }

    public static List GetSessionsFilteredByRecent(String gameName, int count) {
        ArrayList filteredByGame = GetSessionsFilteredByGameName(gameName);
        Collections.sort(filteredByGame, new GameSessionDateComp());
        Collections.reverse(filteredByGame);
        if(filteredByGame.size()<count) {
            return filteredByGame;
        }
        return filteredByGame.subList(0, count);
    }

    // Static Filter Methods
    public static ArrayList<GameSession> GetSessionsFilteredByGameName(String gameName) {
        ArrayList<GameSession> filteredSessions = new ArrayList<GameSession>();
        for (GameSession g : masterSessionList) {
            if (g.getGameName().equals(gameName)) {
                filteredSessions.add(g);
            }
        }
        return filteredSessions;
    }


    public static void RegisterCompletedGameSession(GameSession game) {
        Log.d("mankad", "Adding session to master list: " + game);
        masterSessionList.add(game);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("gameStates/"+game.getGameSessionId());
        mDatabase.setValue(game);
    }

}

class GameSessionDateComp implements Comparator<GameSession> {
    @Override
    public int compare(GameSession o1, GameSession o2) {
        return Long.compare(o1.getInitiatedTime(), o2.getInitiatedTime());
    }
}