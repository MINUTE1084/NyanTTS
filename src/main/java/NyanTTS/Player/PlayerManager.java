package NyanTTS.Player;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager instance;

    private final Map<Long, TTSManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager(){
        musicManagers = new HashMap<>();
        audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
    }

    public TTSManager getTTSManager(Guild guild){
        return musicManagers.computeIfAbsent(guild.getIdLong(), (guildID) -> {
            final TTSManager ttsManager = new TTSManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(ttsManager.getTtsPlayer());
            return ttsManager;
        });
    }

    public void loadAndPlay(Guild guild, MessageChannel channel, String trackUrl){
        final TTSManager ttsManager = this.getTTSManager(guild);

        audioPlayerManager.loadItem(trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                ttsManager.scheduler.queue(track);
                System.out.println("A");

            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                System.out.println("C");
            }

            @Override
            public void noMatches() {
                System.out.println("C");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("오류가 발생했습니다. 오류 내용 : " + exception.getMessage()).queue();
                exception.printStackTrace();
            }
        });

    }

    public static PlayerManager getInstance(){
        if (instance == null) {
            instance = new PlayerManager();
        }

        return instance;
    }
}
