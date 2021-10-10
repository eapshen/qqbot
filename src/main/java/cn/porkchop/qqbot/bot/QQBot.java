package cn.porkchop.qqbot.bot;

import cn.porkchop.qqbot.event.MessageEvents;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QQBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    //一个实例只给一个bot，暂时不考虑一个实例允许部署多个bot
    private static Bot bot;

    public static Bot getBot() {
        return bot;
    }

    @Autowired
    private MessageEvents messageEvents;

    @Value("${bot.account}")
    private Long botAccount;

    @Value("${bot.password}")
    private String botPassword;


    /**
     * 启动BOT
     */
    public void startBot() {
        bot = BotFactory.INSTANCE.newBot(botAccount, botPassword, new BotConfiguration() {
            {
                //保存设备信息到文件deviceInfo.json文件里相当于是个设备认证信息
                fileBasedDeviceInfo();
                setProtocol(MiraiProtocol.ANDROID_WATCH); // 切换协议
                noNetworkLog();
            }
        });

        bot.login();
        Events.registerEvents(bot, messageEvents);

        bot.join();
    }
}
