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

import javax.naming.NamingException;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-22 上午10:42
 */

public class AuthorityAddRuleRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityAddRuleRoute.class);
    private ResourceLdap resourceLdap;
    @Inject
    public AuthorityAddRuleRoute(ResourceLdap resourceLdap){
        this.resourceLdap=resourceLdap;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        ResourcePartEntity resourcePartEntity = bodyJsonEntity.getBodyJsonEntity(ResourcePartEntity.class,request);
        System.out.println("-----------------------------------添加规则-----------------------------------");
        resourceLdap.connect();
        try {
            int size = resourceLdap.FindAllAttribut(resourcePartEntity).size();
//            System.out.println("size:"+size);
            //查询dn下规则数量，大于10禁止添加，小于10正常添加。
            if (size>=10){
                System.out.println("此安全组规则超过十条,添加规则失败！");
                String string = "此安全组规则超过十条,添加规则失败！";
                JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
                return jsonObject;
            }
            resourceLdap.saveRule(resourcePartEntity);
        } catch (NamingException e) {
            e.printStackTrace();
            String string = "添加规则失败,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            return jsonObject;
        }
        System.out.println("[成功添加规则]");
        resourceLdap.close();
        String string = "成功添加规则";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        return jsonObject;
    }
}
