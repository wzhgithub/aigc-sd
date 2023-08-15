package com.yiyun.ai.core.api.business.sd;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * <a href="https://github.com/TooTallNate/Java-WebSocket/tree/master/src/main/example">...</a>
 */
@Slf4j
public class SDWebSocketClient extends WebSocketClient {

    final List<AbstractSDWSJsonHandler> handlers;

    public SDWebSocketClient(URI serverUri, List<AbstractSDWSJsonHandler> handlers) {
        super(serverUri);
        this.handlers = handlers;
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("[onOpen]:{}", serverHandshake.getHttpStatusMessage());
    }

    @Override
    public void onMessage(String s) {
        log.info("[onMessage]:{}", s);
        JsonObject jsonObject = JsonParser.parseString(s).getAsJsonObject();
        Optional<JsonObject> res = handlers.stream().map(handler -> handler.handle(jsonObject))
                .reduce((x, y) -> x.isPresent() ? x : y)
                .flatMap(e -> e);
        // 处理结果
        res.ifPresent(object -> {
            log.info("[onMessage]result:{}", object);
        });
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("[onClose]reason:{} code:{} remote:{}", s, i, b);
    }

    @Override
    public void onError(Exception e) {
        log.error("[onError]", e);
    }
}
