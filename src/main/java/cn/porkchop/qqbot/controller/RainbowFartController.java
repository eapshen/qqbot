package cn.porkchop.qqbot.controller;

import cn.porkchop.qqbot.service.RainbowFartService;
import cn.porkchop.qqbot.util.QQMessageHandleFunction;
import net.mamoe.mirai.message.data.QuoteReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RainbowFartController {

    @Autowired
    private RainbowFartService rainbowFartService;

    public QQMessageHandleFunction rainbowFart = (event) -> {
        event.getSubject().sendMessage(
                new QuoteReply(event.getSource())
                        .plus(" " + rainbowFartService.getRainbowFart())
        );
    };
}
