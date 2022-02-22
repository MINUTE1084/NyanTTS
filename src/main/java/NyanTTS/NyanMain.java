package NyanTTS;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class NyanMain {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = JDABuilder.createDefault("");
        builder.addEventListeners(new EventManager());
        builder.setActivity(Activity.listening("=help"));

        jda = builder.build();
    }
}