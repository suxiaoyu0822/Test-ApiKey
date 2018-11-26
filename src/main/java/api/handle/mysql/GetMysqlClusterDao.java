package api.handle.mysql;


import java.sql.ResultSet;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-7-3 下午1:58
 */

public interface GetMysqlClusterDao {
    //获取数据库数据
    ResultSet GetSQLCluster(String sql);
}
