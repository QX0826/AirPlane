package com.zero_one.enemy;

import com.zero_one.Load.Assets;
import com.zero_one.common.CustomEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @Description: 敌机生成
 * @Author: QX
 * @Date: 2024-06-27 9:34
 */

@Slf4j

public class EnemySpawn {
    //放置敌机的容器
    private Pane container;

    //场景的宽度
    private double gameWidth;

    //回调事件
    private CustomEvent callback;

    //敌机生成随机数量
    private Random random = new Random();

    //判断敌机的生成位置
    private boolean isLeft = false;

    //时间轴
    private Timeline timeline;

    public EnemySpawn(Pane container, double gameWidth, CustomEvent callback) {
        this.container = container;
        this.gameWidth = gameWidth;
        this.callback = callback;
    }

    //敌机生成
    public void StartSpawn() {
        //时间轴
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> creatEnemyPlane()));
        timeline.setCycleCount(Timeline.INDEFINITE); //无限循环
        timeline.play();


    }

    private void creatEnemyPlane() {
        //随机生成敌机的位置
        double startX = 0;
        if (isLeft) {
            isLeft = false;
            startX = random.nextDouble() * (container.getWidth() / 2 - gameWidth);
        } else {
            isLeft = true;
            startX = random.nextDouble() * (-container.getWidth() / 2 + gameWidth);
        }

        //创建敌机
        Image image = new Image(Assets.IMAGES.get("plane_enemy_" + (random.nextInt(4) + 1)));


        ImageView imageView = new ImageView(image);
        imageView.setTranslateX(startX);
        imageView.setTranslateY(-container.getHeight());

        //生命值
        imageView.setUserData(random.nextInt(6) + 1);

        //添加到容器
        container.getChildren().add(imageView);

        //创建敌机移动动画
        TranslateTransition translate = new TranslateTransition(Duration.seconds(random.nextInt(10) + 10), imageView);
        translate.setToY(container.getHeight() + 100);
        translate.setOnFinished(event -> {
            container.getChildren().remove(imageView);
        });
        translate.play();

        //碰撞检测
        EnemyCollision.startCollision(container, imageView, callback);

    }

    /**
     * 停止生成敌机
     */
    public void stopSpawn() {
        if(timeline != null){
            timeline.stop();
            log.info("停止生成敌机");
        }
    }
}
