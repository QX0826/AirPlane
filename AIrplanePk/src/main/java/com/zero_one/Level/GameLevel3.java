package com.zero_one.Level;

import com.zero_one.Load.Assets;
import com.zero_one.Player.MP3Player;
import com.zero_one.Scene.ReLoopImageView;
import com.zero_one.Scene.StartMenuScene;
import com.zero_one.assembly.ReVideoView;
import com.zero_one.config.Appconfig;
import com.zero_one.enemy.EnemySpawn;
import com.zero_one.hero.HeroPlane;
import com.zero_one.record.GameScore;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 第三关地图
 * @Author: QX
 * @Date: 2024-06-28 10:23
 */

 @Slf4j

public class GameLevel3 {

    private Stage primarystage;//舞台
    private Scene scene;//场景
    private StackPane root;//布局(根节点)
    private Label label;//标签
    private MP3Player player;//音频播放器
    private ReLoopImageView level3Map;//地图
    private HeroPlane heroPlane;    //飞机
    private EnemySpawn enemySpawn;//敌机生成


    public GameLevel3(Stage primaryStage) {
        this.primarystage = primaryStage;
        initRoot();//初始化布局
        initScene();//初始化场景
        showScene();//显示场景
    }

    private void initRoot() {
        //创建根节点
        root = new StackPane();
        // 初始化根节点的宽高
        root.setPrefSize(primarystage.getWidth(), primarystage.getHeight());
    }

    private void initScene() {
        //创建场景
        scene = new Scene(root);
        //设置背景颜色
        scene.setFill(Color.BLACK);
    }

    private void showScene() {
        //判断是不是全屏
        boolean isFullScreen = Boolean.parseBoolean(Appconfig.getProperty("game.fullScreen"));
        //设置场景
        primarystage.setScene(scene);
        //设置全屏
        primarystage.setFullScreen(isFullScreen);
        //初始化背景
        initBackground();
        //初始化音频播放器
        initBgm();
        //初始化地图
        initLevel3Map();
        //初始化飞机
        initHeroPlane();
        //初始化计分板
        initScoreBoard();
    }

    private void initBackground() {
        //加载图片
        Image image = new Image(Assets.IMAGES.get("bg_game_2"));
        //创建图片视图
        ImageView imageView = new ImageView(image);
        //设置图片视图的宽高
        imageView.setFitWidth(primarystage.getWidth());
        imageView.setFitHeight(primarystage.getHeight());
        //添加到根节点
        root.getChildren().add(imageView);
    }

    private void initBgm() {
        player = new MP3Player(Assets.AUDIOS.get("bgm_level_3"));
        player.play();
        player.setLoop(true);
    }

    private void initLevel3Map() {
        level3Map = new ReLoopImageView(Assets.IMAGES.get("map_level_3"), primarystage.getHeight(), root);
    }

    private void initHeroPlane() {
        heroPlane = new HeroPlane(root, scene, level3Map.actualWidth, this::initEnemySpawn);
        heroPlane.in();
    }

    private void initEnemySpawn() {
        enemySpawn = new EnemySpawn(root, level3Map.actualWidth, () -> {
            //分数标签
            GameScore.addScore();
            label.setText(String.valueOf(GameScore.score));

            if (GameScore.score >= 15) {

                heroPlane.out((() -> {
                    player.stop();
                    enemySpawn.stopSpawn();
                    //播放视频
                    new ReVideoView(primarystage, Assets.VIDEOS.get("movie_story_4"), () -> {
                        new StartMenuScene(primarystage);
                    });
                }));
            }
        });
        enemySpawn.StartSpawn();
    }

    private void initScoreBoard() {
        label = new Label(String.valueOf(GameScore.score));
        label.setFont(Font.font("楷体", FontWeight.BOLD, 20));
        label.setTextFill(Color.YELLOW);
        //设置位置在地图左上角
        label.setTranslateX(-level3Map.actualWidth / 2 + 30);
        label.setTranslateY(-scene.getHeight() / 2 + 30);
        //添加到根节点
        root.getChildren().add(label);
    }
}
