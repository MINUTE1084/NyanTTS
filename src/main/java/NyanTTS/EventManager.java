package NyanTTS;

import NyanTTS.Player.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.w3c.dom.Text;

import javax.imageio.ImageIO;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class EventManager extends ListenerAdapter {
    private ArrayList<String> badWordList;
    private ArrayList<String> joinList = new ArrayList<>();
    private Map<String, String> emojiList;

    public EventManager(){
        badWordList = new ArrayList<>();
        badWordList.add("ㄴㄱㅁ");
        badWordList.add("ㄴㅇㅁ");
        badWordList.add("ㅂㅅ");
        badWordList.add("ㅄ");
        badWordList.add("ㅅㄲ");
        badWordList.add("ㅅㅂ");
        badWordList.add("ㅆㅂ");
        badWordList.add("ㅆㅃ");
        badWordList.add("ㅈㄹ");
        badWordList.add("ㅈㄲ");
        badWordList.add("김치녀");
        badWordList.add("김치남");
        badWordList.add("느금마");
        badWordList.add("느개비");
        badWordList.add("니애미");
        badWordList.add("니애비");
        badWordList.add("니미");
        badWordList.add("병신");
        badWordList.add("보지");
        badWordList.add("자지");
        badWordList.add("보전깨");
        badWordList.add("불알");
        badWordList.add("부랄");
        badWordList.add("뻐큐");
        badWordList.add("뻑유");
        badWordList.add("새끼");
        badWordList.add("시발");
        badWordList.add("애자");
        badWordList.add("씨발");
        badWordList.add("씨빨");
        badWordList.add("썅");
        badWordList.add("지랄");
        badWordList.add("좆");
        badWordList.add("씹");
        badWordList.add("염병");
        badWordList.add("옘병");
        badWordList.add("염병");
        badWordList.add("창녀");
        badWordList.add("창놈");
        badWordList.add("남창");
        badWordList.add("피싸개");
        badWordList.add("한남");
        badWordList.add("한녀");
        badWordList.add("호로");
        badWordList.add("엠창");
        badWordList.add("앰창");
        badWordList.add("후장");
        badWordList.add("후빨");

        emojiList = new HashMap<>();
        emojiList.put("^^", "눈웃음");
        emojiList.put("●▅▇█▇▆▅▄▇", "누워!");
        emojiList.put("@==(^o^)@", "태보해!");
    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        if (event.getMember().getUser().isBot()) return;

        AudioChannel botChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
        AudioChannel connectedChannel = event.getChannelJoined();

        if (botChannel != null && botChannel.equals(connectedChannel)) {
            if (!joinList.contains(event.getMember().getNickname())) {
                joinList.add(event.getMember().getNickname());
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        joinList.remove(event.getMember().getNickname());
                    }
                };

                timer.schedule(task, 300000);

                File newSnd = new File("tts/");
                if (!newSnd.exists()) newSnd.mkdir();
                File[] fList = newSnd.listFiles();

                if (fList.length > 50){
                    for( int i = 0; i < fList.length; i++ ) {
                        fList[i].delete();
                    }
                }

                String ttsStr = "<speak>" +
                                    "<voice name=\"WOMAN_DIALOG_BRIGHT\">" +
                                        "<prosody rate=\"slow\" volume=\"soft\">"
                                            + event.getMember().getNickname() + "님이 입장하셨습니다." +
                                        "</prosody>" +
                                    "</voice>" +
                                "</speak>";
                String Url = HttpConnection.sendPost("https://kakaoi-newtone-openapi.kakao.com/v1/synthesize", ttsStr);

                PlayerManager.getInstance().loadAndPlay(event.getGuild(), event.getGuild().getDefaultChannel(), Url);
            }
        }
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        if (event.getMember().getUser().isBot()) return;

        AudioChannel botChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
        AudioChannel connectedChannel = event.getChannelLeft();

        if (botChannel != null && botChannel.equals(connectedChannel)) {
            if (!joinList.contains(event.getMember().getNickname())) {
                joinList.add(event.getMember().getNickname());
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        joinList.remove(event.getMember().getNickname());
                    }
                };

                timer.schedule(task, 300000);

                File newSnd = new File("tts/");
                if (!newSnd.exists()) newSnd.mkdir();
                File[] fList = newSnd.listFiles();

                if (fList.length > 50){
                    for( int i = 0; i < fList.length; i++ ) {
                        fList[i].delete();
                    }
                }

                String ttsStr = "<speak>" +
                        "<voice name=\"WOMAN_DIALOG_BRIGHT\">" +
                        "<prosody rate=\"slow\" volume=\"soft\">"
                        + event.getMember().getNickname() + "님이 퇴장하셨습니다." +
                        "</prosody>" +
                        "</voice>" +
                        "</speak>";
                String Url = HttpConnection.sendPost("https://kakaoi-newtone-openapi.kakao.com/v1/synthesize", ttsStr);

                PlayerManager.getInstance().loadAndPlay(event.getGuild(), event.getGuild().getDefaultChannel(), Url);
            }
        }
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        if (event.getMember().getUser().isBot()) return;

        AudioChannel botChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
        AudioChannel connectedChannel = event.getChannelLeft();

        if (botChannel != null && botChannel.equals(connectedChannel)) {
            if (!joinList.contains(event.getMember().getNickname())) {
                joinList.add(event.getMember().getNickname());
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        joinList.remove(event.getMember().getNickname());
                    }
                };

                timer.schedule(task, 300000);

                File newSnd = new File("tts/");
                if (!newSnd.exists()) newSnd.mkdir();
                File[] fList = newSnd.listFiles();

                if (fList.length > 50){
                    for( int i = 0; i < fList.length; i++ ) {
                        fList[i].delete();
                    }
                }

                String ttsStr = "<speak>" +
                        "<voice name=\"WOMAN_DIALOG_BRIGHT\">" +
                        "<prosody rate=\"slow\" volume=\"soft\">"
                        + event.getMember().getNickname() + "님이 다른 방으로 이동하셨습니다." +
                        "</prosody>" +
                        "</voice>" +
                        "</speak>";
                String Url = HttpConnection.sendPost("https://kakaoi-newtone-openapi.kakao.com/v1/synthesize", ttsStr);

                PlayerManager.getInstance().loadAndPlay(event.getGuild(), event.getGuild().getDefaultChannel(), Url);
            }

            return;
        }

        connectedChannel = event.getChannelJoined();
        if (botChannel != null && botChannel.equals(connectedChannel)) {
            if (!joinList.contains(event.getMember().getNickname())) {
                joinList.add(event.getMember().getNickname());
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        joinList.remove(event.getMember().getNickname());
                    }
                };

                timer.schedule(task, 300000);

                File newSnd = new File("tts/");
                if (!newSnd.exists()) newSnd.mkdir();
                File[] fList = newSnd.listFiles();

                if (fList.length > 50){
                    for( int i = 0; i < fList.length; i++ ) {
                        fList[i].delete();
                    }
                }

                String ttsStr = "<speak>" +
                        "<voice name=\"WOMAN_DIALOG_BRIGHT\">" +
                        "<prosody rate=\"slow\" volume=\"soft\">"
                        + event.getMember().getNickname() + "님이 다른 방에서 이동하셨습니다." +
                        "</prosody>" +
                        "</voice>" +
                        "</speak>";
                String Url = HttpConnection.sendPost("https://kakaoi-newtone-openapi.kakao.com/v1/synthesize", ttsStr);

                PlayerManager.getInstance().loadAndPlay(event.getGuild(), event.getGuild().getDefaultChannel(), Url);
            }
        }
    }

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
                        "\n=help : 명령어를 확인합니다." +
                        "\n=connect : 음성 채널에 접속합니다." +
                        "\n=disconnect : 음성 채널 접속을 해제합니다." +
                        "\n=reset : 현재 리스팅 된 모든 음성을 제거합니다." +
                        "\n=정보 [메이플 닉네임] : maple.gg에서 캐릭터 정보를 가져옵니다." +
                        "\n;[할말] : 여성 목소리로 TTS를 실행합니다." +
                        "\n,[할말] : 남성 목소리로 TTS를 실행합니다." +
                        "\nFredBoat와의 호환성을 위해, ;;[할말]은 작동하지 않습니다." +
                        "\n필터링/변환 이후의 글자수가 100자를 초과할 경우, 작동하지 않습니다." +
                        "\n```").queue();
                break;
            case "=connect":
                if(connectedChannel == null) {
                    channel.sendMessage("음성 채널에 접속한 뒤 사용해주세요.").queue();
                    return;
                }

                joinList.clear();
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

                joinList.clear();
                PlayerManager.getInstance().getTTSManager(event.getGuild()).audioPlayer.stopTrack();
                PlayerManager.getInstance().getTTSManager(event.getGuild()).scheduler.resetQueue();
                botAudioManager.closeAudioConnection();
                break;
            case "=reset":
                joinList.clear();
                PlayerManager.getInstance().getTTSManager(event.getGuild()).audioPlayer.stopTrack();
                PlayerManager.getInstance().getTTSManager(event.getGuild()).scheduler.resetQueue();

                channel.sendMessage("현재 리스팅 된 모든 음성을 제거했습니다.").queue();
                break;
            default:
                if (cmdStr.startsWith("=정보 ")) {
                    getMapleUser(channel, cmdStr.replace("=정보 ", ""));
                }
                if (cmdStr.startsWith(";")) {
                    try {
                        if(connectedChannel == null) {
                            channel.sendMessage("음성 채널에 접속한 뒤 사용해주세요.").queue();
                            return;
                        }

                        AudioChannel botChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
                        if (botChannel == null || !botChannel.equals(connectedChannel)) {
                            joinList.clear();
                            PlayerManager.getInstance().getTTSManager(event.getGuild()).audioPlayer.stopTrack();
                            PlayerManager.getInstance().getTTSManager(event.getGuild()).scheduler.resetQueue();
                            botAudioManager.openAudioConnection(connectedChannel);
                        }

                        if (cmdStr.startsWith(";;")) return;

                        cmdStr = convertString(cmdStr.substring(1), channel);

                        if (cmdStr.length() > 100) {
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

                        String ttsStr = "<speak>" +
                                            "<voice name=\"WOMAN_DIALOG_BRIGHT\">" +
                                                "<prosody rate=\"slow\" volume=\"soft\">"
                                                    + cmdStr +
                                                "</prosody>" +
                                            "</voice>" +
                                        "</speak>";
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
                            joinList.clear();
                            PlayerManager.getInstance().getTTSManager(event.getGuild()).audioPlayer.stopTrack();
                            PlayerManager.getInstance().getTTSManager(event.getGuild()).scheduler.resetQueue();
                            botAudioManager.openAudioConnection(connectedChannel);
                        }

                        cmdStr = convertString(cmdStr.substring(1), channel);

                        if (cmdStr.length() > 100) {
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

                        String ttsStr = "<speak>" +
                                            "<voice name=\"MAN_DIALOG_BRIGHT\">" +
                                                "<prosody rate=\"slow\" volume=\"soft\">"
                                                    + cmdStr +
                                                "</prosody>" +
                                            "</voice>" +
                                        "</speak>";
                        String Url = HttpConnection.sendPost("https://kakaoi-newtone-openapi.kakao.com/v1/synthesize", ttsStr);

                        PlayerManager.getInstance().loadAndPlay(event.getGuild(), channel, Url);
                    } catch (Exception e) {
                        channel.sendMessage("음성을 출력하지 못했습니다.").queue();
                        e.printStackTrace();
                    }
                }
        }
    }

    private String convertString(String str, MessageChannel channel){
        String newStr = str;
        boolean isEnd;

        // 이모티콘 변환
        for (Map.Entry<String, String> emoji : emojiList.entrySet()) {
            if (newStr.contains(emoji.getKey())) {
                newStr = newStr.replaceAll(emoji.getKey(), emoji.getValue());
            }
        }

        //초성체 변환, 욕 순화
        do {
            isEnd = true;

            if (newStr.contains("ㄱㄱ")){
                newStr = newStr.replaceAll("ㄱ", "고");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄱㄷ")){
                newStr = newStr.replaceAll("ㄱㄷ", "기달");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄱㅅ") || newStr.contains("ㄳ")){
                newStr = newStr.replaceAll("ㄱㅅ", "감사");
                newStr = newStr.replaceAll("ㄳ", "감사");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄲㅈ")){
                newStr = newStr.replaceAll("ㄲㅈ", "저리가");
                channel.sendMessage("순화된 말로 변경합니다.").queue();
                isEnd = false;
                continue;
            }
            if (newStr.contains("꺼져")){
                newStr = newStr.replaceAll("꺼져", "저리가");
                channel.sendMessage("순화된 말로 변경합니다.").queue();
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄲㅂ")){
                newStr = newStr.replaceAll("ㄲㅂ", "까비");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄴㄴ")){
                newStr = newStr.replaceAll("ㄴ", "노");
                isEnd = false;
                continue;
            }
            if (newStr.contains("돌딤")){
                newStr = newStr.replaceAll("돌딤", "덜딤");
                channel.sendMessage("덜딤이 맞는 단어입니다.").queue();
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄴㅇㅅ")){
                newStr = newStr.replaceAll("ㄴㅇㅅ", "나이스");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄴㅇㄱ")){
                newStr = newStr.replaceAll("ㄴㅇㄱ", "상상도 못한 정체!");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄵ") || newStr.contains("ㄴㅈ")){
                newStr = newStr.replaceAll("ㄴㅈ", "노잼");
                newStr = newStr.replaceAll("ㄵ", "노잼");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄷㅊ")){
                newStr = newStr.replaceAll("ㄷㅊ", "조용히 해");
                channel.sendMessage("순화된 말로 변경합니다.").queue();
                isEnd = false;
                continue;
            }
            if (newStr.contains("닥쳐")){
                newStr = newStr.replaceAll("닥쳐", "조용히 해");
                channel.sendMessage("순화된 말로 변경합니다.").queue();
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄷㅈ")){
                newStr = newStr.replaceAll("ㄷㅈ", "닥전");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄷㅎ")){
                newStr = newStr.replaceAll("ㄷㅎ", "닥후");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄹㄱㄴ") || newStr.contains("ㄺㄴ")){
                newStr = newStr.replaceAll("ㄹㄱㄴ", "레게노");
                newStr = newStr.replaceAll("ㄺㄴ", "레게노");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄹㄷ")){
                newStr = newStr.replaceAll("ㄹㄷ", "레디");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄾ") || newStr.contains("ㄹㅌ")){
                newStr = newStr.replaceAll("ㄾ", "리트");
                newStr = newStr.replaceAll("ㄹㅌ", "리트");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄹㄹ")){
                newStr = newStr.replaceAll("ㄹ", "리");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄹㅇ")){
                newStr = newStr.replaceAll("ㄹㅇ", "리얼");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㄹㅈㄷ")){
                newStr = newStr.replaceAll("ㄹㅈㄷ", "레전드");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅁㄹ") || newStr.contains("ㅁ?ㄹ")){
                newStr = newStr.replaceAll("ㅁㄹ", "몰루?");
                newStr = newStr.replaceAll("ㅁ\\?ㄹ", "몰루?");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅂㄷㅂㄷ")){
                newStr = newStr.replaceAll("ㅂㄷㅂㄷ", "부들부들");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅂㄹ")){
                newStr = newStr.replaceAll("ㅂㄹ", "별로");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅃㄹ")){
                newStr = newStr.replaceAll("ㅃㄹ", "빨리");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅅㄱ")){
                newStr = newStr.replaceAll("ㅅㄱ", "수고");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅇㄱㅇ")){
                newStr = newStr.replaceAll("ㅇㄱㅇ", "어게이");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅇㅈㄹ")){
                newStr = newStr.replaceAll("ㅇㅈㄹ", "이러고 앉아잇내");
                channel.sendMessage("순화된 말로 변경합니다.").queue();
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅇㅈ")){
                newStr = newStr.replaceAll("ㅇㅈ", "인정");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅇㅉ")){
                newStr = newStr.replaceAll("ㅇㅉ", "어쩔");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅇㅋ")){
                newStr = newStr.replaceAll("ㅇㅋ", "오케이");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅇㅎ")){
                newStr = newStr.replaceAll("ㅇㅎ", "아하");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅈㅅ")){
                newStr = newStr.replaceAll("ㅈㅅ", "죄송");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅈㄴ")){
                newStr = newStr.replaceAll("ㅈㄴ", "엄청");
                channel.sendMessage("순화된 말로 변경합니다.").queue();
                isEnd = false;
                continue;
            }
            if (newStr.contains("존나")){
                newStr = newStr.replaceAll("존나", "엄청");
                channel.sendMessage("순화된 말로 변경합니다.").queue();
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅉㅉ")){
                newStr = newStr.replaceAll("ㅉ", "쯧");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅊㅊ")){
                newStr = newStr.replaceAll("ㅊ", "축");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅊㅋ")){
                newStr = newStr.replaceAll("ㅊㅋ", "축하");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅋㄲㅈㅁ")){
                newStr = newStr.replaceAll("ㅋㄲㅈㅁ", "콩까지마");
                isEnd = false;
                continue;
            }
            if (newStr.contains("ㅍㅁ")){
                newStr = newStr.replaceAll("ㅍㅁ", "파밍");
                isEnd = false;
            }
            if (newStr.contains("ㅎㅇ")){
                newStr = newStr.replaceAll("ㅎㅇ", "하이");
                isEnd = false;
            }
            if (newStr.contains("ㅃㅇ") || newStr.contains("ㅂㅇ")){
                newStr = newStr.replaceAll("ㅃㅇ", "바이");
                newStr = newStr.replaceAll("ㅂㅇ", "바이");
                isEnd = false;
            }
        } while (!isEnd);

        //욕설 필터링
        String badWord = newStr;
        badWord = badWord.replaceAll("0", "");
        badWord = badWord.replaceAll("1", "");
        badWord = badWord.replaceAll("2", "");
        badWord = badWord.replaceAll("3", "");
        badWord = badWord.replaceAll("4", "");
        badWord = badWord.replaceAll("5", "");
        badWord = badWord.replaceAll("6", "");
        badWord = badWord.replaceAll("7", "");
        badWord = badWord.replaceAll("8", "");
        badWord = badWord.replaceAll("9", "");

        for (String bad : badWordList) {
            if (badWord.contains(bad)) {
                channel.sendMessage("문장이 필터링 되었습니다. 순화된 말을 사용해주세요.").queue();
                return "나쁜 말 안돼!!";
            }
        }

        //자음, 모음 변환
        newStr = newStr.replaceAll("ㄱ", "그");
        newStr = newStr.replaceAll("ㄴ", "느");
        newStr = newStr.replaceAll("ㄷ", "드");
        newStr = newStr.replaceAll("ㄹ", "르");
        newStr = newStr.replaceAll("ㅁ", "므");
        newStr = newStr.replaceAll("ㅂ", "브");
        newStr = newStr.replaceAll("ㅅ", "스");
        if (!newStr.contains("ㅇㅇ")) newStr = newStr.replaceAll("ㅇ", "으");
        newStr = newStr.replaceAll("ㅈ", "즈");
        newStr = newStr.replaceAll("ㅊ", "츠");
        newStr = newStr.replaceAll("ㅋ", "크");
        newStr = newStr.replaceAll("ㅌ", "트");
        newStr = newStr.replaceAll("ㅍ", "프");
        newStr = newStr.replaceAll("ㅎ", "흐");

        newStr = newStr.replaceAll("ㄳ", "그스");
        newStr = newStr.replaceAll("ㄵ", "느즈");
        newStr = newStr.replaceAll("ㄶ", "느흐");
        newStr = newStr.replaceAll("ㄺ", "르그");
        newStr = newStr.replaceAll("ㄻ", "르므");
        newStr = newStr.replaceAll("ㄼ", "르브");
        newStr = newStr.replaceAll("ㄽ", "르스");
        newStr = newStr.replaceAll("ㄾ", "르트");
        newStr = newStr.replaceAll("ㄿ", "르프");
        newStr = newStr.replaceAll("ㅀ", "르흐");
        newStr = newStr.replaceAll("ㅄ", "브스");

        newStr = newStr.replaceAll("ㄲ", "끄");
        newStr = newStr.replaceAll("ㄸ", "뜨");
        newStr = newStr.replaceAll("ㅃ", "쁘");
        newStr = newStr.replaceAll("ㅆ", "쓰");
        newStr = newStr.replaceAll("ㅉ", "쯔");

        newStr = newStr.replaceAll("ㅏ", "아");
        newStr = newStr.replaceAll("ㅑ", "야");
        newStr = newStr.replaceAll("ㅓ", "어");
        newStr = newStr.replaceAll("ㅕ", "여");
        newStr = newStr.replaceAll("ㅗ", "오");
        newStr = newStr.replaceAll("ㅛ", "요");
        newStr = newStr.replaceAll("ㅜ", "우");
        newStr = newStr.replaceAll("ㅠ", "유");
        newStr = newStr.replaceAll("ㅡ", "으");
        newStr = newStr.replaceAll("ㅣ", "이");

        newStr = newStr.replaceAll("ㅐ", "애");
        newStr = newStr.replaceAll("ㅒ", "얘");
        newStr = newStr.replaceAll("ㅔ", "에");
        newStr = newStr.replaceAll("ㅖ", "예");
        newStr = newStr.replaceAll("ㅘ", "와");
        newStr = newStr.replaceAll("ㅙ", "왜");
        newStr = newStr.replaceAll("ㅚ", "외");
        newStr = newStr.replaceAll("ㅝ", "워");
        newStr = newStr.replaceAll("ㅞ", "웨");
        newStr = newStr.replaceAll("ㅟ", "위");
        newStr = newStr.replaceAll("ㅢ", "의");

        //욕설 필터링
        badWord = newStr;
        badWord = badWord.replaceAll("0", "");
        badWord = badWord.replaceAll("1", "");
        badWord = badWord.replaceAll("2", "");
        badWord = badWord.replaceAll("3", "");
        badWord = badWord.replaceAll("4", "");
        badWord = badWord.replaceAll("5", "");
        badWord = badWord.replaceAll("6", "");
        badWord = badWord.replaceAll("7", "");
        badWord = badWord.replaceAll("8", "");
        badWord = badWord.replaceAll("9", "");

        for (String bad : badWordList) {
            if (badWord.contains(bad)) {
                channel.sendMessage("문장이 필터링 되었습니다. 순화된 말을 사용해주세요.").queue();
                return "나쁜 말 안돼!!";
            }
        }

        // 특수문자 변환
        if(!newStr.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            newStr = newStr.replaceAll("\\?", "물음표");
            newStr = newStr.replaceAll("!", "느낌표");
        }
        newStr = newStr.replaceAll("@", "골뱅이");
        newStr = newStr.replaceAll("#", "샵");
        newStr = newStr.replaceAll("%", "퍼센트");
        newStr = newStr.replaceAll("\\^", "캐럿");
        newStr = newStr.replaceAll("&", "앤");
        newStr = newStr.replaceAll("\\*", "곱하기");
        newStr = newStr.replaceAll("/", "나누기");
        newStr = newStr.replaceAll("-", "빼기");
        newStr = newStr.replaceAll("_", "언더바");
        newStr = newStr.replaceAll("=", "는");
        newStr = newStr.replaceAll("\\+", "더하기");
        newStr = newStr.replaceAll(":", "콜론");
        newStr = newStr.replaceAll(";", "세미콜론");
        return newStr;
    }

    private void getMapleUser(MessageChannel channel, String name) {
        try {
            var temp = Jsoup.connect("https://maple.gg/u/" + name).get();
            var data = temp.select("div.character-card");

            String nick = data.select("div.character-card-name").get(0).text() + " (" + data.select("li.character-card-summary-item").get(1).text() + ")";
            String server = data.select("li.character-card-summary-item").get(0).text();
            String classes = data.select("li.character-card-summary-item").get(2).text();
            String popular = data.select("div.character-card-popular").get(0).text().split("길드 ")[0].split("인기도 ")[1];
            String guild = data.select("div.character-card-popular").get(0).text().split("길드 ")[1];
            String rank = data.select("div.character-card-popular").get(1).text().split("직업랭킹 ")[1];

            String tower = data.select("li.character-card-additional-item").get(0).text().split("무릉도장 ")[1];
            try {
                String towerTemp[] = tower.split(" ");
                tower = towerTemp[1] + " (" + towerTemp[2] + " " + towerTemp[3] + ")";
            } catch (Exception e) {
                tower = "확인 불가";
            }

            String union = data.select("li.character-card-additional-item").get(1).text().split("유니온 ")[1];
            try {
                String unionTemp[] = union.split(" ");
                union = unionTemp[0] + " " + unionTemp[1] + " (" + unionTemp[2] + ")";
            } catch (Exception e) {
                union = "확인 불가";
            }

            String seed = data.select("li.character-card-additional-item").get(2).text().split("더시드 ")[1];
            try {
                String seedTemp[] = seed.split(" ");
                seed = seedTemp[1] + " (" + seedTemp[2] + " " + seedTemp[3] + ")";
            } catch (Exception e) {
                seed = "확인 불가";
            }
            String charUrl = data.select("img.character-image").get(0).absUrl("src");

            File imgData = SaveImg(charUrl);

            Random rand = new Random();
            EmbedBuilder eb = new EmbedBuilder();

            eb.setTitle(nick, "https://maple.gg/u/" + name);
            eb.setColor(Color.getHSBColor(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            eb.setThumbnail("attachment://char.png");

            eb.addField("직업", classes, true);
            eb.addField("서버", server, true);
            eb.addField("인기도", popular, true);
            eb.addField("길드", guild, true);
            eb.addField("직업 랭킹", rank, true);
            eb.addField("무릉도장", tower, true);
            eb.addField("유니온", union, true);
            eb.addField("더 시드", seed, true);

            channel.sendMessageEmbeds(eb.build()).addFile(imgData, "char.png").queue();
            imgData.delete();
        }
        catch (Exception e) {
            e.printStackTrace();
            channel.sendMessage("캐릭터를 찾을 수 없습니다.").queue();
        }
    }

    private File SaveImg(String urlStr){
        try {
            File imageFile = new File(HttpConnection.randomString() + ".png");
            URL url = new URL(urlStr);
            BufferedImage image = ImageIO.read(url);

            ImageIO.write(image,"png", imageFile);
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
