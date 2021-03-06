package api.handle.http.router;

import api.handle.dto.BodyJsonEntity;
import api.handle.dto.UserEntity;
import api.handle.ldap.Ldap;
import api.handle.util.GetDigestUtil;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-10 下午4:52
 */

public class UserRegisterRoute implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegisterRoute.class);
    private Ldap ldap;
    @Inject
    UserRegisterRoute(Ldap ldap){
        this.ldap=ldap;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //用户注册认证
        BodyJsonEntity bodyJsonEntity = new BodyJsonEntity();
        String resp =bodyJsonEntity.getHeaders(request,"Authorization",false);
        System.out.println("Authorization: "+resp);
        UserEntity userEntity= bodyJsonEntity.getBodyJsonEntity(UserEntity.class,request);
        String username = userEntity.getCn();
        String password = userEntity.getUserPassword();
//        String sex = userEntity.getMan();
        String address = null;
        String telephoneNumber = userEntity.getTelephoneNumber();
        String organization = userEntity.getOrganization();
        String organizationalUnit = userEntity.getOrganizationalUnit();
        String email = userEntity.getMail();
        String company = null;
        String clientNonce = userEntity.getClientNonce();
        System.out.println("clientNonce: "+clientNonce);
        String uri = userEntity.getUri();
        String reaml = userEntity.getReaml();
        LOGGER.info("Authorization: {}",resp);
        Map<Object, Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
//        map.put("sex",sex);
        map.put("address",address);
        map.put("organization",organization);
        map.put("email",email);
        map.put("company",company);
        map.put("clientNonce",clientNonce);
        map.put("uri",uri);
        map.put("reaml",reaml);
        //验证用户名和密码是否正确
        GetDigestUtil getDigestUtil = new GetDigestUtil();
        String respOk = getDigestUtil.GetResponse(map);
        System.out.println("AuthorizationOK:"+respOk);
        if (!respOk.equals(resp)){
            return "fail";
        }
        //存储用户到ldap
        String o=organization;
        String ou=organizationalUnit;
        String sn= username.substring(1);
        String cn= username;
        //等等
        ldap.connect();
        String dn = ",dc=registry,dc=baotoucloud,dc=com";
        while (!ldap.isExistInLDAP("o="+o+dn)){
            ldap.addO(o);
        }
        while (!ldap.isExistInLDAP("ou="+ou+",o="+o+dn)){
            ldap.addOU(o,ou);
        }
//        ldap.addEntry(o,ou,sn,cn,password,company,address,email,telephoneNumber);
        ldap.close();
        System.out.println("add ok!");
        return "Success";
    }
}
