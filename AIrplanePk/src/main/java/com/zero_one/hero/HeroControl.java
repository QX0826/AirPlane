package com.zero_one.hero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * @Description: 键盘控制类
 * @Author: QX
 * @Date: 2024-06-24 14:13
 */

@Slf4j
@Getter
@Setter

public class HeroControl {

    //玩家飞机
    private HeroPlane heroPlane;

    //绑定页面
    private Scene scene;

    //标识空格是否被按下
    private boolean SpacePressed = false;


    //键盘按键事件
    private final EventHandler<KeyEvent> keypressed = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent event) {
            //获取键码
            KeyCode keycode = event.getCode();
            //判断按键
            if (keycode == KeyCode.UP) {
                //设置飞机用户数据
                heroPlane.getImageView().setUserData(KeyCode.UP);

            } else if (keycode == KeyCode.DOWN) {
                heroPlane.getImageView().setUserData(KeyCode.DOWN);

            } else if (keycode == KeyCode.LEFT) {
                heroPlane.getImageView().setUserData(KeyCode.LEFT);

            } else if (keycode == KeyCode.RIGHT) {
                heroPlane.getImageView().setUserData(KeyCode.RIGHT);

            }
            if (keycode == KeyCode.SPACE) {
                //设置空格键按下
                SpacePressed = true;

                //发射子弹
                heroPlane.fireBullet();

            }

        }

    };


    public HeroControl(HeroPlane heroPlane, Scene scene) {
        this.heroPlane = heroPlane;
        this.scene = scene;
    }

    /**
     * 初始化键盘控制
     */
    public void initKeyControl() {
        //绑定按键事件
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keypressed);
        //绑定松开按键事件
        scene.setOnKeyReleased(event -> {
            KeyCode keycode = event.getCode();
            if (keycode == KeyCode.UP && heroPlane.getImageView().getUserData() == KeyCode.UP) {
                //变为空
                heroPlane.getImageView().setUserData(null);
            } else if (keycode == KeyCode.DOWN && heroPlane.getImageView().getUserData() == KeyCode.DOWN) {
                heroPlane.getImageView().setUserData(null);
            } else if (keycode == KeyCode.LEFT && heroPlane.getImageView().getUserData() == KeyCode.LEFT) {
                heroPlane.getImageView().setUserData(null);
            } else if (keycode == KeyCode.RIGHT && heroPlane.getImageView().getUserData() == KeyCode.RIGHT) {
                heroPlane.getImageView().setUserData(null);
            }//按空格键发射子弹
            else if (keycode == KeyCode.SPACE) {
                //判断是否按下
                SpacePressed= false;
            }

        });

    }

    /**
     * 创建飞机移动动画
     *
     * @return
     */
    public Timeline createAnimation() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            //获取用户数据
            //heroPlane.getImageView().getUserData();

            KeyCode keycode = (KeyCode) heroPlane.getImageView().getUserData();
            if (keycode != null) {
                //获取位置
                Double newX = heroPlane.getImageView().getTranslateX();
                Double newY = heroPlane.getImageView().getTranslateY();

                //判断按键
                switch (keycode) {
                    case UP:
                        newY -= HeroPlane.speed;
                        break;
                    case DOWN:
                        newY += HeroPlane.speed;
                        break;
                    case LEFT:
                        newX -= HeroPlane.speed;
                        break;
                    case RIGHT:
                        newX += HeroPlane.speed;
                        break;

                    default:
                        break;
                }
                //获取宽高
                Double width = heroPlane.getImageView().getImage().getWidth();
                Double height = heroPlane.getImageView().getImage().getHeight();
                //计算飞机在X和Y坐标的最大移动范围
                Double minx = -heroPlane.getMaxWidth() / 2 + width / 2;
                Double maxx = heroPlane.getMaxWidth() / 2 - width / 2;
                Double miny = -heroPlane.getScene().getHeight() / 2 + height / 2;
                Double maxy = heroPlane.getScene().getHeight() / 2 - height / 2;
                //判断飞机在范围
                if (newX >= minx && newX <= maxx) {
                    heroPlane.getImageView().setTranslateX(newX);
                }
                if (newY >= miny && newY <= maxy) {
                    heroPlane.getImageView().setTranslateY(newY);
                }

            }
        }));
        //设置动画执行
        timeline.setCycleCount(Timeline.INDEFINITE);

        //返回动画
        return timeline;
    }

    /**
     * 移除键盘监听
     */
    public void removeKeyControl() {
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, keypressed);
    }


}



