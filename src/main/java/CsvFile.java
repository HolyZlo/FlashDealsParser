import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFile {
    private static final String CSV_FILE_NAME = "flashdeals_" + System.currentTimeMillis() + ".csv";
    private static String headers = "productId,sellerId,oriMinPrice,oriMaxPrice,promotionId,startTime,endTime,phase," +
            "productTitle,minPrice,maxPrice,discount,orders,productImage,productDetailUrl," +
            "shopUrl,totalTranpro3,productPositiveRate,productAverageStar,itemEvalTotalNum" + System.lineSeparator();
    private static FileWriter out;

    public static String getCsvFileName() {
        return CSV_FILE_NAME;
    }

    static {
        try {
            out = new FileWriter(CSV_FILE_NAME,true);
            out.write(headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCSVFile(List<Product> products) throws IOException {
        int i =1;
        products.forEach(product -> {
            try {
                out.write(product.convertToCsvString() + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        out.flush();
    }
}
