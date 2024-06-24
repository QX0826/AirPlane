package com.zero_one;

import com.zero_one.Load.Assets;
import com.zero_one.Scene.StartMenuScene;
import com.zero_one.assembly.ReVideoView;
import com.zero_one.config.Appconfig;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;


/**
 * @Description: 游戏启动类
 * @Author: QX
 * @Date: 2024-06-21 14：00
 */
@Slf4j

public class GameLauncher extends Application {

    //舞台-》场景-》布局-》控件-》事件
    public void start(Stage primaryStage) throws InterruptedException {
        primaryStage.setTitle("AirPlane War");//设置标题
        primaryStage.getIcons().add(new Image(Assets.IMAGES.get("logo")));//设置图标
        primaryStage.setResizable(false);//设置窗口大小不可变
        primaryStage.setFullScreenExitKeyCombination(KeyCodeCombination.NO_MATCH);
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        primaryStage.show();//显示窗口

        Thread.sleep(500);
        new ReVideoView(primaryStage,Assets.VIDEOS.get("movie_story_0"),()->{
            //跳转到视频启动页面
            new StartMenuScene(primaryStage);
        });

        //设置舞台全屏
        boolean isfullScreen = Boolean.parseBoolean(Appconfig.getProperty("game.fullScreen"));
        if (isfullScreen) {
            primaryStage.setFullScreen(true);
        }else {
            primaryStage.setWidth(Integer.parseInt(Appconfig.getProperty("screen.width")));
            primaryStage.setHeight(Integer.parseInt(Appconfig.getProperty("screen.height")));

        }
   }

    public static void main(String[] args) {
        launch(args);
        log.info("GameLauncher is running");//日志输出
    }
}
































