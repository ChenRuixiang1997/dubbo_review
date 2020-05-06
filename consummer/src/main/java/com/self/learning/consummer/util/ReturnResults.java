package com.self.learning.consummer.util;

import lombok.Data;

@Data
public class ReturnResults<T> {
    private Integer code;
    private String message;
    private T data;
}
