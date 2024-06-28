package com.zero_one.hero;

import com.zero_one.Load.Assets;
import com.zero_one.Player.MP3Player;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 玩家飞机发射子弹
 * @Author: QX
 * @Date: 2024-06-26 14:26
 */

@Slf4j

public class HeroBullet {
    public static void fireBullet(Pane container, ImageView plane) {
        //子弹大小
        double bulletWidth = 20;
        double bulletHeight = 30;
        //子弹位置
        double startX = plane.getTranslateX();
        //                    飞机垂直坐标                  飞机图像的高度                飞机图像的一半
        double startY = plane.getTranslateY() - (plane.getImage().getHeight() / 2) + (bulletHeight / 2) - 10;
        //创建子弹的视图
        //加载图片
        ImageView bullet = new ImageView(Assets.IMAGES.get("bullet_hero_type_a_1"));
        //设置用户数据
        bullet.setUserData("herobullet");//用于碰撞检测
        //设置子弹大小
        bullet.setFitWidth(bulletWidth);
        bullet.setFitHeight(bulletHeight);
        //设置子弹位置
        bullet.setTranslateX(startX);
        bullet.setTranslateY(startY);
        //将子弹添加到容器中
        container.getChildren().add(bullet);
        //子弹发射的声音
        MP3Player.playOnce(Assets.AUDIOS.get("effect_bullet_hero_type_a_1"));
        //创建子弹移动动画
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), bullet);
        transition.setToY(-9999);
        transition.play();
        transition.setOnFinished(e -> {
            //子弹移出屏幕后，从容器中删除
            container.getChildren().remove(bullet);
        });
    }
}























