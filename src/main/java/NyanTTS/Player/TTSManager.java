package NyanTTS.Player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class TTSManager {
    public final AudioPlayer audioPlayer;
    public final TrackScheduler scheduler;

    private final TTSPlayer ttsPlayer;

    public TTSManager(AudioPlayerManager manager){
        this.audioPlayer = manager.createPlayer();
        this.scheduler = new TrackScheduler(this.audioPlayer);
        this.audioPlayer.addListener(this.scheduler);
        this.ttsPlayer = new TTSPlayer(this.audioPlayer);
    }

    public TTSPlayer getTtsPlayer() {
        return ttsPlayer;
    }
}
