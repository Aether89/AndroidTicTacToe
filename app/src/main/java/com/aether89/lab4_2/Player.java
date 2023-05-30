package com.aether89.lab4_2;

public class Player {

    private String name = "";
    private String symbol = "";
    private Integer score = 0;

    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        score = 0;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public Integer getScore() {
        return score;
    }

    public void addScore() {
        this.score ++;
    }

    public void addScore(Integer score) {
        this.score += score;
    }

}
