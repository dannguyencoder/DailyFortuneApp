import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DailyFortuneApp {

    public static final String API_URL = "https://talaikis.com/api/quotes/random/";

    JFrame gettingResultFrame;

    public DailyFortuneApp() {
        JFrame f=new JFrame("Button Example");
        //submit button
        JButton b=new JButton("Get your fortune");
        b.setBounds(100,100,140, 40);
        //enter name label
        JLabel label = new JLabel();
        label.setText("Enter Name :");
        label.setBounds(10, 10, 100, 100);
        //empty label which will show event after button clicked
        JLabel label1 = new JLabel();
        label1.setBounds(10, 110, 200, 100);
        //textfield to enter name
        JTextField textfield= new JTextField();
        textfield.setBounds(110, 50, 130, 30);
        //add to frame
        f.add(label1);
        f.add(textfield);
        f.add(label);
        f.add(b);
        f.setSize(300,300);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        //action listener
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                label1.setText("Get your quote now !");
                String name = textfield.getText();
                try {
                    getFortune(name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    showGettingResultWindow(name);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showGettingResultWindow(String name) throws InterruptedException {
//        JOptionPane.showMessageDialog(null, "Getting the result...");
        int time = 0;
        JLabel label = new JLabel("Getting the result...: " + time);
        gettingResultFrame = new JFrame();
        gettingResultFrame.add(label);
        gettingResultFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gettingResultFrame.setSize(250, 200);
        gettingResultFrame.setLocationRelativeTo(null);
        gettingResultFrame.setLayout(new GridBagLayout());
        gettingResultFrame.setVisible(true);

        new Thread() {
            int counter = 10;
            public void run() {
                while(counter >= 0) {
                    label.setText(String.format("%s, please wait...%d", name, (counter--)));
                    label.getParent().validate();
                    try{
                        Thread.sleep(1000);
                    } catch(Exception e) {}
                }
            }
        }.start();
    }

    private void getFortune(String name) throws IOException {
        //1. call api
        final String[] response = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response[0] = callApi(API_URL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(response[0]);
                        //2. parse the response => get the fortune string
                        String quote = parseResponseAndGetQuote(response[0]);
                        System.out.println(quote);
                        //3. show it in a dialog
                        showQuoteDialog(name, quote);
                    }
                });
            }
        });
        thread.start();
    }

    private void showQuoteDialog(String name, String quote) {
        gettingResultFrame.dispose();
        JOptionPane.showMessageDialog(null, String.format("Hi %s, your quote today is:%n%s", name, quote));
    }

    private String parseResponseAndGetQuote(String response) {
        Quote quote = new Gson().fromJson(response, Quote.class);
        return quote.getQuote();
    }

    private String callApi(String apiUrl) throws IOException {

        StringBuilder result = new StringBuilder();
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
