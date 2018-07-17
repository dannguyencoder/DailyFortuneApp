import javax.swing.*;

public class DailyFortune {

    public static void main(String[] args) {
        setupProxy();
        new DailyFortuneApp();
    }

    private static void setupProxy() {
        System.setProperty("http.proxyHost", "10.60.135.36");
        System.setProperty("http.proxyPort", "8800");
    }
}
