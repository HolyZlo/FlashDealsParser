import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Page {
    private static final String MAIN_URL = "https://flashdeals.aliexpress.com/ru.htm?";
    private static final String JSON_URL = "https://gpsfront.aliexpress.com/getRecommendingResults.do?";
    private final HttpClient client = HttpClientBuilder.create().build();
    private final HttpEntity entity = client.execute(new HttpGet(MAIN_URL)).getEntity();
    private static List<Product> products = new ArrayList<>();
    private static String widgetId = null;
    private static int countReplay = 1;
    private static int pageNumber = 0;
    private static String postback = null;

    public Page() throws IOException {
        if (entity != null) {
            String result = EntityUtils.toString(entity);
            int indexWidget = result.indexOf("data-widgetid=\"");
            if (indexWidget > 0) {
                widgetId = result.substring(indexWidget + 15, indexWidget + 22);
            } else {
                System.out.println("Ошибка страницы https://flashdeals.aliexpress.com/ru.htm?");
            }
        }
    }

    public static List<Product> getProducts() {
        return products;
    }

    public void putProductList() throws IOException {
        while ((products.size() / countReplay) < 100) {
            String tempResult = new BasicResponseHandler().handleResponse(
                    client.execute(new HttpGet(JSON_URL + "widget_id=" + widgetId + "&limit=12"
                            + (postback == null ? "" : "&postback=" + postback + "&offset=" + pageNumber * 12))));
            if (tempResult.indexOf("\"success\":true") < 1) {
                System.out.println("Ошибка в выполнении запроса!");
                break;
            }
            String resultJson = tempResult.substring(tempResult.indexOf("["), tempResult.indexOf("]") + 1);
            JSONArray posts = new JSONArray(resultJson);
            int indexPostback = tempResult.indexOf("postback");
            if (indexPostback > 0) {
                postback = tempResult.substring(indexPostback + 11, tempResult.indexOf("\",\"pin\":\"gps"));
            }
            parseArrayJsonObject(posts);
            pageNumber++;
        }
        countReplay++;
    }


    private void parseArrayJsonObject(JSONArray posts) {
        for (int i = 0; i < posts.length(); i++) {
            if (products.size()/countReplay < 100) {
                JSONObject post = posts.getJSONObject(i);
                products.add(new Product(post));
            }
        }
    }
}
