package api.handle.http.router.manage;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.OrganizationEntity;
import api.handle.dto.UserEntity;
import api.handle.ldap.Ldap;
import api.handle.ldap.impl.LdapImpl;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-10-9 上午9:32
 */

public class ManageSearchOrganizationRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageSearchOrganizationRoute.class);
    private Ldap ldap;
    @Inject
    public ManageSearchOrganizationRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String dn =userEntity.getDn();
        ldap.connect();
        System.out.println("查询根下的o");
        String Attribut = "o";
        List list=ldap.searchForAttribut(Attribut,dn);
        for (int i=0;i<list.size();i++) {
            System.out.println(list.get(i));
        }
        return list.toString();
    }
}
