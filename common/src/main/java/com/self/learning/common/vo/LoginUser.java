package com.self.learning.common.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginUser implements Serializable {
    //网站账户信息
    private String uId;
    private String uPhonneNumber;
    private String uNickname;
    private Integer uSex;
    private String uPassword;
    private Date uCreaedTime;
    private Long uScore;

    //用户微信信息
    private Long id;
    private String openid;
    private String nickname;
    private Integer sex;
    private String lang;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String privilege;
}
