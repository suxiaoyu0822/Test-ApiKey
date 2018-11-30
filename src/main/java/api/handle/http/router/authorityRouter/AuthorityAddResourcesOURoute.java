package api.handle.http.router.authorityRouter;

import api.handle.authority.resourcesldap.ResourceLdap;
import api.handle.dto.BodyJsonEntity;
import api.handle.dto.ResourcePartEntity;
import api.handle.util.ReturnJson;
import com.google.inject.Inject;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-31 下午7:39
 */

public class AuthorityAddResourcesOURoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityAddResourcesOURoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthorityAddResourcesOURoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }
    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        String dn = resourcePartEntity.getDn();
//        System.out.println("************"+dn);
        String resource_id = resourcePartEntity.getResources_id();
//        System.out.println("************"+resource_id);
        resourcePartEntity.setResources_id(resource_id);
        System.out.println("-----------------------------------创建资源组织单元-----------------------------------");
        resourceLdap.connect();
        //创建创建组织
        if (resourceLdap.isExistInLDAP("resources-id="+resource_id+","+dn)){
            System.out.println("[所创建资源组织单元已存在]");
            resourceLdap.close();
            String string = "所创建资源组织单元已存在,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        try {
            resourceLdap.saveResourcesOU(resourcePartEntity);
        }catch (Exception e){
            System.out.println("[所创建资源组织单元已存在，或者绑定失败！]");
            resourceLdap.close();
            String string = "所创建资源组织单元已存在,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        System.out.println("[成功创建资源组织单元]");
        String string = "成功创建资源组织单元！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        resourceLdap.close();
        return jsonObject;
    }
}
