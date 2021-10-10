package cn.porkchop.qqbot.controller;

import cn.porkchop.qqbot.event.MessageEvents;
import cn.porkchop.qqbot.util.QQMessageHandleFunction;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.QuoteReply;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class HelpController {
    public QQMessageHandleFunction help = (event) -> {
//        event.getSubject().sendMessage(
//                new QuoteReply(event.getSource())
//                        .plus(" 搜图")
//        );

        MessageEvent quoteMessageEvent = MessageEvents.messageMap.get(
                Arrays.toString(event.getMessage().get(QuoteReply.Key).getSource().getIds())
        );

        event.getSubject().sendMessage(quoteMessageEvent.getMessage());
    };
}
