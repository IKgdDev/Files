import java.io.*;
import java.util.Scanner;

public class Basket implements Serializable {
    private String[] prodName;
    private int[] prices;
    private int[] prodAmount;

    private static final long serialVersionUID = 1L;

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

    public void saveTxt(File textFile) throws Exception {
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

    static Basket loadFromTxtFile(File textFile) throws Exception {
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
        } else {
            String[] products = {"Хлеб", "Сырок", "Шоколадка"};
            int[] prices = {40, 60, 100};
            Basket basket = new Basket(products, prices);
            return basket;
        }
    }

    public String[] getProdName() {
        return prodName;
    }

    public int[] getPrices() {
        return prices;
    }

    public void saveBin(File file) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        }
    }

    public static Basket loadFromBinFile(File file) throws Exception {
        if (file.exists()) {
            Basket basket = null;
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                basket = (Basket) ois.readObject();
            }
            return basket;
        } else {
            String[] products = {"Хлеб", "Сырок", "Шоколадка"};
            int[] prices = {40, 60, 100};
            Basket basket = new Basket(products, prices);
            return basket;
        }
    }
}
