package com.example.modelinggame;

import android.graphics.RectF;
import java.util.List;

public class Person {
    private int id;
    private List<KeyPoint> keyPoints;
//    private RectF boundingBox; for movenet multipose
    private float score;

    public Person(int id, List<KeyPoint> keyPoints, float score) { //RectF boundingBox
        this.id = id;
        this.keyPoints = keyPoints;
        //this.boundingBox = boundingBox;
        this.score = score;
    }

    public Person(List<KeyPoint> keyPoints, float score) {
        this(-1, keyPoints, score); //, null
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<KeyPoint> getKeyPoints() {
        return keyPoints;
    }

    public void setKeyPoints(List<KeyPoint> keyPoints) {
        this.keyPoints = keyPoints;
    }


    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", keyPoints=" + keyPoints +
                ", score=" + score +
                '}';
    }
}
