package com.zxw.config;

import com.zxw.auth.utils.RsaUtils;
import com.zxw.util.Logger;

import javax.annotation.PostConstruct;

import org.smart4j.framework.annotaion.Service;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

@Service
public class JwtProperties {

    private String secret = "ly@Login(Auth}*^31)&heiMa%"; // 瀵嗛挜,鐧诲綍鏍￠獙鐨勫瘑閽�

    private String pubKeyPath = "C:/Users/zxw/Desktop/key/rsa.pub";// 鍏挜

    private String priKeyPath = "C:/Users/zxw/Desktop/key/rsa.pri";// 绉侀挜

    private int expire = 30;// token杩囨湡鏃堕棿

    private PublicKey publicKey; // 鍏挜

    private PrivateKey privateKey; // 绉侀挜

    private Integer cookieMaxAge = 18000;

    private String cookieName = "VS_TOKEN";
    
    Logger logger = new Logger(JwtProperties.class);
    
    

	public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public String getPriKeyPath() {
        return priKeyPath;
    }

    public void setPriKeyPath(String priKeyPath) {
        this.priKeyPath = priKeyPath;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public Integer getCookieMaxAge() {
        return cookieMaxAge;
    }

    public void setCookieMaxAge(Integer cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }
}