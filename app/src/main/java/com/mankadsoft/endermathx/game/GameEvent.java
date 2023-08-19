package com.mankadsoft.endermathx.game;

import java.util.ArrayList;
import java.util.Objects;

public class GameEvent {

    public static final String START_GAME = "START_GAME";
    public static final String END_GAME = "END_GAME";
    public static final String PRESENT_PROBLEM = "PRESENT_PROBLEM";
    public static final String KEY_STROKE_NUM = "KEY_STROKE_NUM";
    public static final String KEY_STROKE_DEL = "KEY_STROKE_DEL";
    public static final String CORRECT_ANSWER = "CORRECT_ANSWER";
    public static final String INCORRECT_ANSWER = "INCORRECT_ANSWER";
    public static final String EMPTY_ANSWER = "EMPTY_ANSWER";

    private Long timestamp;
    private String eventType;
    private Problem problem;
    private Result result;
    private String key;

    public GameEvent(String eventType, Problem problem, Result result, String key) {
        this.timestamp = new Long(java.util.Calendar.getInstance().getTimeInMillis());
        this.eventType = eventType;
        this.problem = problem;
        this.result = result;
        this.key = key;
    }

    public GameEvent() {
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public Problem getProblem() {
        return problem;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEvent gameEvent = (GameEvent) o;
        return Objects.equals(timestamp, gameEvent.timestamp) &&
                Objects.equals(eventType, gameEvent.eventType) &&
                Objects.equals(problem, gameEvent.problem) &&
                Objects.equals(result, gameEvent.result) &&
                Objects.equals(key, gameEvent.key);
    }

    @Override
    public String toString() {
        return "GameEvent{" +
                "timestamp=" + timestamp +
                ", eventType='" + eventType + '\'' +
                ", problem=" + problem +
                ", result=" + result +
                ", key='" + key + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, eventType, problem, result, key);
    }

    public Result getResult() {
        return result;
    }

}
