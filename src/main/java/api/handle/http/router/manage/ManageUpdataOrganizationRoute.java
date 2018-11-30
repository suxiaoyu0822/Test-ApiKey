package api.handle.http.router.manage;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.OrganizationEntity;
import api.handle.dto.UserEntity;
import api.handle.ldap.Ldap;
import api.handle.ldap.impl.LdapImpl;
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
 * @Date: Created in 18-10-9 上午9:32
 */

public class ManageUpdataOrganizationRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageUpdataOrganizationRoute.class);
    private Ldap ldap;
    @Inject
    public ManageUpdataOrganizationRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        OrganizationEntity organizationEntity= bodyJsonEntity.getBodyJsonEntity(OrganizationEntity.class,request);
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String neworganization = organizationEntity.getNeworganization();
        String dn = userEntity.getDn();
        System.out.println("-----------------------------------修改组织-----------------------------------");

        ldap.connect();
        //修改组织
        if (!ldap.isExistInLDAP(dn)){
            System.out.println("[所修改组织不存在]");
            String string = "所修改组织不存在,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            ldap.close();
            return jsonObject;
        }
        String newo= "o="+neworganization;
        ldap.updateNodes(dn,newo);
        ldap.close();
        System.out.println("[成功修改组织]");
        String string = "成功修改组织！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        return jsonObject;
    }
}
