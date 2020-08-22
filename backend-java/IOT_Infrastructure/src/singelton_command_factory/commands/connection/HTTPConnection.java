package singelton_command_factory.commands.connection;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class HTTPConnection implements Connection<JSONObject>{
    private String url;
    
    public HTTPConnection(String url) {
         this.url = url;
    }
    
    @Override
    public int send(JSONObject data) {
    	CloseableHttpClient httpClient = HttpClients.createDefault();
    	HttpPost post = new HttpPost(url);
        try {
			post.setEntity(new StringEntity(data.toString()));
		    HttpResponse httpResponse = httpClient.execute(post);	
		    return httpResponse.getStatusLine().getStatusCode();
		    
		} catch (ClientProtocolException e) {
		    return 503;
		} catch (IOException e) {
		    return 403;
		}
    }
}
