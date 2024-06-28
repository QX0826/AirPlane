package com.zero_one.enemy;

import com.zero_one.Load.Assets;
import com.zero_one.Player.MP3Player;
import com.zero_one.common.CustomEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 爆炸类
 * @Author: QX
 * @Date: 2024-06-27 15:15
 */

 @Slf4j


public class EnemyExplosion {
     public static MP3Player player;//音频播放器

     public static void explode(Pane container, ImageView enemy, CustomEvent callback){
         //回调
         callback.onCallbak();
         //声音
         MP3Player.playOnce(Assets.AUDIOS.get("effect_explosion_enemy_type_a_1"));
         //时间轴
         Timeline timeline = new Timeline(
                 new KeyFrame(Duration.ZERO, event ->enemy.setImage(new Image(Assets.IMAGES.get("explosion1")))),
                 new KeyFrame(Duration.millis(100), event -> enemy.setImage(new Image(Assets.IMAGES.get("explosion2")))),
                 new KeyFrame(Duration.millis(200), event -> enemy.setImage(new Image(Assets.IMAGES.get("explosion3")))),
                 new KeyFrame(Duration.millis(300), event -> enemy.setImage(new Image(Assets.IMAGES.get("explosion4")))),
                 new KeyFrame(Duration.millis(400), event -> enemy.setImage(new Image(Assets.IMAGES.get("explosion5")))),
                 new KeyFrame(Duration.millis(500), event -> enemy.setImage(new Image(Assets.IMAGES.get("explosion6")))),
                 //删除敌机
                 new KeyFrame(Duration.millis(600), event -> container.getChildren().remove(enemy))


         );
         //播放动画
         timeline.play();

     }
}
