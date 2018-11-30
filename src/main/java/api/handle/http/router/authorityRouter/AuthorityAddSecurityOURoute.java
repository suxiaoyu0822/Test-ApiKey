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

import java.util.Date;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-31 下午7:39
 */

public class AuthorityAddSecurityOURoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityAddSecurityOURoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthorityAddSecurityOURoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }
    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        String dn = resourcePartEntity.getDn();
        Date date = new Date();
        String security_id = String.valueOf(date.getTime());
        System.out.println("-----------------------------------创建安全组织单元-----------------------------------");
        resourceLdap.connect();
        if (resourceLdap.isExistInLDAP("security-id="+security_id+","+dn)){
            System.out.println("[所创建安全组织单元已存在]");
            resourceLdap.close();
            String string = "所创建安全组织单元已存在,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        resourcePartEntity.setSecurity_id(security_id);
        resourceLdap.saveSecurityOU(resourcePartEntity);
        System.out.println("[成功创建安全组织单元]");
        String string = "成功创建安全组织单元！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        resourceLdap.close();
        return jsonObject;
    }
}
