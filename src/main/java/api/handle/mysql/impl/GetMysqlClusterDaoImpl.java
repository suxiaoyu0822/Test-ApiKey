package api.handle.mysql.impl;

import api.handle.mysql.GetMysqlClusterDao;
import api.handle.util.PropertyUtil;

import java.sql.*;
import java.util.Properties;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-19 下午2:51
 */

public class GetMysqlClusterDaoImpl implements GetMysqlClusterDao {
    Connection conn = null;
    PreparedStatement statement = null;
    Properties properties = PropertyUtil.loadProperties("mysql.properties");
    public void connSQLCluster() {

        String url = properties.getProperty("mysql.url");
        String username = properties.getProperty("mysql.username");
        String password = properties.getProperty("mysql.password");
        // 加载驱动程序以连接数据库
        try {
            Class.forName("com.mysql.jdbc.Driver" );
            conn = DriverManager.getConnection( url,username, password );
            System.out.println("Connect the OwnMySQL Cluster to success!");
        }
        //捕获加载驱动程序异常
        catch ( ClassNotFoundException cnfex ) {
            System.err.println(
                    "The load JDBC/ODBC driver failed." );
            cnfex.printStackTrace();
        }
        //捕获连接数据库异常
        catch ( SQLException sqlex ) {
            System.err.println( "Unable to connect to the database" );
            sqlex.printStackTrace();
        }

    }

    public void CloseSQLCluster() {
        try {
            if (conn != null)
                conn.close();
            System.out.println("OwnMySQL Cluster has been closed");
        } catch (Exception e) {
            System.out.println("Closing the OwnMySQL Cluster exception:");
            e.printStackTrace();
        }

    }

//    public boolean insertSQL(RecordInfo recordInfo) {
//        try {
//            String sql;
//            sql = "insert into record_db(dataCenter,nationalId,stime,etime) values(?,?,?,?)";
//            statement = conn.prepareStatement(sql);
//            statement.setString(1,recordInfo.getDataCenter());
//            statement.setString(2,recordInfo.getNationalId());
//            statement.setString(3,recordInfo.getStime());
//            statement.setString(4,recordInfo.getEtime());
//            statement.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            System.out.println("Error when inserting the database:");
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("Error when inserting:");
//            e.printStackTrace();
//        }
//        return false;
//    }

    public boolean delSQL(int i) {
        try {
            String sql;
            sql = "delete from record_db where workId=?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1,i);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Database error when deleting:");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error when deleting:");
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSQL(int i, int a) {
        try {
            String sql;
            sql = "update record_db set state=? where workId=?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1,a);
            statement.setInt(2,i);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Database error when updating:");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error in update:");
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet selectSQL(String sql) {
        ResultSet rs = null;
//        RecordInfo recordInfo = new RecordInfo();
        try {
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Query error:");
            e.printStackTrace();
        }
        return rs;
    }
    @Override
    public ResultSet GetSQLCluster(String sql) {
        connSQLCluster();
        ResultSet resultSet = selectSQL(sql);
        return resultSet;
    }


    public static void main(String[] args) throws Exception {
        long apiversionid = 2482;
        String sql ="SELECT security_id FROM `video-cloud-base-platform`.api_version where id ='"+apiversionid+"'";
//        String sql ="SELECT access_control_id FROM `video-cloud-base-platform`.api_version where id = 2482";
//        String sql ="SELECT * FROM `video-cloud-base-platform`.api_version";
        GetMysqlClusterDaoImpl getMysqlClusterDao = new GetMysqlClusterDaoImpl();
        ResultSet rs= getMysqlClusterDao.GetSQLCluster(sql);
        while (rs.next()){
            System.out.println(rs.getString("security_id"));
        }
    }
}
