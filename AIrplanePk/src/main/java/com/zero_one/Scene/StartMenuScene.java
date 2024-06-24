package com.zero_one.Scene;

import com.zero_one.Level.GameLevel1;
import com.zero_one.Load.Assets;
import com.zero_one.Player.MP3Player;
import com.zero_one.assembly.ReVideoView;
import com.zero_one.config.Appconfig;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * @Description: 首页启动菜单类
 * @Author: QX
 * @Date: 2024-06-23 10:15
 */

@Slf4j
@Setter
public class StartMenuScene {

    private final Stage primaryStage;//舞台

    private Scene scene;//场景

    private StackPane root;//布局(根节点)

    private String label;//标签

    private MP3Player player;//音频播放器
    public StartMenuScene(Stage primaryStage) {

        this.primaryStage = primaryStage;
        
        initRoot();//初始化布局
        
        initScene();//初始化场景
        
        showScene();//显示场景

    }

    /**
     * 初始化布局
     */
    private void initRoot() {
        //创建根节点
        root = new StackPane();

        // 初始化根节点的宽高
        root.setPrefSize(primaryStage.getWidth(),primaryStage.getHeight());
    }

    /**
     * 初始化场景
     */
    private void initScene() {
        //创建场景
        scene = new Scene(root);

        //设置背景颜色
        scene.setFill(Color.BLACK);

    }

    /**
     * 显示场景
     */
    private void showScene() {
    //判断是不是全屏
    boolean isFullScreen = Boolean.parseBoolean(Appconfig.getProperty("game.fullScreen"));

    //设置场景
    primaryStage.setScene(scene);

    //设置全屏
    primaryStage.setFullScreen(isFullScreen);

    //初始化背景
    initBackground();

    //初始化菜单
    initMenu();

    //初始化音频播放器
    initBgm();
    }

    private void initBgm() {
        player = new MP3Player(Assets.AUDIOS.get("bgm_menu"));

        player.play();
        player.setLoop(true);

    }


    /**
     * 初始化背景
     */
    private void initBackground() {
        //加载图片
        Image image = new Image(Assets.IMAGES.get("bg_menu"));

        //创建图片视图
        ImageView imageView = new ImageView(image);

        //设置图片视图的宽高
        imageView.setFitWidth(primaryStage.getWidth());
        imageView.setFitHeight(primaryStage.getHeight());

        //添加到根节点
        root.getChildren().add(imageView);
    }

    /**
     * 初始化菜单
     */
    private void initMenu() {
        //垂直布局容器
        VBox vBox = new VBox();

        //设置布局容器宽高
        vBox.setAlignment(Pos.BOTTOM_LEFT);

        //设置内边框
        vBox.setPadding(new Insets(100));

        //创建按钮

        vBox.getChildren().add(createButton("拯救地球", event -> {
            player.stop();
            //进入第一关游戏视频
            new ReVideoView(primaryStage,Assets.VIDEOS.get("movie_story_1"),()->{

                //跳转到游戏场景
                new GameLevel1(primaryStage);

            });

        }));
        vBox.getChildren().add(createButton("即日再战", event -> {
            //退出游戏
            System.exit(0);
        }));

        //添加到根节点
        root.getChildren().add(vBox);


    }

    /**
     * 创建按钮
     */

    private Label createButton(String text, EventHandler<MouseEvent> event) {
        //创建标签
        Label label = new Label(text);

        //设置标签的宽高
        label.setFont(Font.font("楷体",FontWeight.BOLD, 50));

        //设置标签的颜色
        label.setStyle("-fx-padding:0 0 25 0;-fx-effect:dropshadow(one-pass-box,black,20,0,0,0)");

        //设置文本的颜色
        label.setTextFill(Color.WHITE);

        //设置事件监听
        label.setOnMouseClicked(event);

        return label;
    }
}
