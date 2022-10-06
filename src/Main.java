import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[] products = {"Хлеб", "Сырок", "Шоколадка"};
        int[] prices = {40, 60, 100};
        int[] prodNum = new int[products.length];
        int sumProducts = 0;

        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i+1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        while (true) {

            System.out.println("Выберите товар и количество или введите `end`");
            String input = scan.nextLine();

            if ("end".equals(input)) {
                break;
            }

            String[] inputArr = input.split(" ");
            int productNumber = Integer.parseInt(inputArr[0]) - 1;
            int productCount = Integer.parseInt(inputArr[1]);

            prodNum[productNumber] += productCount;
        }

        System.out.println("Ваша корзина:");

        for (int i = 0; i < products.length; i++) {
            if (prodNum[i] != 0) {
                System.out.println(products[i] + " " + prodNum[i] + " шт " + prices[i] + " руб/шт "
                        + (prodNum[i] * prices[i]) + " руб в сумме");
                sumProducts += prodNum[i] * prices[i];
            }
        }
        System.out.println("Итого " + sumProducts + " руб");

    }
}