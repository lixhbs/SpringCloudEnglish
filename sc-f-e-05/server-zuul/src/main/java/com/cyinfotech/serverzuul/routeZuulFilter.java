package com.cyinfotech.serverzuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;

/**
 * @author 煮酒泛舟.
 * @title Route
 * @program sc-f-e-05
 * @description Route
 * @createtime 2018-11-01 16:01
 */
@Component
public class routeZuulFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(errorZuulFilter.class);

    @Override
    public String filterType() {
        return ROUTE_TYPE;
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
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), "run by ROUTE_TYPE"));
        return null;
    }
}