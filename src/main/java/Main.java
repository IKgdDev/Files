import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        Scanner scan = new Scanner(System.in);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));

        ConfigBlock load = new ConfigBlock(doc.getElementsByTagName("load").item(0));
        ConfigBlock save = new ConfigBlock(doc.getElementsByTagName("save").item(0));
        ConfigBlock log = new ConfigBlock(doc.getElementsByTagName("log").item(0));

        Basket basket = null;

        if (load.enabled == true) {
            if (load.format.equals("txt")) {
                try {
                    basket = Basket.loadFromTxtFile(new File(load.fileName));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (load.format.equals("json")) {
                try {
                    basket = Basket.loadFromJsonFile(new File(load.fileName));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        if (basket == null) {
            String[] products = {"Хлеб", "Сырок", "Шоколадка"};
            int[] prices = {40, 60, 100};
            basket = new Basket(products, prices);
        }

        ClientLog clientLog = new ClientLog();

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

                if (log.enabled == true) {
                    try {
                        clientLog.exportAsCSV(new File(log.fileName));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
            }

            switch (Integer.parseInt(input)) {
                case 1:
                    System.out.println("Введите номер товара и количество");
                    input = scan.nextLine();
                    String[] inputArr = input.split(" ");
                    int productNumber = Integer.parseInt(inputArr[0]);
                    int productCount = Integer.parseInt(inputArr[1]);

                    basket.addToCart(productNumber - 1, productCount);
                    clientLog.log(productNumber, productCount);

                    if (save.enabled == true) {
                        if (save.format.equals("txt")) {
                            try {
                                basket.saveTxt(new File(save.fileName));
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        if (save.format.equals("json")) {
                            try {
                                basket.saveJson(new File(save.fileName));
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    break;

                case 2:
                    basket.printCart();
                    break;
            }
        }
    }
}

class ConfigBlock {
    boolean enabled;
    String fileName;
    String format;

    public ConfigBlock(Node node) {
        NodeList listNode = node.getChildNodes();

        for (int i = 0; i < listNode.getLength(); i++) {
            Node currentNode = listNode.item(i);
            if (Node.ELEMENT_NODE == currentNode.getNodeType()) {
                if (currentNode.getNodeName().equals("enabled")) {
                    enabled = Boolean.parseBoolean(currentNode.getTextContent());
                }
                if (currentNode.getNodeName().equals("fileName")) {
                    fileName = currentNode.getTextContent();
                }
                if (currentNode.getNodeName().equals("format")) {
                    format = currentNode.getTextContent();
                }
            }
        }
    }
}