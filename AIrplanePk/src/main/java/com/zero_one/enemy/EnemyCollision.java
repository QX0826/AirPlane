package com.zero_one.enemy;

import com.zero_one.Load.Assets;
import com.zero_one.Player.MP3Player;
import com.zero_one.common.CustomEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @Description: 敌机碰撞
 * @Author: QX
 * @Date: 2024-06-27 13:45
 */

@Slf4j
public class EnemyCollision {
    public static void startCollision(Pane container, ImageView enemy, CustomEvent callback) {
        //定时器
        Timeline collisionTimeline = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            List<ImageView> bulletToRemove = new ArrayList<>();
            //遍历容器所有的子节点
            for (Node node : container.getChildren()) {
                if (node instanceof ImageView && node != enemy) {
                    //node子节点强转为子弹的视图
                    ImageView bullet = (ImageView) node;
                    //获取敌机生命
                    int enemyLife = (int) enemy.getUserData();
                    //检查子弹是否是玩家飞机的子弹
                    if ("herobullet".equals(bullet.getUserData()) && enemyLife > 0) {
                        //判断子弹与敌机是否相交
                        if (enemy.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                            //移除子弹
                            bulletToRemove.add(bullet);
                            //减少生命值
                            decreaseHealth(container, enemy, callback);
                            break;
                        }
                    }
                }
            }
            //将要移除的集合从容器中删除
            container.getChildren().removeAll(bulletToRemove);
        }));
        //设置循环次数
        collisionTimeline.setCycleCount(Timeline.INDEFINITE);
        //开始碰撞检测
        collisionTimeline.play();

    }
    private static void decreaseHealth(Pane container, ImageView enemy, CustomEvent callback) {
        //获取敌机生命值
        int enemyLife = (int) enemy.getUserData();
        //生命值减1
        enemyLife--;
        //重新赋值
        enemy.setUserData(enemyLife);
        //判断如果《=0则敌机爆炸
        if (enemyLife <= 0) {
            //调用爆炸
            EnemyExplosion.explode(container, enemy, callback);
        }
    }
}





























