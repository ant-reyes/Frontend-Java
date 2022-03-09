import javax.swing.*;
import java.io.*;
import java.net.*;
import org.json.*;

public class Frontend {
    public static void main(String[] args) throws Exception {
        UI();
    }

    public static void UI() {
        JFrame frame = new JFrame("Frontend-Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel panel2 = new JPanel();
        JLabel display = new JLabel("Click a button to display a message.");
        panel2.add(display);

        JPanel panel1 = new JPanel();
        JButton btn1 = new JButton("getSomeData");
        btn1.addActionListener(e -> {
            readFromServer(display, true);
        });

        JButton btn2 = new JButton("getSomeDataFromFile");
        btn2.addActionListener(e -> {
            readFromServer(display, false);
        });

        panel1.add(btn1);
        panel1.add(btn2);

        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panel1);
        frame.setSize(500, 200);
        frame.setVisible(true);
    }

    public static void readFromServer(JLabel display, Boolean choice) {
        try {
            URL url = new URL("http://localhost:5000/getSomeDataFromFile");

            if (choice == true) {
                url = new URL("http://localhost:5000/getSomeData");
            }

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                try {
                    JSONObject obj = new JSONObject(inputLine);
                    display.setText(obj.getString("message"));
                } catch (JSONException err) {
                    err.printStackTrace();
                }
            }

            in.close();
        } catch(ConnectException | MalformedURLException e) {
            display.setText("Server is down.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
