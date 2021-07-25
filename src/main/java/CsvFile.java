import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFile {
    private static final String CSV_FILE_NAME = "flashdeals_" + System.currentTimeMillis() + ".csv";
    private static final String headers = "productId,sellerId,oriMinPrice,oriMaxPrice,promotionId,startTime,endTime,phase," +
            "productTitle,minPrice,maxPrice,discount,orders,productImage,productDetailUrl," +
            "shopUrl,totalTranpro3,productPositiveRate,productAverageStar,itemEvalTotalNum";
    private static FileWriter out;
    private String headersDetail;

    public void setHeadersDetail(String headersDetail) {
        this.headersDetail = headersDetail;
    }

    public static String getCsvFileName() {
        return CSV_FILE_NAME;
    }

    public void createCSVFile(List<Product> products) {
        try {
            out = new FileWriter(CSV_FILE_NAME, true);
            if (headersDetail != null) {
                out.write(headers + "," + headersDetail + System.lineSeparator());
            } else {
                out.write(headers + System.lineSeparator());
            }
            products.forEach(product -> {
                try {
                    out.write(product.convertMainToCsvString() + (headersDetail != null ? product.convertDetailToCsvString() : "") + System.lineSeparator());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
