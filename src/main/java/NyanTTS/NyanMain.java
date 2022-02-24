package NyanTTS;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class NyanMain {
    public static JDA jda;
    public static String discordKey;
    public static String kakaoKey;

    public static void main(String[] args) throws LoginException {
        if (args.length > 0) discordKey = args[0];
        if (args.length > 1) kakaoKey = args[1];

        JDABuilder builder = JDABuilder.createDefault(discordKey);
        builder.addEventListeners(new EventManager());
        builder.setActivity(Activity.listening("=help"));

        jda = builder.build();
    }
}