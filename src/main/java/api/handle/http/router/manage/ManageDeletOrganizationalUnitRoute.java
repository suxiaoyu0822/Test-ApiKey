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
 * @Date: Created in 18-10-9 上午9:33
 */

public class ManageDeletOrganizationalUnitRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageDeletOrganizationalUnitRoute.class);
    private Ldap ldap;
    @Inject
    public ManageDeletOrganizationalUnitRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public JSON handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String dn =userEntity.getDn();
        System.out.println("-----------------------------------删除组织单元-----------------------------------");

        ldap.connect();
        //删除组织单元
        if (!ldap.isExistInLDAP(dn)){
            System.out.println("[所删除组织单元不存在]");
            String string = "所删除组织单元不存在,请重新操作！";
            JSONObject jsonObject = ReturnJson.ReturnFailJson(string);
            ldap.close();
            return jsonObject;
        }
        ldap.delete(dn);
        ldap.close();
        System.out.println("[成功删除组织单元]");
        String string = "成功删除组织单元！";
        JSONObject jsonObject = ReturnJson.ReturnSuccessJson(string);
        return jsonObject;
    }
}
