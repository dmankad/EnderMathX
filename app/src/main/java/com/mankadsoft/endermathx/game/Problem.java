package com.mankadsoft.endermathx.game;

import java.util.Objects;

public class Problem {

    private String id;
    private String valA;
    private String valB;
    private String operator;

    public Problem(String id, String valA, String valB, String operator) {
        this.id = id;
        this.valA = valA;
        this.valB = valB;
        this.operator = operator;
    }

    public Problem() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValA(String valA) {
        this.valA = valA;
    }

    public void setValB(String valB) {
        this.valB = valB;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getId() {
        return id;
    }

    public String getValA() {
        return valA;
    }

    public String getValB() {
        return valB;
    }

    public String getOperator() {
        return operator;
    }

    public String toFriendlyString() {
        return valA + " " + operator + " " + this.valB;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return id.equals(problem.id) &&
                Objects.equals(valA, problem.valA) &&
                Objects.equals(valB, problem.valB) &&
                Objects.equals(operator, problem.operator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valA, valB, operator);
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id='" + id + '\'' +
                ", valA='" + valA + '\'' +
                ", operator='" + operator + '\'' +
                ", valB='" + valB + '\'' +
                '}';
    }
}
