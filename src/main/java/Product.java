import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private Long productId;
    private Long sellerId;
    private String oriMinPrice;
    private String oriMaxPrice;
    private Long promotionId;
    private Time startTime;
    private Time endTime;
    private int phase;
    private String productTitle;
    private String minPrice;
    private String maxPrice;
    private String discount;
    private String orders;
    private String productImage;
    private String productDetailUrl;
    private String shopUrl;
    private String totalTranpro3;
    private String productPositiveRate;
    private String productAverageStar;
    private int itemEvalTotalNum;
    private List<String> sku;
    private String availability;
    private String detailCharacteristics;

    public void setSku(String skuValue) {
        this.sku.add(skuValue);
    }


    public Product() {
    }

    public Product(JSONObject post) {
        this.productId = post.has("productId") ? post.getLong("productId") : null;
        this.sellerId = post.has("sellerId") ? post.getLong("sellerId") : null;
        this.oriMinPrice = post.has("oriMinPrice") ? post.getString("oriMinPrice") : null;
        this.oriMaxPrice = post.has("oriMaxPrice") ? post.getString("oriMaxPrice") : null;
        this.promotionId = post.has("promotionId") ? post.getLong("promotionId") : null;
        this.startTime = post.has("startTime") ? new Time(post.getLong("startTime")) : null;
        this.endTime = post.has("endTime") ? new Time(post.getLong("endTime")) : null;
        this.phase = post.has("phase") ? post.getInt("phase") : 0;
        this.productTitle = post.has("productTitle") ? post.getString("productTitle") : null;
        this.minPrice = post.has("minPrice") ? post.getString("minPrice") : null;
        this.maxPrice = post.has("maxPrice") ? post.getString("maxPrice") : null;
        this.discount = post.has("discount") ? post.getString("discount") : null;
        this.orders = post.has("orders") ? post.getString("orders") : null;
        this.productImage = post.has("productImage") ? post.getString("productImage") : null;
        this.productDetailUrl = post.has("productDetailUrl") ? post.getString("productDetailUrl") : null;
        this.shopUrl = post.has("shopUrl") ? post.getString("shopUrl") : null;
        this.totalTranpro3 = post.has("totalTranpro3") ? post.getString("totalTranpro3") : null;
        this.productPositiveRate = post.has("productPositiveRate") ? post.getString("productPositiveRate") : null;
        this.productAverageStar = post.has("productAverageStar") ? post.getString("productAverageStar") : null;
        this.itemEvalTotalNum = post.has("itemEvalTotalNum") ? post.getInt("itemEvalTotalNum") : 0;
        this.sku = new ArrayList<>();
    }

    public void addDetailInfo(HttpClient client) {
        DetailPage detailPage = new DetailPage(this.productDetailUrl, client,this);
    }

    public String convertMainToCsvString() {
        return "\"" + productId + "\"" +
                ",\"" + sellerId + "\"" +
                ",\"" + oriMinPrice + "\"" +
                ",\"" + oriMaxPrice + "\"" +
                ",\"" + promotionId + "\"" +
                ",\"" + startTime + "\"" +
                ",\"" + endTime + "\"" +
                ",\"" + phase + "\"" +
                ",\"" + productTitle + "\"" +
                ",\"" + minPrice + "\"" +
                ",\"" + maxPrice + "\"" +
                ",\"" + discount + "\"" +
                ",\"" + orders + "\"" +
                ",\"" + productImage + "\"" +
                ",\"" + productDetailUrl + "\"" +
                ",\"" + shopUrl + "\"" +
                ",\"" + totalTranpro3 + "\"" +
                ",\"" + productPositiveRate + "\"" +
                ",\"" + productAverageStar + "\"" +
                ",\"" + itemEvalTotalNum + "\"";
    }

    public String convertDetailToCsvString() {
        StringBuilder sb = new StringBuilder();
        sku.forEach(s -> sb.append(s).append("|"));
        return ",\"" + sb.toString() + "\"" +
                ",\"" + availability + "\"" +
                ",\"" + detailCharacteristics + "\"";
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setDetailCharacteristics(String detailCharacteristics) {
        this.detailCharacteristics = detailCharacteristics;
    }
}
