package com.baomidou.mybatisplus.samples.dts.rabbit;

import com.baomidou.mybatisplus.dts.DtsMeta;
import com.baomidou.mybatisplus.dts.sender.RabbitRmtSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * rabbit 分布式事务消息测试
 *
 * @author jobob
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {
    public static final Logger logger = LoggerFactory.getLogger(RabbitController.class);
    @Autowired
    protected PlatformTransactionManager transactionManager;
    @Autowired
    private RabbitRmtSender rmtSender;


    @RequestMapping(value = "/send")
    @Transactional
    public String send() {
        String event = "Message: send";
        logger.info("Sending message: {} with transaction manager: {}", event, transactionManager.getClass().getSimpleName());
        rmtSender.send(new DtsMeta().setEvent(event).setPayload("rabbit send"));
        return String.format("Event sent: %s", event);
    }

    @RequestMapping(value = "/send-error")
    @Transactional
    public String sendError() {
        String event = "Message: sendError";
        logger.info("Sending message: {} with transaction manager: {}", event, transactionManager.getClass().getSimpleName());
        rmtSender.send(new DtsMeta().setEvent(event).setPayload("rabbit send-error"));
        throw new RuntimeException("Test exception");
    }

    @RequestMapping(value = "/send-receive-error")
    @Transactional
    public String sendReceiveError() {
        String event = "ErrorMessage: sendReceiveError";
        logger.info("Sending message: {} with transaction manager: {}", event, transactionManager.getClass().getSimpleName());
        rmtSender.send(new DtsMeta().setEvent(event).setPayload("rabbit send-receive-error"));
        return String.format("Event sent: %s", event);
    }
}