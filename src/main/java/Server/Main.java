package Server;


import Client.Request;
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
        List<Category> categoryRequest = new ArrayList<>();
        File fileHistory = new File("data.bin");


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

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    Request request = gson.fromJson(in.readLine(), Request.class);

                    String category = "другое";
                    for (Product product : products) {
                        if (product.getTitle().equals(request.getTitle())) {
                            category = product.getCategory();
                            break;
                        }
                    }

                    categoriesProduct.get(category).addSum(request.getSum());

                    CalculationCategory calculationCategory = new CalculationCategory();


                    categoryRequest.add(new Category(category, request.getSum()));

                    if (!fileHistory.exists()) {
                        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileHistory));) {

                            objectOutputStream.writeObject(categoryRequest);

                        } catch (IOException e) {
                            System.out.println("Не могу сохранить запрос");
                        }
                    } else {

                        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileHistory));) {
                            categoryRequest = (ArrayList<Category>) objectInputStream.readObject();

                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    calculationCategory.setMaxCategory(categoryRequest);

                    Gson gson2 = builder.create();

                    out.println(gson2.toJson(calculationCategory));


                }
            }
        } catch (
                IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}