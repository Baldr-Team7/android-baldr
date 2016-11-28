package com.gu.example.axel.baldr;

/**
 * Created by arasb on 2016-11-22.
 */

public class Json {
    private LightInfo lightInfo;

    private String protocolName;

    private String version;

    public LightInfo getLightInfo() {
        return lightInfo;
    }

    public void setLightInfo(LightInfo lightInfo) {
        this.lightInfo = lightInfo;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return " [lightInfo = " + lightInfo + ", protocolName = " + protocolName + ", version = " + version + "]";
    }





}


