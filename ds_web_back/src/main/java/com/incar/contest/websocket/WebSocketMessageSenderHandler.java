//package com.incar.contest.websocket;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.web.socket.*;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
///**
// * Created by bzheng on 2020/2/29.
// */
////@Log4j2
//public class WebSocketMessageSenderHandler implements WebSocketHandler {
//
//    // {key:vin, value:{key:sessionId,value:session}} 一个Vin码可能被多个用户访问，条件不一样返回的数据也不一样
//    private static final Map<String, Map<String, WebSocketSession>> vins = new ConcurrentHashMap<>();
//
//    //新增socket后立刻执行的操作
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//
//    }
//
//    // 接收socket信息
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        try {
//            String json = JSON.toJSONString(message.getPayload());
//            JSONObject jsonobject = JSONObject.parseObject(json);
//            log.info(jsonobject);
////            sendMessageToUser(jsonobject.get("VIN") + "", new TextMessage("服务器收到了，hello!"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        if (session.isOpen()) {
//            session.close();
//        }
//        log.info("前后端websocket链接报错： session {}   {}", session.getId(), exception);
////        getClientId(session);
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
//        log.info("前后端websocket链接关闭： session {} ", session.getId());
////        getClientId(session);
//    }
//
//    @Override
//    public boolean supportsPartialMessages() {
//        return false;
//    }
//}
