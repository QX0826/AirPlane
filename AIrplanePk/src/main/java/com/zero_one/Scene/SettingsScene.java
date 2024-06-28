package com.zero_one.Scene;



import com.zero_one.enemy.EnemyExplosion;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;



/**
 * 选择飞机
 */
public class SettingsScene {

    public static int planeID = 1;
    /**
     * 文本标签
     */
    private static Label selectLable, soundLable;
    /**
     * 单选按钮
     */
    public static RadioButton rb1, rb2, rb3, rb4;
    /**
     * 复选框
     */
    public static CheckBox cb1, cb2;
    /**
     * 图片公共路径
     */
    public static String path = "/assets/images/plane/plane_hero_";

    /**
     * 场景设置方法
     */
    public static void start(Stage primaryStage) {
        primaryStage.setTitle("飞机设置");
        //垂直布局容器
        VBox vBox = new VBox();
        //设置布局容器内边距
        vBox.setPadding(new Insets(10));
        //设置子节点编辑
        vBox.setSpacing(10);
        //创建选择飞机文本标签
        selectLable = new Label("选择飞机");
        selectLable.setFont(Font.font("楷体", FontWeight.BOLD, 20));

        //创建一个互斥的按钮组
        ToggleGroup toggleGroup = new ToggleGroup();
        rb1 = createPlaneRadioButton(path + "1.png", "1", toggleGroup);
        rb2 = createPlaneRadioButton(path + "2.png", "2", toggleGroup);
        rb3 = createPlaneRadioButton(path + "3.png", "3", toggleGroup);
        rb4 = createPlaneRadioButton(path + "4.png", "4", toggleGroup);
//创建音效标签
        soundLable = new Label("音效标签");
        soundLable.setFont(Font.font("楷体", FontWeight.BOLD, 20));
        //设置在布局中的坐标
        soundLable.setLayoutX(10);
        soundLable.setLayoutY(100);
        //创建复选框
        cb1 = new CheckBox("背景声音");
        cb1.setLayoutX(20);
        cb1.setLayoutY(140);
        //判断如果背景声音正在播放，设置复选框为选中状态
        if (StartMenuScene.player.isPlaying()) {
            cb1.setSelected(true);
        }
        //当复选框发生变化时
        cb1.setOnAction(event -> {
            //如果未被选中
            if (!cb1.isSelected()) {
                //表示背景声音没有在播放
                StartMenuScene.player.stop();
                cb1.setSelected(false);
            } else {
                //播放声音
                StartMenuScene.player.play();
            }
        });
        cb2 = new CheckBox("爆炸声音");
        cb2.setLayoutX(20);
        cb2.setLayoutY(180);
        cb2.setSelected(true);
        cb2.setOnAction(event -> {
            if (!cb2.isSelected()) {
                EnemyExplosion.player.stop();
                cb2.setSelected(false);
            } else {
                EnemyExplosion.player.play();
            }
        });

        //创建场景
        Scene scene = new Scene(vBox,2560,1440);
        //设置场景 全屏



        primaryStage.setScene(scene);
        //创建确定按钮
        Button button = new Button("确定");
        button.setLayoutX(30);
        button.setLayoutY(220);
        button.setOnAction(event -> {
            new StartMenuScene(primaryStage);
        });

        vBox.getChildren().addAll(selectLable,rb1,rb2,rb3,rb4,soundLable,cb1,cb2,button);
        primaryStage.show();
    }

    /**
     * 创建单选按钮
     *
     * @return
     */
    private static RadioButton createPlaneRadioButton(String imagePath, String text, ToggleGroup toggleGroup) {
        //加载图片
        Image image = new Image(SettingsScene.class.getResourceAsStream(imagePath));
        //显示图像
        ImageView imageView = new ImageView(image);
        //设置图片大小
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        //创建一个单选按钮
        RadioButton radioButton = new RadioButton(text);
        //将图片设置到按钮旁边
        radioButton.setGraphic(imageView);
        //将按钮添加到按钮组中
        radioButton.setToggleGroup(toggleGroup);
        //判断飞机ID是否和文本一致
        if (planeID == Integer.parseInt(text)) {
            radioButton.setSelected(true);
        }
        //按钮被选中
        radioButton.setOnAction(event -> {
            planeID = Integer.parseInt(text);
        });
        return radioButton;
    }


}
