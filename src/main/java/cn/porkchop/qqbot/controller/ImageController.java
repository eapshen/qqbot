package cn.porkchop.qqbot.controller;

import cn.porkchop.qqbot.service.ImageService;
import cn.porkchop.qqbot.util.QQMessageHandleFunction;
import net.mamoe.mirai.internal.message.OnlineImage;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.message.data.SingleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ImageController {
    private static final String IMAGE_SEARCH_NO_IMAGE_INPUT = " 图呢????????";
    private static final String IMAGE_SEARCH_IMAGE_URL_PARSE_FAIL = " 图片解析失败";

    @Autowired
    private ImageService imageService;

    public QQMessageHandleFunction searhchImageSource = (event) -> {
        String[] args = event.getMessage().contentToString().trim().split("\\s+");

        if (args.length <= 1) {
            event.getSubject().sendMessage(
                    new QuoteReply(event.getSource())
                            .plus(IMAGE_SEARCH_NO_IMAGE_INPUT)
            );

            return;
        }
        //获取传入图片，解析mirai消息中的网络图片链接
        //[mirai:image:{FD4A1FC8-45F7-A3A9-FB8A-C75AFE71C47E}.mirai]
//         String temp_msg_part = messageChain.get(messageChain.size()-1).toString();
//        if(MiraiUtil.isMiraiImg(temp_msg_part)){
//        }
        //args中，图片被转为了字符串"[图片]"，这里直接判断args第一个参数是否为"[图片]",然后从messageChain中取索引为2的元素，忽略后面的图片
        if (!args[1].equals("[图片]")) {
            event.getSubject().sendMessage(
                    new QuoteReply(event.getSource())
                            .plus(IMAGE_SEARCH_NO_IMAGE_INPUT)
            );

            return;
        }

        //获取图片网络链接，gchat.qpic.cn是腾讯自家的
        //mirai中原图链接在messageChain中的图片元素下，但是需要强转
        //我看不太懂kotlin，先凑合着用吧,虽然可以运行，但代码报红，提示Usage of Kotlin internal declaration from different module
        //http://gchat.qpic.cn/gchatpic_new/455806936/3987173185-2655981981-FD4A1FC845F7A3A9FB8AC75AFE71C47E/0?term=2
        String imgUrl = ((OnlineImage) event.getMessage().get(2)).getOriginUrl();

        if (!StringUtils.hasText(imgUrl)) {
            event.getSubject().sendMessage(
                    new QuoteReply(event.getSource())
                            .plus(IMAGE_SEARCH_IMAGE_URL_PARSE_FAIL)
            );

            return;
        }

        String report = imageService.searchImgFromSaucenao(imgUrl);

        event.getSubject().sendMessage(
                new QuoteReply(event.getSource())
                        .plus(" " + report)
        );
    };

    public QQMessageHandleFunction listenAndSearchSingleImage = (event) -> {
        SingleMessage singleMessage = event.getMessage().get(1);

        if (OnlineImage.class.isAssignableFrom(singleMessage.getClass())) {
            String originUrl = ((OnlineImage) singleMessage).getOriginUrl();

            String report = imageService.searchImgFromSaucenao(originUrl);

            if (
                    !ImageService.IMAGE_NOT_FOUND_MESSAGE.equals(report) &&
                            !ImageService.IMAGE_LOW_SIMILARITY_MESSAGE.equals(report)
            ) {
                event.getSubject().sendMessage(
                        new QuoteReply(event.getSource())
                                .plus(" " + report)
                );
            }
        }
    };
}
