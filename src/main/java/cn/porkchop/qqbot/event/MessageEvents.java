package cn.porkchop.qqbot.event;

import cn.porkchop.qqbot.router.Router;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class MessageEvents extends SimpleListenerHost {
    public static Map<String, MessageEvent> messageMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(MessageEvents.class);

    @Autowired
    private Router router;

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        logger.error("RecallEvent Error:{}", exception.getMessage());
    }

    @NotNull
    @EventHandler(priority = Listener.EventPriority.LOW)
    public ListeningStatus onGroupMessage(@NotNull MessageEvent event) {
        User sender = event.getSender();
        String oriMsg = event.getMessage().contentToString();

        messageMap.put(Arrays.toString(event.getSource().getIds()), event);

        router.findHandleFunction(oriMsg).ifPresent(function -> {
            function.handle(event);
        });

        return ListeningStatus.LISTENING; // 表示继续监听事件
    }
}
