import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Basket {
    private String[] prodName;
    private int[] prices;
    private int[] prodAmount;


    public Basket(String[] productName, int[] price) {
        this.prodName = productName;
        this.prices = price;
        prodAmount = new int[prodName.length];
    }

    public void addToCart(int productNum, int amount) {
        prodAmount[productNum] += amount;
    }

    public void printCart() {
        int sumProducts = 0;

        System.out.println("Ваша корзина:");

        for (int i = 0; i < prodName.length; i++) {
            if (prodAmount[i] != 0) {
                System.out.println(prodName[i] + " " + prodAmount[i] + " шт " + prices[i] + " руб/шт "
                        + (prodAmount[i] * prices[i]) + " руб в сумме");
                sumProducts += prodAmount[i] * prices[i];
            }
        }
        System.out.println("Итого " + sumProducts + " руб");
    }

    public void saveJson(File jsonFile) throws IOException {
        try (FileWriter writer = new FileWriter(jsonFile, false)) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            writer.write(gson.toJson(this));
        }
    }

    static Basket loadFromJsonFile(File jsonFile) throws IOException {
        if (jsonFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(jsonFile));) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                return gson.fromJson(br.readLine(), Basket.class);
            }
        } else {
            String[] products = {"Хлеб", "Сырок", "Шоколадка"};
            int[] prices = {40, 60, 100};
            return new Basket(products, prices);
        }
    }

    public String[] getProdName() {
        return prodName;
    }

    public int[] getPrices() {
        return prices;
    }
}
