import org.json.JSONObject;

import java.sql.Time;

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
        this.phase = post.has("phase") ? post.getInt("phase") : null;
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
        this.itemEvalTotalNum = post.has("itemEvalTotalNum") ? post.getInt("itemEvalTotalNum") : null;
    }

    public String convertToCsvString() {
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

}
