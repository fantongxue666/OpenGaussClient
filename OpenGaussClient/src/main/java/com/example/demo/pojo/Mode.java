package com.example.demo.pojo;

public class Mode {
    public Mode(String ip, String port, String username, String password, String dataBaseName, String modeName) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.dataBaseName = dataBaseName;
        this.modeName = modeName;
    }
    private String modeId;
    private String ip;
    private String port;
    private String username;
    private String password;
    private String dataBaseName;
    private String modeName;

    public String getModeId() {
        return modeId;
    }

    public void setModeId(String modeId) {
        this.modeId = modeId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }
}
