package com.baomidou.mybatisplus.samples.dts.rabbit;

import com.baomidou.mybatisplus.dts.DtsMeta;
import com.baomidou.mybatisplus.dts.listener.IDtsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * rabbit 消息接收器
 *
 * @author jobob
 * @since 2019-04-21
 */
@Component
public class RabbitReceiver implements IDtsListener {
    public static final Logger logger = LoggerFactory.getLogger(RabbitReceiver.class);
    @Autowired
    protected PlatformTransactionManager transactionManager;

    @Override
    public void process(DtsMeta dtsMeta) {
        logger.info("Receiving message: {} with transaction manager: {}",
                dtsMeta.getPayload(), transactionManager.getClass().getSimpleName());
        /**
         * 根据 event 处理不同业务逻辑
         */
        if (dtsMeta.getEvent().startsWith("Error")) {
            throw new RuntimeException("Test receiver exception");
        }
    }
}
