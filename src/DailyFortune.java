import javax.swing.*;

public class DailyFortune {

    public static void main(String[] args) {
//        setupProxy();
        new DailyFortuneApp();
    }

    private static void setupProxy() {
        System.setProperty("http.proxyHost", "proxy-tct");
        System.setProperty("http.proxyPort", "3128");
    }
}
