import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MainApp {

    public static void main(String[] args) {

        HttpClient client = HttpClientBuilder.create().build();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        MainPage mainPage = new MainPage("https://flashdeals.aliexpress.com/ru.htm?"
                , "https://gpsfront.aliexpress.com/getRecommendingResults.do?", client);
        CsvFile csvFile = new CsvFile();
        if (args.length == 0) {
            Long timeStart = System.currentTimeMillis();
            mainPage.getDataProduct();
            MainPage.getProducts().forEach(product -> product.addDetailInfo(client));
            csvFile.setHeadersDetail("skuCharacteristics,totalAvailQuantity,detailCharacteristics");
            csvFile.createCSVFile(MainPage.getProducts());
            System.out.println("Файл с данными сохранен с именем - " + CsvFile.getCsvFileName());
            Long timeStop = System.currentTimeMillis();
            System.out.println("Время выполнения - " + (timeStop - timeStart) / 1000d + " сек., обработано - "
                    + (MainPage.getProducts().size()) + " товаров");
            System.exit(0);

        }

        if (Arrays.asList(args).contains("count")) {
            while (true) {
                System.out.print("Хотите загрузить 100 товаров? Введите - y/n: ");
                try {
                    String userResponse = br.readLine();
                    if (userResponse.equalsIgnoreCase("n")) {
                        break;
                    } else if (userResponse.equalsIgnoreCase("y")) {
                        mainPage.getDataProduct();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (Arrays.asList(args).contains("detail")) {
            while (true) {
                System.out.print("Хотите загрузить дополнительную информацию о товарах?" +
                        " Примерное время выполнения - " + MainPage.getProducts().size() / 2 + " сек." +
                        " Введите - y/n: ");
                try {
                    String userResponse = br.readLine();
                    if (userResponse.equalsIgnoreCase("y")) {
                        Long timeStart = System.currentTimeMillis();
                        MainPage.getProducts().forEach(product -> product.addDetailInfo(client));
                        csvFile.setHeadersDetail("skuCharacteristics,totalAvailQuantity,detailCharacteristics");
                        Long timeStop = System.currentTimeMillis();
                        System.out.println("Время загрузки дополнительной информации - " + (timeStop - timeStart) / 1000d + " сек.");
                        break;
                    } else if (userResponse.equalsIgnoreCase("n")) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        csvFile.createCSVFile(MainPage.getProducts());
        System.out.println("Файл с данными сохранен с именем - " + CsvFile.getCsvFileName());
        System.exit(0);
    }
}
