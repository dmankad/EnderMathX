package com.mankadsoft.endermathx.entities;

import java.util.Objects;

public class KidLevel {
    private String levelName;
    private String levelDescription;
    private int score;

    public KidLevel(String levelName, String levelDescription, int score) {
        this.levelName = levelName;
        this.levelDescription = levelDescription;
        this.score = score;
    }

    @Override
    public String toString() {
        return "KidLevel{" +
                "levelName='" + levelName + '\'' +
                ", levelDescription='" + levelDescription + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KidLevel kidLevel = (KidLevel) o;
        return score == kidLevel.score &&
                Objects.equals(levelName, kidLevel.levelName) &&
                Objects.equals(levelDescription, kidLevel.levelDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelName, levelDescription, score);
    }

    public String getLevelName() {
        return levelName;
    }

    public String getLevelDescription() {
        return levelDescription;
    }

    public int getScore() {
        return score;
    }
}
