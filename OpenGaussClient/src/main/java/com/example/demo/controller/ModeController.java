package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.DataBaseUtils;
import com.example.demo.config.LocalDataBase;
import com.example.demo.pojo.Mode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mode")
public class ModeController {

    /**
     * 新增mode
     */
    @PostMapping("/addMode")
    public int addModel(@RequestBody Mode mode){
        if(StringUtils.isAnyBlank(mode.getModeName(),mode.getIp(),mode.getDataBaseName(),
                mode.getPassword(),mode.getUsername(),mode.getPort())){
            return -1;
        }else{
            mode.setModeId(UUID.randomUUID().toString().replaceAll("-",""));
            LocalDataBase.modeList.add(mode);
            return 1;
        }
    }

    /**
     * 查询mode列表
     */
    @GetMapping("/modeList")
    public List<Mode> getmodeList(){
        return LocalDataBase.modeList;
    }

    /**
     * 删除mode
     */
    @GetMapping("/deleteMode")
    public int deleteMode(String text){
        String[] s1 = text.split("_");
        String ip = s1[0];
        String db = s1[1];
        String mode = s1[2];
        List<Mode> modeList = LocalDataBase.modeList.stream().filter(s -> {
            if(StringUtils.equals(ip,s.getIp())&&StringUtils.equals(db,s.getDataBaseName())
                    &&StringUtils.equals(mode,s.getModeName())){
                return true;
            }else{
                return false;
            }
        }).collect(Collectors.toList());
        String modeId = modeList.get(0).getModeId();
        if(modeList.size() < 1){
            return -1;
        }else{
            for(int i=0;i<LocalDataBase.modeList.size();i++){
                if(StringUtils.equals(modeId,LocalDataBase.modeList.get(i).getModeId())){
                    LocalDataBase.modeList.remove(i);
                    break;
                }
            }
            return 1;
        }
    }

    //点击测试连接按钮，填充选择数据库下拉框
    @RequestMapping("/testConnection")
    @ResponseBody
    public int testConnection(String ip,String port,String username,String password,
                              String db,String mode) throws Exception{
        String url = "jdbc:postgresql://"+ip+":"+port+"/"+db+"?currentSchema="+mode;
        try {
            Connection connection = DataBaseUtils.getConnection(url, username, password);
        } catch (Exception e) {
            return -1;
        }
        return 1;

    }

    /**
     * 查询某一个mode
     */
    @RequestMapping("/getModeByText")
    @ResponseBody
    public Mode getModeByText(String text){
        String[] s1 = text.split("_");
        String ip = s1[0];
        String db = s1[1];
        String mode = s1[2];
        List<Mode> modeList = LocalDataBase.modeList.stream().filter(s -> {
            if(StringUtils.equals(ip,s.getIp())&&StringUtils.equals(db,s.getDataBaseName())
                    &&StringUtils.equals(mode,s.getModeName())){
                return true;
            }else{
                return false;
            }
        }).collect(Collectors.toList());
        return modeList.get(0);
    }

    //获取数据库下的模式下的表的列表
    @RequestMapping("/getTables")
    @ResponseBody
    public List<String> getDataBases(String text) throws Exception {
        String[] s1 = text.split("_");
        String ip = s1[0];
        String db = s1[1];
        String mode = s1[2];
        List<Mode> modeList = LocalDataBase.modeList.stream().filter(s -> {
            if(StringUtils.equals(ip,s.getIp())&&StringUtils.equals(db,s.getDataBaseName())
            &&StringUtils.equals(mode,s.getModeName())){
                return true;
            }else{
                return false;
            }
        }).collect(Collectors.toList());
        String port = modeList.get(0).getPort();
        String username = modeList.get(0).getUsername();
        String password = modeList.get(0).getPassword();

        String url = "jdbc:postgresql://"+ip+":"+port+"/"+db+"?currentSchema="+mode;
        Connection connection = DataBaseUtils.getConnection(url, username, password);

        String sql = "select tablename from pg_tables where schemaname = '"+mode+"'";
        List<JSONObject> jsonObjects = DataBaseUtils.executeSql(connection, sql);
        List<String> tablename = jsonObjects.stream().map(s -> s.getString("tablename")).collect(Collectors.toList());
        return tablename;
    }

    //执行sql
    @RequestMapping("executeSql")
    @ResponseBody
    public JSONObject executeSql(String text, String sql) throws Exception {
        String[] s1 = text.split("_");
        String ip = s1[0];
        String db = s1[1];
        String mode = s1[2];
        List<Mode> modeList = LocalDataBase.modeList.stream().filter(s -> {
            if(StringUtils.equals(ip,s.getIp())&&StringUtils.equals(db,s.getDataBaseName())
                    &&StringUtils.equals(mode,s.getModeName())){
                return true;
            }else{
                return false;
            }
        }).collect(Collectors.toList());
        String port = modeList.get(0).getPort();
        String username = modeList.get(0).getUsername();
        String password = modeList.get(0).getPassword();
        String url = "jdbc:postgresql://"+ip+":"+port+"/"+db+"?currentSchema="+mode;
        Connection connection = DataBaseUtils.getConnection(url, username, password);
        JSONObject result = new JSONObject();
        try {
            List<JSONObject> jsonObjects = DataBaseUtils.executeSql(connection, sql);
            result.put("code",0);
            result.put("list",jsonObjects);
        } catch (Exception e) {
            result.put("code",-1);
            result.put("errorMsg",e.getMessage());
        }
        return result;

    }


}
