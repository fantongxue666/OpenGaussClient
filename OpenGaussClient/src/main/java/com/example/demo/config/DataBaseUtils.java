package com.example.demo.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtils {
    //获取数据库连接
    //http://localhost:8081/mode/getDataBases?ip=192.168.8.45&port=5432&username=test&password=Gauss_234
    public static Connection getConnection(String url,String username,String pwd) throws Exception {
        //获取连接
        Class.forName("org.postgresql.Driver");//注册驱动
        Connection connection = DriverManager.getConnection(url,username,pwd);
        return connection;
    }

    //执行sql
    public static List<JSONObject> executeSql(Connection connection,String sql) throws Exception {

        Statement statement = connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);
        //列数
        int columnCount = resultSet.getMetaData().getColumnCount();
        List<JSONObject> result = new ArrayList<>();
        while (resultSet.next()){
            JSONObject jsonObject = new JSONObject();
            for(int i = 0;i<columnCount;i++){
                String columnName = resultSet.getMetaData().getColumnName(i+1);
                jsonObject.put(columnName,resultSet.getObject(i+1));
            }
            result.add(jsonObject);
        }
        connection.close();
        return result;
    }


}