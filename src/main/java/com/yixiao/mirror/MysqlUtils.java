package com.yixiao.mirror;
 
import org.junit.Test;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
/**
 * @date on 14:48 2018/8/16
 * @author yuyong
 * @describe 用于连接mysql数据库的工具类
 */
public class MysqlUtils {
 
    enum Constants{
        /**
         *定义枚举类型放入数据库连接的常量
         * Constants_Driver 驱动
         * Constants_Url 数据库路径
         * Constants_user 用户名
         * Constants_password 密码
         */
        Constants_Driver("com.mysql.jdbc.Driver"),
        Constants_Url("jdbc:mysql://localhost:3306/microsoft"),
        Constants_user("12345678"),
        Constants_password("12345678");
 
        private String description;
        Constants (String description){
            this.description = description;
        }
        public String getDescription(){
            return description;
        }
    }
 
    static {
        try {
            Class.forName(Constants.Constants_Driver.getDescription());
            System.out.println("数据库驱动注册成功...");
        } catch (ClassNotFoundException e) {
            System.out.println("驱动注册失败");
            e.printStackTrace();
        }
    }
 
    /**
     * ��ȡ���ݿ������
     * @return Connection
     */
    public static Connection getConnection(){
        try {
            Connection connection = DriverManager.getConnection(Constants.Constants_Url.getDescription(),
                    Constants.Constants_user.getDescription(),Constants.Constants_password.getDescription());
            System.out.println("数据库连接成功...");
            return connection;
        } catch (SQLException e) {
            System.out.println("获取连接失败");
            e.printStackTrace();
        }
        return null;
    }
 
    public static boolean closeConnection(Connection connection){
        if (null != connection){
            try {
                connection.close();
                System.out.println("数据库关闭成功...");
                return true;
            } catch (SQLException e) {
                System.out.println("关闭连接失败！！");
                e.printStackTrace();
            }
        }
        return false;
    }
 
    /**
     * Junit测试
     */
    @Test
    public void mysqlUtilsTest(){
        Connection connection = MysqlUtils.getConnection();
        MysqlUtils.closeConnection(connection);
    }
 
}
