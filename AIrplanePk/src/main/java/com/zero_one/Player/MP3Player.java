package com.zero_one.Player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description: MP3播放器
 * @Author: QX
 * @Date: 2024-06-24 10:26
 */

@Slf4j
@Setter
public class MP3Player {
    private AdvancedPlayer player;
    private ExecutorService executorService;
    private Future<?> future;
    private boolean isPaused;
    private int pausedFrame;
    private boolean loop;
    private String filePath;

    public MP3Player(String filePath) {
        this.filePath = filePath;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void play() {
        if (isPaused) {
            resume();
            return;
        }

        stop();

        try {
            FileInputStream fis = new FileInputStream(filePath);
            player = new AdvancedPlayer(fis);
            future = executorService.submit(() -> {
                try {
                    player.setPlayBackListener(new PlaybackListener() {
                        @Override
                        public void playbackFinished(PlaybackEvent evt) {
                            if (!isPaused) {
                                pausedFrame = 0;
                                if (loop) {
                                    play();
                                }
                            }
                        }
                    });
                    player.play(pausedFrame, Integer.MAX_VALUE);
                } catch (JavaLayerException e) {
                    log.debug(e.getMessage());
                }
            });
        } catch (JavaLayerException | IOException e) {
            log.debug(e.getMessage());
        }
    }

    public void pause() {
        if (player != null) {
            try {
                isPaused = true;
                pausedFrame = 0;
                player.close();
                future.cancel(true);
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }
    }

    public void resume() {
        if (isPaused) {
            isPaused = false;
            play();
        }
    }

    public void stop() {
        if (player != null) {
            player.close();
            future.cancel(true);
            pausedFrame = 0;
            isPaused = false;
        }
    }

    public boolean isPlaying() {
        return future != null && !future.isDone();
    }
}

