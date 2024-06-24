package com.zero_one.Load;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j


/**
 * @Description: 资源加载器
 * @Author: QX
 * @Date: 2024-06-22 9:41
 */

public class Assets {



    public Assets() {
    }

    /**
     * 存储图片的资源文件
     */
    private static final String IMAGE_DIRECTORY = "assets"+ File.separator+"images";
    /**
     * 存储音频的资源文件
     */
    private static final String AUDIO_DIRECTORY = "assets"+File.separator+"audio";
    /**
     * 存储视频的资源文件
     */
    private static final String VIDEO_DIRECTORY = "assets"+File.separator+"video";

    /**
     * 存储图片集合
     */

    public  static Map<String, String> IMAGES = new HashMap<>();

    /**
     * 存储音频集合
     */

    public  static Map<String, String> AUDIOS = new HashMap<>();

    /**
     * 存储视频集合
     */

    public static Map<String, String> VIDEOS = new HashMap<>();


    //类执行的时候就调用static方法
    static {
        //加载图片
        loadImage();
        //加载音频
        loadAudio();
        //加载视频
        loadVideo();
    }
    //加载图片

    private static void loadImage() {
        log.info("加载-----------------图片");
        loadResource(IMAGE_DIRECTORY);
    }



    //加载音频
    private static void loadAudio() {
        log.info("加载-----------------音频");
        loadResource(AUDIO_DIRECTORY);
    }

    //加载视频
    private static void loadVideo() {
        log.info("加载-----------------视频");
        loadResource(VIDEO_DIRECTORY);
    }

    //加载资源文件
    private static void loadResource(String FilePath) {
        //加载资源文件
        File loader = new File(loadClassPath().concat(FilePath));
        File[] files = loader.listFiles();
        if (files != null) {
            for (File file : files) {
               if (file.exists()&&file.isDirectory()) {
                   //加载对应的文件
                   loadResourcerecursion(file);
               }
            }

        }else {
            log.info("没有找到资源文件");
        }

    }
    /*
    * 递归遍历子文件
    * */
    private static void loadResourcerecursion(File files) {
        for(File folder: Objects.requireNonNull(files.listFiles())){
            if (folder.isDirectory()) {
                loadResourcerecursion(files);
            }else {
                String name = folder.getName();
                //截取文件名不含后缀
                String fileName = name.substring(0, name.lastIndexOf("."));
                log.info("加载资源文件："+fileName);

                String filePath =folder.getAbsolutePath();
                if (filePath.contains(IMAGE_DIRECTORY)) {
                    //加载文件名获取整个文件路径
                    IMAGES.put(fileName,"file:".concat(filePath));
                    log.info("加载图片".concat(fileName));
                }else if (filePath.contains(AUDIO_DIRECTORY)) {
                    AUDIOS.put(fileName,filePath);
                    log.info("加载音频".concat(fileName));
                } else if (filePath.contains(VIDEO_DIRECTORY)) {
                    VIDEOS.put(fileName,filePath);
                    log.info("加载视频".concat(fileName));
                }




            }
        }
    }

    /*
    * 加载资源路径
    * */

    public static String loadClassPath() {
        System.out.println(Assets.class.getClassLoader().getResource("").getPath());
        return Objects.requireNonNull(Assets.class.getClassLoader().getResource("").getPath());
    }


}
