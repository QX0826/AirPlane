package com.zero_one.hero;

import com.zero_one.Load.Assets;
import com.zero_one.Player.MP3Player;

import com.zero_one.Scene.SettingsScene;
import com.zero_one.common.CustomEvent;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 创建玩家飞机
 * @Author: QX
 * @Date: 2024-06-24 10:29
 */

@Slf4j
@Getter
@Setter

public class HeroPlane {
    //飞机的速度
    public static double speed = 5;
    //飞机图片
    private Image image;
    //飞机的试图
    private ImageView imageView;
    //飞机的容器
    private Pane container;
    //飞机的场景
    private Scene scene;
    //飞机的能移动的最大宽度
    private double maxWidth;
    //回调事件
    private CustomEvent callback;
    //玩家飞机监听
    private HeroControl heroControl;

    /**
     * 构造函数
     *
     * @param container 容器
     * @param scene     场景
     * @param maxWidth  最大宽度
     * @param callback  回调事件
     */

    public HeroPlane(Pane container, Scene scene, double maxWidth, CustomEvent callback) {
        this.image = new Image(Assets.IMAGES.get("plane_hero_"+ SettingsScene.planeID));
        this.imageView = new ImageView(image);
        this.container = container;
        this.scene = scene;
        this.maxWidth = maxWidth;
        this.callback = callback;
        this.heroControl = new HeroControl(this, scene);
    }

    /**
     * 玩家飞机飞入动画
     */
    public void in() {
        //音频
        MP3Player.playOnce(Assets.AUDIOS.get("effect_plane_flying_overhead"));
        //设置初始飞机的位置
        imageView.setTranslateY(scene.getHeight());
        //把飞机放入容器
        container.getChildren().add(imageView);
        //飞机飞入动画
        TranslateTransition translateTransitionin = new TranslateTransition(Duration.seconds(2), imageView);
        translateTransitionin.setFromY(scene.getHeight());
        translateTransitionin.setToY(0);
        translateTransitionin.setInterpolator(Interpolator.EASE_OUT);//减速
        //飞下画面
        TranslateTransition translateTransitiondown = new TranslateTransition(Duration.seconds(2), imageView);
        translateTransitiondown.setFromY(0);
        translateTransitiondown.setToY(scene.getHeight() / 2 - image.getHeight() / 2 - 20);
        translateTransitiondown.setInterpolator(Interpolator.EASE_IN);
        //创建顺序动画
        SequentialTransition sequentialTransition = new SequentialTransition(translateTransitionin, translateTransitiondown);
        sequentialTransition.setOnFinished(event -> {
            //初始化键盘控制
            heroControl.initKeyControl();
            //创建飞机移动动画
            heroControl.createAnimation().play();
            callback.onCallbak();
        });
        sequentialTransition.play();
    }

    /**
     * 飞出动画
     *
     * @return
     */
    public void out(CustomEvent callback) {
        //移除键盘控制
        heroControl.removeKeyControl();
        //飞机飞出动画
        double translateY = imageView.getTranslateY();
        //创建平移动画类
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), imageView);
        translateTransition.setFromY(translateY);
        translateTransition.setToY(-scene.getHeight() - 100);
        //逐渐加速
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        translateTransition.setOnFinished(event -> {
            //移除飞机
            container.getChildren().remove(imageView);
            callback.onCallbak();
        });
        //播放
        translateTransition.play();
    }

    /**
     * 发射子弹
     */
    public void fireBullet() {
        HeroBullet.fireBullet(container, imageView);
    }

    public double getSpeed() {
        return speed;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Pane getContainer() {
        return container;
    }

    public void setContainer(Pane container) {
        this.container = container;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public CustomEvent getCallback() {
        return callback;
    }

    public void setCallback(CustomEvent callback) {
        this.callback = callback;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
