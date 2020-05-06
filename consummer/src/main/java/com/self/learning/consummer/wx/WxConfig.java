package com.self.learning.consummer.wx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/610:19
 * @Description TODO
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WxConfig {
    private String appId;
    private String redirectUri;
    private String responseType;
    private String scope;
    private String codeUri;
    private String accessTokenUri;
    private String secret;
    private String grantType;
    private String userInfoUri;
    private String loginSuccess;
    private String loginFailure;

    public String reqCodeUrl(){
        StringBuffer sb = new StringBuffer(getCodeUri());
        sb.append("?").append("appid=").append(getAppId());
        sb.append("&").append("redirect_uri=").append(getRedirectUri());
        sb.append("&").append("response_type=").append(getResponseType());
        sb.append("&").append("scope=").append(getScope());
        sb.append("&").append("state=").append("STATE");
        sb.append("#wechat_redirect");
        return sb.toString();
    }

    public String reqAccessTokenUri(String code){
        StringBuffer sb = new StringBuffer(getAccessTokenUri());
        sb.append("?").append("appid=").append(getAppId());
        sb.append("&").append("secret=").append(getSecret());
        sb.append("&").append("code=").append(code);
        sb.append("&").append("grant_type=").append(getGrantType());
        return sb.toString();
    }

    public String reqUserInfo(String accessToken,String openId){
        StringBuffer sb = new StringBuffer(getUserInfoUri());
        sb.append("?").append("access_token=").append(accessToken);
        sb.append("&").append("openid=").append(openId).append("&lang=zh_CN");
        return sb.toString();
    }
}
