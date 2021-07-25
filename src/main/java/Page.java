import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public abstract class Page {
    protected String mainUrl;
    protected String jsonUrl;
    protected HttpClient client;
    protected HttpEntity mainEntity;
    protected String resultMain;
    protected boolean validPage;
    public HttpEntity getMainEntity() {
        if (mainEntity == null) {
            try {
                mainEntity = client.execute(new HttpGet(mainUrl)).getEntity();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mainEntity;
    }

    public void setResultMain() {
        try {
            resultMain = EntityUtils.toString(mainEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
