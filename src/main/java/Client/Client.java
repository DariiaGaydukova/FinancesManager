package Client;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

    public static void main(String[] args) {

        String host = "127.0.0.1";
        int port = 8989;

        try (
                Socket socket = new Socket(host, port)) {
            // стартуем сервер один(!) раз
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                Request requestClient = new Request();
                requestClient.setSum(200);
                requestClient.setTitle("еда");
                requestClient.setDate("2022.11.22");
                requestClienttoJS(requestClient);

                out.println("new_data.json");
                String answerServer = in.readLine();
                System.out.println(answerServer);


            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void requestClienttoJS(Request requestClient) {

        JSONObject obj = new JSONObject();
        obj.put("title", requestClient.getTitle());
        obj.put("date", requestClient.getDate());
        Integer sum = requestClient.getSum();
        obj.put("sum", Integer.toString(sum));
        try (FileWriter file = new FileWriter("new_data.json")) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
        }
    }

}