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
        if (sumProducts == 0) {
            System.out.println("Пуста");
        } else {
            System.out.println("Итого " + sumProducts + " руб");
        }
    }

    public void saveJson(File jsonFile) throws IOException {
        try (FileWriter writer = new FileWriter(jsonFile, false)) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            writer.write(gson.toJson(this));
        }
    }

    public static Basket loadFromJsonFile(File jsonFile) throws IOException {
        if (jsonFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(jsonFile));) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                return gson.fromJson(br.readLine(), Basket.class);
            }
        }
        return null;
    }

    public void saveTxt(File textFile) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(textFile));) {
            StringBuilder s = new StringBuilder();

            for (int i = 0; i < prodName.length; i++) {
                s.append(prodName[i] + " ");
            }
            bw.write(s.toString());
            bw.newLine();
            s.setLength(0);

            for (int i = 0; i < prices.length; i++) {
                s.append(prices[i] + " ");
            }
            bw.write(s.toString());
            bw.newLine();
            s.setLength(0);

            for (int i = 0; i < prodAmount.length; i++) {
                s.append(prodAmount[i] + " ");
            }
            bw.write(s.toString());
        }
    }

    static Basket loadFromTxtFile(File textFile) throws IOException {
        if (textFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(textFile));) {

                String[] products = br.readLine().strip().split(" ");

                String[] pricesStr = br.readLine().strip().split(" ");
                int[] prices = new int[pricesStr.length];

                for (int i = 0; i < prices.length; i++) {
                    prices[i] = Integer.parseInt(pricesStr[i]);
                }

                Basket basket = new Basket(products, prices);

                String[] amountsStr = br.readLine().strip().split(" ");

                for (int i = 0; i < amountsStr.length; i++) {
                    basket.prodAmount[i] = Integer.parseInt(amountsStr[i]);
                }
                return basket;
            }
        }
        return null;
    }

    public String[] getProdName() {
        return prodName;
    }

    public int[] getPrices() {
        return prices;
    }
}
