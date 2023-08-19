package com.mankadsoft.endermathx.entities;

public class Kid {

    public Kid() {
    }

    public Kid(String id, String name, Integer points) {
        this.id = id;
        this.name = name;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Kid{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", points=" + points +
                '}';
    }

    private String id;
    private String name;
    private Integer points;


}
