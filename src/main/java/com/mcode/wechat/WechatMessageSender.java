package com.mcode.wechat;

import java.io.IOException;

@FunctionalInterface
public interface WechatMessageSender {
    void send(String text) throws IOException;
}
