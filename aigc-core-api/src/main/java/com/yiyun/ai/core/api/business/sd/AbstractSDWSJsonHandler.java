package com.yiyun.ai.core.api.business.sd;

import com.google.gson.JsonObject;
import com.yiyun.ai.core.api.websocket.WSHandler;

import java.util.Optional;

public abstract class AbstractSDWSJsonHandler implements WSHandler<JsonObject, Optional<JsonObject>> {
}
