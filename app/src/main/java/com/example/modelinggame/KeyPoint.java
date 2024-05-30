package com.example.modelinggame;

import android.graphics.PointF;

public class KeyPoint {
    //private BodyPart bodyPart;
    private int bodyPart;
    private PointF coordinate;
    private float score;

    public KeyPoint(int bodyPart, PointF coordinate, float score) {
        this.bodyPart = bodyPart;
        this.coordinate = coordinate;
        this.score = score;
    }

    public int getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(int bodyPart) {
        this.bodyPart = bodyPart;
    }

    public PointF getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(PointF coordinate) {
        this.coordinate = coordinate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "KeyPoint{" +
                "bodyPart=" + bodyPart +
                ", coordinate=" + coordinate +
                ", score=" + score +
                '}';
    }
}
