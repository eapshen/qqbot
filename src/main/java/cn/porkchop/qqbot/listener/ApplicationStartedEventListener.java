package cn.porkchop.qqbot.listener;

import cn.porkchop.qqbot.bot.QQBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent>{

    @Autowired
    private QQBot qqBot;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent ev) {
        qqBot.startBot();
    }
}
