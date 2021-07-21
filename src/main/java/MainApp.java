import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainApp {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        CsvFile csvFile = new CsvFile();
        Page page = null;
        try {
            page = new Page();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Long timeStart = System.currentTimeMillis();
                page.putProductList();
                Long timeStop = System.currentTimeMillis();
                System.out.println("Время выполнения - " + (timeStop - timeStart) / 1000d + " сек.");
                System.out.println("Всего загружено - "+ (page.getProducts().size()) + " товаров");
                System.out.print("Хотите загрузить еще? Введите - y/n: " );

                if (br.readLine().toLowerCase().equals("n")){
                    csvFile.createCSVFile(Page.getProducts());
                    System.out.println("Файл с данными сохранен с именем - " + CsvFile.getCsvFileName());
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        System.exit(0);

    }
}