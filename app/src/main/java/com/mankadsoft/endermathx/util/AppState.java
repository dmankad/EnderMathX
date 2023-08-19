package com.mankadsoft.endermathx.util;

import android.util.Log;

import com.mankadsoft.endermathx.entities.Kid;

import com.google.firebase.database.DatabaseReference;
import com.mankadsoft.endermathx.game.GameEvent;
import com.mankadsoft.endermathx.game.GameSession;
import com.mankadsoft.endermathx.game.MathGame;

public class AppState {

    public static final String LOCAL_KID_KEY = "localkid";
    private static Kid currentKid = null;
    private static GameSession currentGame = null;

    public static GameSession getCurrentGame() {
        return currentGame;
    }

    public static void initGame(String gameName) {
        AppState.currentGame = new GameSession(gameName);
        AppState.currentGame.logEvent(GameEvent.START_GAME);
        Log.d("mankad", "Starting Game: " + gameName);
    }

    public static void completeGame(int finalScore) {
        AppState.currentGame.setFinalScore(finalScore);
        AppState.currentGame.logEvent(GameEvent.END_GAME);
        Log.d("mankad", "Ending Game with final score: " + finalScore);
    }

    public static Kid getCurrentKid() {
        return currentKid;
    }
    public static void setCurrentKid(Kid currentKid) {
        AppState.currentKid = currentKid;
    }

}
