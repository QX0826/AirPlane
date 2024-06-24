package com.zero_one.Scene;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 地图类
 * @Author: QX
 * @Date: 2024-06-23 15:41
 */

@Slf4j

public class ReLoopImageView {
    /**
     * 滚动步长
     */
    private final int SCRDLL_SPEED = 20;

    /**
     * 实际长度大小
     */
   public Double actualWidth;

    /**
     * @param fileImage 地图图片路径
     * @param height     地图高度
     * @param container 地图容器
     * @Description: 地图类构造方法
     */
    public ReLoopImageView(String fileImage, Double height, Pane container) {
        //初始化地图图片
        Image image = new Image(fileImage);
        //初始化地图容器
        ImageView TopImageView = new ImageView(image);
        ImageView BottomImageView = new ImageView(image);

        //初始化地图宽度
        TopImageView.setFitHeight(height);
        BottomImageView.setFitHeight(height);

        //设置宽高比例不变
        TopImageView.setPreserveRatio(true);
        BottomImageView.setPreserveRatio(true);

        //获取视图的高度
        Double ScoreDistance = TopImageView.getFitHeight();

        //移动动画类
        TranslateTransition TopTranslation = new TranslateTransition(Duration.seconds(SCRDLL_SPEED), TopImageView);
        TranslateTransition BottomTranslation = new TranslateTransition(Duration.seconds(SCRDLL_SPEED), BottomImageView);

        //设置移动动画的速率为匀速
        TopTranslation.setInterpolator(Interpolator.LINEAR);
        BottomTranslation.setInterpolator(Interpolator.LINEAR);

        //设置移动的移动坐标
        TopTranslation.setFromY(-ScoreDistance);
        TopTranslation.setToY(0);

        BottomTranslation.setFromY(0);
        BottomTranslation.setToY(ScoreDistance);

        //设置循环次数为无限循环
        TopTranslation.setCycleCount(Animation.INDEFINITE);
        BottomTranslation.setCycleCount(Animation.INDEFINITE);

        //播放动画
        TopTranslation.play();
        BottomTranslation.play();

        //添加到容器
        container.getChildren().addAll(TopImageView, BottomImageView);

        //初始化宽度
        this.actualWidth = calculateWidth(TopImageView);


    }

    /**
     * @param imageView
     * @return
     */

    private Double calculateWidth(ImageView imageView) {
        //获取图像
        Image image = imageView.getImage();

        //获取图像的宽度
        Double originalWidth = image.getWidth();

        //获取图像的宽高比例
        Double fitWidth = imageView.getFitWidth();
        Double fitHeight = imageView.getFitHeight();

        //如果没有设置宽高比例，则返回图像的宽度
        Double actualWidth = fitWidth;
        if(imageView.isPreserveRatio()){
            //实际宽高比
            actualWidth = fitHeight * (originalWidth / image.getHeight());
        }

        return actualWidth;




    }
}

