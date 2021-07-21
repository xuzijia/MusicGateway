package com.music.musicgateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 鉴权信息统一响应工具类
 */
@Component
public class ResponseUtils {

    public Mono<Void> unauthorized(ServerWebExchange exchange, String message, Integer status){
        // 自定义返回格式
        Map<String, Object> resultMap = new HashMap<>(8);
        resultMap.put("code", status == null ? 2 : status);
        resultMap.put("message", StringUtils.isBlank(message) ? "服务异常！" : message);
        resultMap.put("data", null);
        return Mono.defer(() -> {
            byte[] bytes;
            try {
                bytes = new ObjectMapper().writeValueAsBytes(resultMap);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("信息序列化异常");
            }
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString());
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Flux.just(buffer));
        });

    }

}
