package api.handle.http.router.authorityRouter;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.dto.BodyJsonEntity;
import api.handle.dto.ResourcePartEntity;
import api.handle.util.ReturnJson;
import com.google.inject.Inject;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-14 下午4:59
 */

public class AuthorityUpdateNodeRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityUpdateNodeRoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthorityUpdateNodeRoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }
    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        String dn = resourcePartEntity.getDn();
//        String a = resourcePartEntity.getSecurity_name();
//        String d = resourcePartEntity.getDescription();
//        System.out.println(dn+a+d);
        System.out.println("-----------------------------------修改节点-----------------------------------");
        resourceLdap.connect();
        if (!resourceLdap.isExistInLDAP(dn)){
            System.out.println("所修改节点不存在,请重新操作！");
            String string = "所修改节点不存在,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            resourceLdap.close();
            return jsonObject;
        }
        boolean b = resourceLdap.updateNodes(resourcePartEntity);
        if (!b){
            System.out.println("修改节点失败,请重新操作！");
            String string = "修改节点失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            resourceLdap.close();
            return jsonObject;
        }
        System.out.println("修改节点成功！");
        String string = "修改节点成功！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        resourceLdap.close();
        return jsonObject;
    }
}
