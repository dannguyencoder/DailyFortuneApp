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
                label1.setText("Name has been submitted.");

                try {
                    getFortune();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getFortune() throws IOException {
        //1. call api
        String response = callApi(API_URL);
        System.out.println(response);
        //2. parse the response => get the fortune string
        String quote = parseResponse(response);
        //3. show it in a dialog
    }

    private String parseResponse(String response) {

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
