package com.zero_one.assembly;

import com.zero_one.common.CustomEvent;
import com.zero_one.config.Appconfig;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description: 视频资源加载器
 * @Author: QX
 * @Date: 2024-06-22 14:10
 */
@Slf4j

public class ReVideoView {
    //舞台
    private Stage primaryStage;
    //场景
    private Scene scene;
    //布局(根节点)
    private StackPane root;
    //媒体播放器
    private MediaPlayer mediaPlayer;
    //媒体图
    private MediaView mediaView;
    //文件路径
    private String filePath;
    private CustomEvent callback;

    /**
     * 构造方法
     *
     * @param primaryStage 舞台
     */
    public ReVideoView(Stage primaryStage, String filePath, CustomEvent callback) {
        this.primaryStage = primaryStage;
        this.filePath = filePath;
        this.callback = callback;
        //初始化布局
        initRoot();
        //初始化场景
        initScene();
        show();
    }

    private void show() {
        //判断是否是全局
        boolean isFullScreen = Boolean.parseBoolean(Appconfig.getProperty("game.fullScreen"));
        //显示视频播放
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(isFullScreen);
        //初始化播放器
        initPlayer();
    }

    /**
     * 初始化播放器
     */
    private void initPlayer() {
        //初始化媒体播放器
        initMediaPlayer();
        if (Boolean.parseBoolean(Appconfig.getProperty("game.fullScreen"))) {
            //静音
            mediaView.setFitHeight(primaryStage.getHeight());
            mediaView.setFitWidth(primaryStage.getWidth());
        } else {
            mediaView.setFitHeight(Integer.parseInt(Appconfig.getProperty("screen.height")));
            mediaView.setFitWidth(Integer.parseInt(Appconfig.getProperty("screen.width")));
        }
        root.getChildren().add(mediaView);
        //播放视频
        mediaPlayer.play();
    }

    /**
     * 初始化媒体播放器
     */
    private void initMediaPlayer() {
        //获取路径
        Path path = Paths.get(filePath);
        //创建媒体
        Media media = null;

        //创建media类 传url路径
        try {
            media = new Media(path.toUri().toURL().toString());
        } catch (MalformedURLException e) {
            log.error("视频加载错误");
        }
        //控制媒体播放
        mediaPlayer = new MediaPlayer(media);
        //显示视频播放
        mediaView = new MediaView(mediaPlayer);
        //设置播放器事件监听
        mediaPlayer.setOnReady(() -> log.debug("视频准备完成", filePath));
        mediaPlayer.setOnEndOfMedia(() -> {
            log.debug("视频播放完成", filePath);
            //将视频播放器的定位放到视频的开始位置
            Platform.runLater(() -> mediaPlayer.seek(Duration.ZERO));
            stop();
            //添加回调事件
            callback.onCallbak();
        });
        //视频播放错误
        mediaPlayer.setOnError(() -> {
            log.error("视频播放错误");
            root.getChildren().add(mediaView);
            initPlayer();
        });
    }

    /**
     * 初始化布局
     */
    private void initRoot() {
        //创建布局容器
        root = new StackPane();
        //设置布局容器大小
        root.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight());
    }

    /**
     * 初始化场景
     */
    private void initScene() {
        //创建场景
        scene = new Scene(root);
        //设置场景背景颜色
        scene.setFill(Color.BLACK);
        //设置事件监听 esc键退出
        scene.setOnKeyPressed(event -> {
            log.info("按键按下");
               if (event.getCode() == KeyCode.ESCAPE) {
                   if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING ||
                           mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                       //跳转到视频结尾
                       mediaPlayer.seek(mediaPlayer.getTotalDuration());
                   }
               }

        });
    }

    private void stop() {
        //停止播放
        mediaPlayer.stop();
    }

    private void start() {
        //播放视频
        mediaPlayer.play();
    }
}
