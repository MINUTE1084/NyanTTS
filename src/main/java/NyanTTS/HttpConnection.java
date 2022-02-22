package NyanTTS;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HttpConnection {
    public static String sendPost(String pURL, String pList) {
        try {
            URL url = new URL(pURL);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            http.setRequestProperty("Content-Type", "application/xml");
            http.setRequestProperty("Authorization", "KakaoAK ");

            StringBuffer buffer = new StringBuffer();
            buffer.append(pList);

            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            InputStream is = http.getInputStream();

            File newSnd = new File("tts/" + randomString() + ".mp3");
            OutputStream outstream = new FileOutputStream(newSnd.getAbsolutePath());
            byte[] buffers = new byte[4096];
            int len;
            while ((len = is.read(buffers)) > 0) {
                outstream.write(buffers, 0, len);
            }
            outstream.close();

            return newSnd.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String randomString() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
