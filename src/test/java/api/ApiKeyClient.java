package api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-10 下午3:07
 */

public class ApiKeyClient {
    public static void main(String[] args)
    {
        Client client = Client.create();
        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        String somthing = "121212121";
        queryParams.add("API_KEY", somthing);
        String url = "http://localhost:8083/api/key/do";
        WebResource getParam = client.resource(url);
        String getS=getParam.queryParams(queryParams).get(String.class);
        System.out.println(getS);
    }

}
