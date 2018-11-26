package api.handle.jwt.jwtldap;

import api.handle.dto.JWTEntity;
import javax.naming.NamingException;
import java.util.List;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-22 下午3:20
 */

public interface JwtLdap {
    void connect() throws NamingException;
    void saveToken(JWTEntity jwtEntity) throws NamingException;
    boolean delete(String dn) throws NamingException;

    boolean updateJwt(String dn,String iat,String token);

    String find(String dn, String searchFilter, String retAttrs) throws NamingException;

    List FindJwtAttributes(String dn, String searchFilter, String returnedAttrs[]) throws NamingException;

    void close() throws NamingException;
    boolean isExistInLDAP(String dn);
}
