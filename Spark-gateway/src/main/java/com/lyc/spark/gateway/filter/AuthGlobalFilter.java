/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lyc.spark.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.common.api.ResultCode;
import com.lyc.spark.core.common.constant.AppConstant;
import com.lyc.spark.gateway.config.WhiteURLConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 鉴权认证
 *
 * @author Chill
 */
@Slf4j
@Component
@AllArgsConstructor // 该注解 默认生成全部属性的构造函数  所有属性通过构造函数注入
public class AuthGlobalFilter implements GlobalFilter, Ordered {


	public WhiteURLConfig whiteURLConfig;

	private ObjectMapper objectMapper;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();
		//  如果是白名单通过
		if (isWhiteURL(path)) {
			return chain.filter(exchange);
		}
		ServerHttpResponse resp = exchange.getResponse();
		String auth_token = exchange.getRequest().getHeaders().getFirst(AppConstant.AUTH_TOKEN);
		if (StrUtil.isBlank(auth_token)) {
			return unAuth(resp, "令牌缺失,鉴权失败");
		}
		// 此处判断令牌是否合法
//		String auth = StrUtil.isBlank(headerToken) ? paramToken : headerToken;
//		String token = JwtUtil.getToken(auth_token);
//		Claims claims = JwtUtil.parseJWT(token);
//		if (claims == null) {
//			return unAuth(resp, "请求未授权");
//		}
		return chain.filter(exchange);
	}

	private boolean isWhiteURL(String path) {
		final List<String> whiteURL = whiteURLConfig.getWhiteURL();
		boolean flag = false;
		for (int i = 0; i < whiteURL.size(); i++) {
			if(whiteURL.get(i).contains(path)) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}

	private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
		resp.setStatusCode(HttpStatus.UNAUTHORIZED);
		resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		String result = "";
		try {
			result = objectMapper.writeValueAsString(CommonResult.fail(ResultCode.UNAUTHORIZED, msg));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
		return resp.writeWith(Flux.just(buffer));
	}

	@Override
	public int getOrder() {
		return -200;
	}


}
