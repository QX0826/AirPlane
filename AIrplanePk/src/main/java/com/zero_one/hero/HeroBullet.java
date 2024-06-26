package com.zero_one.hero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
        double startY = plane.getTranslateY() - (plane.getImage().getHeight() / 2) + (bulletHeight / 2) + 20;

        //创建子弹的视图
        //加载图片
        ImageView bullet = new ImageView("bullet_hero_type_a_1");



//        //设置子弹大小
//        bullet.setFitWidth(bulletWidth);
//        bullet.setFitHeight(bulletHeight);
//        //设置子弹位置
//        bullet.setTranslateX(startX);
//        bullet.setTranslateY(startY);
//        //将子弹添加到容器中
//        container.getChildren().add(bullet);
//        //设置子弹移动速度
//        double speed = 5;
//        //创建子弹移动动画
//        TranslateTransition transition = new TranslateTransition();






    }

}
