package com.mankadsoft.endermathx.game;

import android.util.Log;

import com.mankadsoft.endermathx.util.AppState;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class GameSession {

    private String gameSessionId;
    private String kidId;
    private String gameName;
    private ArrayList<GameEvent> events;
    private int finalScore;
    private long initiatedTime;

    public long getInitiatedTime() {
        return initiatedTime;
    }

    public GameSession() {
    }

    public void setKidId(String kidId) {
        this.kidId = kidId;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setEvents(ArrayList<GameEvent> events) {
        this.events = events;
    }

    public void setInitiatedTime(long initiatedTime) {
        this.initiatedTime = initiatedTime;
    }

    public void setGameSessionId(String gameSessionId) {
        this.gameSessionId = gameSessionId;
    }

    public GameSession(String gameName) {
        this.gameSessionId = java.util.UUID.randomUUID().toString();
        this.kidId = AppState.getCurrentKid().getId();
        this.gameName = gameName;
        this.events = new ArrayList<GameEvent>();
        this.finalScore = 0;
        this.initiatedTime = Calendar.getInstance().getTimeInMillis();
    }

    public String getGameSessionId() {
        return gameSessionId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getKidId() {
        return kidId;
    }

    public ArrayList<GameEvent> getEvents() {
        return events;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void logEvent(String eventType) {
        GameEvent event = new GameEvent(eventType, null, null, null);
        events.add(event);
        Log.d("mankad", "Logging Event: " + event);
    }

    public void logKeyStroke(String eventType, String key) {
        GameEvent event = new GameEvent(eventType, null, null, key);
        events.add(event);
        Log.d("mankad", "Logging Event: " + event);
    }

    public void logEvent(String eventType, Problem problem, Result result) {
        GameEvent event = new GameEvent(eventType, problem, result, null);
        events.add(event);
        Log.d("mankad", "Logging Event: " + event);
    }
    public void logEvent(String eventType, Problem problem) {
        GameEvent event = new GameEvent(eventType, problem, null, null);
        events.add(event);
        Log.d("mankad", "Logging Event: " + event);
    }


    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSession that = (GameSession) o;
        return Objects.equals(gameSessionId, that.gameSessionId) &&
                Objects.equals(kidId, that.kidId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameSessionId, kidId);
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "gameSessionId='" + gameSessionId + '\'' +
                ", kidId='" + kidId + '\'' +
                ", gameName=" + gameName +
                ", events=" + events +
                ", finalScore=" + finalScore +
                '}';
    }
}
