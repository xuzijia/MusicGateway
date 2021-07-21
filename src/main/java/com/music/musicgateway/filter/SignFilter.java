package com.music.musicgateway.filter;

import com.music.musicgateway.constant.GatewayConstant;
import com.music.musicgateway.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 接口统一鉴权
 */
@Component
public class SignFilter implements GlobalFilter {

    @Autowired
    private ResponseUtils responseUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
//        String sign = request.getHeaders().getFirst("sign");
//        if(sign==null){
//            //没有授权签名，返回401错误
//            return responseUtils.unauthorized(exchange,GatewayConstant.UNAUTHORIZED_MESSAGE, HttpStatus.UNAUTHORIZED.value());
//        }
        return chain.filter(exchange);
    }

}
