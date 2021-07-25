import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("")
public class DetailPage extends Page {
    public DetailPage(String mainUrl, HttpClient client, Product product) {
        this.mainUrl = mainUrl.indexOf("https:") > 0 ? mainUrl : "https:" + mainUrl;
        this.client = client;
        if (getMainEntity() != null) {
            this.setResultMain();
            this.getDetailCharacteristics(product);
            this.getSkuCharacteristics(product);
            this.getAvailability(product);
        }
    }


    public void getDetailCharacteristics(Product product) {
        if (resultMain != null) {
            int searchIndex = resultMain.indexOf("\"props\":");
            if (searchIndex > 0) {
                String detailCharacteristics = resultMain.substring(searchIndex + 8, resultMain.indexOf("]", searchIndex)+1);
                JSONArray posts = new JSONArray(detailCharacteristics);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.getJSONObject(i);
                    sb.append(post.getString("attrName")).append("-").append(post.getString("attrValue")).append("|");
                }
                product.setDetailCharacteristics(sb.toString());
            }


        }
    }

    public void getSkuCharacteristics(Product product) {
        if (resultMain != null) {
            int startIndex = 0;
            while (true) {
                if (resultMain.indexOf("\"skuPropertyId\":", startIndex) > 0) {
                    startIndex = resultMain.indexOf("\"skuPropertyId\":", startIndex) + 1;
                    String skuPropertyId = resultMain.substring(startIndex + 15,
                            resultMain.indexOf("\"skuPropertyName\"", startIndex) - 1);
                    String skuPropertyName = resultMain.substring(resultMain.indexOf("\"skuPropertyName\"", startIndex) + 19,
                            resultMain.indexOf("\"skuPropertyValues\"", startIndex) - 2);
                    String skuPropertyValues = resultMain.substring(resultMain.indexOf("\"skuPropertyValues\"", startIndex) + 20,
                            resultMain.indexOf("}]", startIndex) + 2);
                    JSONArray posts = new JSONArray(skuPropertyValues);
                    StringBuilder sb = new StringBuilder();
                    sb.append("ID - ").append(skuPropertyId).append(" |Name - ").append(skuPropertyName).append("| Значения - ");
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.getJSONObject(i);
                        sb.append(post.getString("propertyValueDisplayName")).append("|");
                    }
                    product.setSku(sb.toString());
                } else {
                    break;
                }
            }
        }
    }

    public void getAvailability(Product product) {
        if (resultMain != null) {
            int searchIndex = resultMain.indexOf("totalAvailQuantity\":");
            if (searchIndex > 0) {
                String availability = resultMain.substring(searchIndex + 20, resultMain.indexOf("}", searchIndex));
                product.setAvailability(availability);
            }
        }
    }

}



