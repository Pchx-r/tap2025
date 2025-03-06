package org.example.tap2025.modelos;

import javafx.scene.image.ImageView;

public class Component {
    private String source;
    private int actualPositionX;
    private int actualPositionY;
    private int goalPositionX;
    private int goalPositionY;

    public Component(String source,int actualPositionX, int actualPositionY, int goalPositionX,int goalPositionY) {

        this.goalPositionX = goalPositionX;
        this.goalPositionY = goalPositionY;
        this.actualPositionX = actualPositionX;
        this.actualPositionY = actualPositionY;
        this.source = source;
    }
    public Component(){}

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getActualPositionX() {
        return actualPositionX;
    }

    public void setActualPositionX(int actualPositionX) {
        this.actualPositionX = actualPositionX;
    }

    public int getActualPositionY() {
        return actualPositionY;
    }

    public void setActualPositionY(int actualPositionY) {
        this.actualPositionY = actualPositionY;
    }

    public int getGoalPositionX() {
        return goalPositionX;
    }

    public void setGoalPositionX(int goalPositionX) {
        this.goalPositionX = goalPositionX;
    }

    public int getGoalPositionY() {
        return goalPositionY;
    }

    public void setGoalPositionY(int goalPositionY) {
        this.goalPositionY = goalPositionY;
    }
}
