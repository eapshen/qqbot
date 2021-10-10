package cn.porkchop.qqbot.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class StockIndexTask {

    @Scheduled(cron = "0/5 * * * * ?")
    private void process() {
        System.out.println("this is scheduler task running ");
    }
}
