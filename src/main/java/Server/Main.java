package Server;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Main {
    private static JSONObject jsonObject;

    public static void main(String[] args) {


        List<Product> products = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new FileReader("categories.tsv"))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] words = line.split("\t");

                products.add(new Product(words[0], words[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Не могу найти файл");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Файл пустой");
            e.printStackTrace();
        }

        Map<String, Category> categoriesProduct = new HashMap<>();
        for (Product product : products) {
            String category = product.getCategory();
            if (!categoriesProduct.containsKey(category)) {
                categoriesProduct.put(category, new Category(category, 0));
            }
        }


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

                    categoriesProduct.get(category).addSum(sum);

                    CalculationCategory calculationCategory = new CalculationCategory();
                    List<Category> categoryRequest = new ArrayList<>();

                    categoryRequest.add(new Category(category, sum));

                    calculationCategory.setMaxCategory(categoryRequest);

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();


                    out.println(gson.toJson(calculationCategory));


                }
            }
        } catch (
                IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }


}