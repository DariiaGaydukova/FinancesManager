package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    private static JSONObject jsonObject;

    public static void main(String[] args) throws ParseException {


        try (
                ServerSocket serverSocket = new ServerSocket(8989);) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {

                    String infoBuy = in.readLine();
                    JSONParser parser = new JSONParser();

                    try {
                        Object object = parser.parse(new FileReader(infoBuy));
                        jsonObject = (JSONObject) object;
                        System.out.println(jsonObject);
                    } catch (IOException | ParseException e) {
                    }


                    String category = (String) jsonObject.get("title");
                    String data = (String) jsonObject.get("data");
                    String sumStr = (String) jsonObject.get("sum");
                    Integer sum = Integer.parseInt(sumStr);

                    String[] maxCategory = CalculationCategory.setMaxCategory(category, sum);

                    out.println("{ " +
                            " maxCategory: " + " { " +
                            "category: " + maxCategory[0] + " , " +
                            "sum: " + maxCategory[1] + " } " + " } ");


                }
            }
        } catch (
                IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}