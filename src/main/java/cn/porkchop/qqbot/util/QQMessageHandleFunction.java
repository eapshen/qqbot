package cn.porkchop.qqbot.util;

import net.mamoe.mirai.event.events.MessageEvent;

@FunctionalInterface
public interface QQMessageHandleFunction {
    void handle(MessageEvent messageEvent);
}
