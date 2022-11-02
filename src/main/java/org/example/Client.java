package org.example;

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
                requestClient("еда", "2022.10.22", "200");
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

    private static void requestClient(String title, String date, String sum) {

        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("date", date);
        obj.put("sum", sum);
        try (FileWriter file = new FileWriter("new_data.json")) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
        }
    }

}