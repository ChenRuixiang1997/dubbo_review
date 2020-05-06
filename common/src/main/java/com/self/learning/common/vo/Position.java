package com.self.learning.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1011:40
 * @Description TODO
 */
@Data
public class Position implements Serializable {
    private String country;
    private String province;
    private String city;
    private long distanceToTarget;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GTM+8")
    private Date now;
}
