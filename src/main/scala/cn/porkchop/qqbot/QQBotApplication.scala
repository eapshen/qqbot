package cn.porkchop.qqbot

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestTemplate


object QQBotApplication {
    def main(args: Array[String]): Unit = {
        SpringApplication.run(classOf[QQBotApplication], args: _*)
    }
}

@EnableScheduling
@SpringBootApplication
class QQBotApplication {
    @Bean
    def restTemplate = new RestTemplate

    @Bean
    def objectMapper = new ObjectMapper
}
