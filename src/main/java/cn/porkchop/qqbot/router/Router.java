package cn.porkchop.qqbot.router;

import cn.porkchop.qqbot.controller.HelpController;
import cn.porkchop.qqbot.controller.ImageController;
import cn.porkchop.qqbot.controller.RainbowFartController;
import cn.porkchop.qqbot.util.QQMessageHandleFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class Router {

    private final Map<String, QQMessageHandleFunction> ROUTER_MAP = new HashMap<>();

    private ImageController imageController;

    @Autowired
    public Router(
            HelpController helpController,
            ImageController imageController,
            RainbowFartController rainbowFartController
    ) {
        this.imageController = imageController;

        ROUTER_MAP.put(".帮助", helpController.help);
        ROUTER_MAP.put(".搜图", imageController.searhchImageSource);
        ROUTER_MAP.put("夸夸我", rainbowFartController.rainbowFart);
    }

    public Optional<QQMessageHandleFunction> findHandleFunction(String stringMessage) {
        return ROUTER_MAP.entrySet().stream().filter(entry -> {
            return stringMessage.startsWith(entry.getKey());
        }).map(Map.Entry::getValue).findFirst().or(() -> Optional.of(imageController.listenAndSearchSingleImage));
    }
}
