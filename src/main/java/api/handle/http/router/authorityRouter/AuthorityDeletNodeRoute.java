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

import javax.naming.NamingException;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-14 下午4:44
 */

public class AuthorityDeletNodeRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityDeletNodeRoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthorityDeletNodeRoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }
    @Override
    public JSON handle(Request request, Response response) throws Exception{
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        String dn = resourcePartEntity.getDn();
        System.out.println("-----------------------------------删除资源鉴权节点（解绑）-----------------------------------");
        resourceLdap.connect();
        System.out.println("dn:"+dn);
//        System.out.println(resourceLdap.isExistInLDAP(dn));
        boolean b;
        try{
             b=resourceLdap.delete(dn);
        }catch (Exception e){
            System.out.println("所删除节点不存在，或ldap服务器异常，无法进行删除操作！");
            String string = "所删除节点不存在,或ldap服务器异常，无法进行删除操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            resourceLdap.close();
            return jsonObject;
        }

        if (!b){
            System.out.println("所删除节点不存在，或节点下存在文件，无法进行删除操作！");
            String string = "所删除节点不存在，或节点下存在文件，无法进行删除操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            resourceLdap.close();
            return jsonObject;
        }
        System.out.println("删除成功！");
        String string = "删除成功！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        resourceLdap.close();
        return jsonObject;
    }
}
