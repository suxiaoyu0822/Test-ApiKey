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

public class AuthorityUpdateRuleRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityUpdateRuleRoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthorityUpdateRuleRoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        String dn = resourcePartEntity.getDn();
        System.out.println("-----------------------------------修改规则-----------------------------------");

        resourceLdap.connect();
//        if (!resourceLdap.isExistInLDAP(dn)){
//            System.out.println("所修改规则不存在,请重新操作！");
//            String string = "所修改规则不存在,请重新操作！";
//            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
//            return jsonObject;
//        }
        boolean b = resourceLdap.updateRule(resourcePartEntity);
        if (!b){
            System.out.println("修改规则失败,请重新操作！");
            String string = "修改规则失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            resourceLdap.close();
            return jsonObject;
        }
        System.out.println("修改规则成功！");
        String string = "修改规则成功！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        resourceLdap.close();
        return jsonObject;
    }
}
