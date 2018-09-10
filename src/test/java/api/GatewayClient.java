package api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-10 下午5:06
 */

public class GatewayClient {
    public static void main(String[] args) throws IOException {
        //时效性
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTM2NTcyNDMwLCJzdWIiOiJUZXN0IHNvbXRoaW5nIiwiaXNzIjoic3h5IiwiZXhwIjoxNTM2NTcyNTMwfQ.kz4Mwsd4PcFN5_vBD9xiqIa-fk39qgAOjWFBcqKbPBk"+"~/api/gateway/judge";
        String url = "http://localhost:8083/api/gateway/judge";
        Client client = Client.create();
        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        queryParams.add("API_KEY", token);
        WebResource getParam = client.resource(url);
        String getS=getParam.queryParams(queryParams).get(String.class);
        System.out.println(getS);
    }
}
