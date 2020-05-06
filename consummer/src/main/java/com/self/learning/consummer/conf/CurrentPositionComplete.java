package com.self.learning.consummer.conf;

import com.self.learning.common.vo.Position;
import org.springframework.core.MethodParameter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1011:46
 * @Description TODO
 */
public class CurrentPositionComplete implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(Position.class)&&methodParameter.hasParameterAnnotation(CurrentPosition.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Position position = (Position) nativeWebRequest.getAttribute("positon", RequestAttributes.SCOPE_REQUEST);
        if(!ObjectUtils.isEmpty(position)){
            return position;
        }
        return null;
    }
}
