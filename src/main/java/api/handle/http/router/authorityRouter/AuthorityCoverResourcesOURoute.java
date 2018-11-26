package api.handle.http.router.authorityRouter;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.dto.BodyJsonEntity;
import api.handle.dto.ResourcePartEntity;
import api.handle.util.ReturnJson;
import com.google.inject.Inject;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-26 上午10:18
 */

public class AuthorityCoverResourcesOURoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityCoverResourcesOURoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthorityCoverResourcesOURoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        String dn = resourcePartEntity.getDn();
        String resource_id = resourcePartEntity.getResources_id();
        System.out.println("-----------------------------------绑定覆盖-----------------------------------");
        resourceLdap.connect();
        if (resourceLdap.isExistInLDAP("resources-id="+resource_id+","+dn)){
            //绑定存在！根据dn进行安全组id的修改
            try{
                resourceLdap.updateResource(resourcePartEntity);
            }catch (Exception e){
                System.out.println("[ldap服务器异常,绑定覆盖失败,请重新操作！！]");
                String string = "ldap服务器异常,绑定覆盖失败,请重新操作！";
                JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
                return jsonObject;
            }
            System.out.println("[绑定覆盖成功！]");
            String string = "绑定覆盖成功！";
            JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
            resourceLdap.close();
            return jsonObject;
        }
        //绑定不存在！根据dn进行添加绑定
        try {
            resourceLdap.saveResourcesOU(resourcePartEntity);
        }catch (Exception e){
            System.out.println(e);
            System.out.println("[ldap服务器异常,添加绑定失败,请重新操作！]");
            String string = "ldap服务器异常,添加绑定失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        System.out.println("[成功创建资源组织单元]");
        String string = "添加绑定成功！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        resourceLdap.close();
        return jsonObject;

    }
}
