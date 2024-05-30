package com.example.modelinggame;

import android.app.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyApplication extends Application {
    private static List<GameItem> gameItemList = new ArrayList<>();

    public MyApplication() {
        fillGameItemList();
    }

    public static List<GameItem> getGameItemList() {
        return gameItemList;
    }

    private void fillGameItemList() {
        GameItem g0 = new GameItem(0,"Standing pose", "#1 Hands on Hips", "beginner", "No limits",
                "https://content1.getnarrativeapp.com/static/8542a067-76df-49d2-b3c6-b347d6da1b85/Sam_0205.jpg?w=750"  );
        GameItem g1 = new GameItem(1,"Standing pose", "#2 Look from the back", "medium", "15seconds",
                "https://celclipmaterialprod.s3-ap-northeast-1.amazonaws.com/62/89/1718962/thumbnail?1532772854"  );
        GameItem g2 = new GameItem(2,"Standing pose", "#3 Happy Pose", "hard", "15seconds",
                "https://nycphoto.com/wp-content/uploads/2021/11/e10d3ab3-smiling-standing-facing-camera.jpg");

        gameItemList.addAll(Arrays.asList(new GameItem[]{g0,g1, g2}));
    }
}
