package com.example.modelinggame;

import java.util.ArrayList;
import java.util.List;

public class GameItem {
    private int id;
    private String category;
    private String heading;
    private String level;
    private String time;
    private String imageURL;
    List<KeyPoint> baseImgResults = new ArrayList<>();

    public GameItem(int id, String category, String heading, String level, String time, String imageURL) {
        this.id = id;
        this.category = category;
        this.heading = heading;
        this.level = level;
        this.time = time;
        this.imageURL = imageURL;
    }
    @Override
    public String toString() {
        return "GameItem{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", heading='" + heading + '\'' +
                ", level='" + level + '\'' +
                ", time='" + time + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
    public void setBaseImgResults(List<KeyPoint> list){
        this.baseImgResults = list;

    }
    public List<KeyPoint> getBaseImgResults(){
        return this.baseImgResults;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getHeading() {
        return heading;
    }
    public void setHeading(String heading) {
        this.heading = heading;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
