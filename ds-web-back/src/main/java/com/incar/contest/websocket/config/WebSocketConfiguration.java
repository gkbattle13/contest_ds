package com.incar.contest.websocket.config;

import com.incar.contest.websocket.WebSocketMessageSenderHandler;
import com.incar.contest.websocket.WebSocketMessageSenderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by bzheng on 2020/2/29.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //handler是webSocket的核心，配置入口
        registry.addHandler(new WebSocketMessageSenderHandler(), "/webSocke/basicStock/track/{vin}").setAllowedOrigins("*").addInterceptors(new WebSocketMessageSenderInterceptor());
    }

}

