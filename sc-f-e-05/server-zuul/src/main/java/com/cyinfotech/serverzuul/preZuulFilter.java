package com.cyinfotech.serverzuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author 煮酒泛舟.
 * @title preZuulFilter
 * @program sc-f-e-05
 * @description preZuulFilter
 * @createtime 2018-10-31 17:28
 */
@Component
public class preZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 过滤顺序
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 是否过滤
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        String name = request.getParameter("name");

        if (StringUtils.isNotEmpty(name) && name.equals("cyit")) {
            requestContext.setSendZuulResponse(true);
        } else {
            requestContext.setSendZuulResponse(false);
            try {
                HttpServletResponse res = requestContext.getResponse();
                res.setContentType("text/html;charset=UTF-8");
                res.setCharacterEncoding("UTF-8");
                res.getWriter().write("用户校验不通过！！！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
