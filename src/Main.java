import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        File file = new File("basket.txt");

        Basket basket = null;
        try {
            basket = Basket.loadFromTxtFile(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < basket.getProdName().length; i++) {
            System.out.println((i + 1) + ". " + basket.getProdName()[i] + " " + basket.getPrices()[i] + " руб/шт");
        }

        while (true) {
            System.out.println("Введите пункт меню или 'end' для выхода:");
            System.out.println("1. Заполнить корзину");
            System.out.println("2. Показать корзину");

            String input = scan.nextLine();

            if ("end".equals(input)) {
                break;
            }

            switch (Integer.parseInt(input)) {
                case 1:
                    System.out.println("Введите номер товара и количество");
                    input = scan.nextLine();
                    String[] inputArr = input.split(" ");
                    int productNumber = Integer.parseInt(inputArr[0]) - 1;
                    int productCount = Integer.parseInt(inputArr[1]);

                    basket.addToCart(productNumber, productCount);

                    try {
                        basket.saveTxt(file);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    basket.printCart();
                    break;
            }
        }
    }
}