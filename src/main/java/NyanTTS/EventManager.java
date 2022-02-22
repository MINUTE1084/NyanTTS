package NyanTTS;

import NyanTTS.Player.PlayerManager;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class EventManager extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String cmdStr = event.getMessage().getContentRaw();
        MessageChannel channel = event.getChannel();
        AudioChannel connectedChannel = event.getMember().getVoiceState().getChannel();
        AudioManager botAudioManager = event.getGuild().getAudioManager();

        switch (cmdStr.toLowerCase()){
            case "=help":
                channel.sendMessage("```" +
                        "버그 많음! 말이 안나온다면 내보냈다가 다시 접속시켜주세요." +
                        "\n=help : 명령어를 확인합니다." +
                        "\n=connect : 음성 채널에 접속합니다." +
                        "\n=disconnect : 음성 채널 접속을 해제합니다." +
                        "\n;[할말] : 여성 목소리로 TTS를 실행합니다." +
                        "\n,[할말] : 남성 목소리로 TTS를 실행합니다." +
                        "\n```").queue();
                break;
            case "=connect":
                if(connectedChannel == null) {
                    channel.sendMessage("음성 채널에 접속한 뒤 사용해주세요.").queue();
                    return;
                }

                PlayerManager.getInstance().getTTSManager(event.getGuild()).audioPlayer.stopTrack();
                PlayerManager.getInstance().getTTSManager(event.getGuild()).scheduler.resetQueue();
                botAudioManager.openAudioConnection(connectedChannel);
                break;
            case "=disconnect":
                connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
                if(connectedChannel == null) {
                    channel.sendMessage("현재 음성 채널에 접속 중이지 않습니다.").queue();
                    return;
                }

                PlayerManager.getInstance().getTTSManager(event.getGuild()).audioPlayer.stopTrack();
                PlayerManager.getInstance().getTTSManager(event.getGuild()).scheduler.resetQueue();
                botAudioManager.closeAudioConnection();
                break;
            case "=reset":
                PlayerManager.getInstance().getTTSManager(event.getGuild()).audioPlayer.stopTrack();
                PlayerManager.getInstance().getTTSManager(event.getGuild()).scheduler.resetQueue();

                channel.sendMessage("현재 리스팅 된 모든 음성을 제거했습니다.").queue();
                break;
            default:
                if (cmdStr.startsWith(";")) {
                    try {
                        AudioChannel botChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
                        if (botChannel == null || !botChannel.equals(connectedChannel)) {
                            PlayerManager.getInstance().getTTSManager(event.getGuild()).audioPlayer.stopTrack();
                            PlayerManager.getInstance().getTTSManager(event.getGuild()).scheduler.resetQueue();
                            botAudioManager.openAudioConnection(connectedChannel);
                        }

                        if (cmdStr.substring(1).length() > 150) {
                            channel.sendMessage("텍스트가 너무 깁니다.").queue();
                            return;
                        }

                        File newSnd = new File("tts/");
                        if (!newSnd.exists()) newSnd.mkdir();
                        File[] fList = newSnd.listFiles();

                        if (fList.length > 50){
                            for( int i = 0; i < fList.length; i++ ) {
                                fList[i].delete();
                            }
                        }

                        String ttsStr = "<speak><voice name=\"WOMAN_DIALOG_BRIGHT\">" + cmdStr.substring(1) + "</voice></speak>";
                        String Url = HttpConnection.sendPost("https://kakaoi-newtone-openapi.kakao.com/v1/synthesize", ttsStr);

                        PlayerManager.getInstance().loadAndPlay(event.getGuild(), channel, Url);
                    } catch (Exception e) {
                        channel.sendMessage("음성을 출력하지 못했습니다.").queue();
                        e.printStackTrace();
                    }
                }
                if (cmdStr.startsWith(",")) {
                    try {
                        AudioChannel botChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
                        if (botChannel == null || !botChannel.equals(connectedChannel)) {
                            PlayerManager.getInstance().getTTSManager(event.getGuild()).audioPlayer.stopTrack();
                            PlayerManager.getInstance().getTTSManager(event.getGuild()).scheduler.resetQueue();
                            botAudioManager.openAudioConnection(connectedChannel);
                        }

                        if (cmdStr.substring(1).length() > 150) {
                            channel.sendMessage("텍스트가 너무 깁니다.").queue();
                            return;
                        }

                        File newSnd = new File("tts/");
                        if (!newSnd.exists()) newSnd.mkdir();
                        File[] fList = newSnd.listFiles();

                        if (fList.length > 50){
                            for( int i = 0; i < fList.length; i++ ) {
                                fList[i].delete();
                            }
                        }

                        String ttsStr = "<speak><voice name=\"MAN_DIALOG_BRIGHT\">" + cmdStr.substring(1) + "</voice></speak>";
                        String Url = HttpConnection.sendPost("https://kakaoi-newtone-openapi.kakao.com/v1/synthesize", ttsStr);

                        PlayerManager.getInstance().loadAndPlay(event.getGuild(), channel, Url);
                    } catch (Exception e) {
                        channel.sendMessage("음성을 출력하지 못했습니다.").queue();
                        e.printStackTrace();
                    }
                }
        }
    }
}
