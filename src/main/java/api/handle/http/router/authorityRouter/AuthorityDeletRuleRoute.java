package api.handle.http.router.authorityRouter;

import api.handle.authority.acl.Acl;
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
 * @Date: Created in 18-10-22 上午10:43
 */

public class AuthorityDeletRuleRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityDeletRuleRoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthorityDeletRuleRoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        String dn = resourcePartEntity.getDn();
        System.out.println("-----------------------------------删除规则-----------------------------------");
        resourceLdap.connect();
//        System.out.println("dn:"+dn);
//        System.out.println(resourceLdap.isExistInLDAP(dn));
        if (!resourceLdap.isExistInLDAP(dn)){
            System.out.println("所删除规则不存在，无法进行删除操作！");
            String string = "所删除规则不存在，无法进行删除操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        resourceLdap.delete(dn);
        System.out.println("删除成功！");
        String string = "删除成功！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        resourceLdap.close();
        return jsonObject;
    }
}
