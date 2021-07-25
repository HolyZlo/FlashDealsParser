import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends Page {
    private static final List<Product> products = new ArrayList<>();
    private static String widgetId = null;
    private static int countReplay = 1;
    private static int pageNumber = 0;
    private static String postback = null;

    public MainPage(String mainUrl, String jsonUrl, HttpClient client) {
        this.mainUrl = mainUrl;
        this.jsonUrl = jsonUrl;
        this.client = client;

        if (getMainEntity() != null) {
            this.setResultMain();
            int indexWidget = resultMain.indexOf("data-widgetid=\"");
            if (indexWidget > 0) {
                validPage = true;
                widgetId = resultMain.substring(indexWidget + 15, indexWidget + 22);
            } else {
                System.out.println("Ошибка страницы - " + mainUrl);
            }
        }
    }

    public void getDataProduct(){
        Long timeStart = System.currentTimeMillis();
        try {
            this.putProductList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long timeStop = System.currentTimeMillis();
        System.out.println("Время выполнения - " + (timeStop - timeStart) / 1000d + " сек., всего загружено - " + (MainPage.getProducts().size()) + " товаров");
    }
    public void putProductList() throws IOException {
        while ((products.size() / countReplay) < 100) {
            String resultResponse = new BasicResponseHandler().handleResponse(
                    client.execute(new HttpGet(this.jsonUrl + "widget_id=" + widgetId + "&limit=12"
                            + (postback == null ? "" : "&postback=" + postback + "&offset=" + pageNumber * 12))));
            if (resultResponse.indexOf("\"success\":true") < 1) {
                System.out.println("Ошибка в выполнении запроса!");
                break;
            }
            JSONArray posts = new JSONArray(resultResponse.substring(resultResponse.indexOf("["), resultResponse.indexOf("]") + 1));
            int indexPostback = resultResponse.indexOf("postback");
            if (indexPostback > 0) {
                postback = resultResponse.substring(indexPostback + 11, resultResponse.indexOf("\",\"pin\":\"gps"));
            }
            parseArrayJsonObject(posts);
            pageNumber++;
        }
        countReplay++;
    }

    private void parseArrayJsonObject(JSONArray posts) {
        for (int i = 0; i < posts.length(); i++) {
            if (products.size() / countReplay < 100) {
                JSONObject post = posts.getJSONObject(i);
                products.add(new Product(post));
            }
        }
    }

    public static List<Product> getProducts() {
        return products;
    }
}
