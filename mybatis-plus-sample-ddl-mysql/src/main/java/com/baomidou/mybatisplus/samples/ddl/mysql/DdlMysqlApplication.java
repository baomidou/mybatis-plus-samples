package com.baomidou.mybatisplus.samples.ddl.mysql;

import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;
import com.baomidou.mybatisplus.extension.ddl.DdlScriptErrorHandler;
import com.baomidou.mybatisplus.extension.ddl.IDdl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DdlMysqlApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(DdlMysqlApplication.class);

    @Bean
    public DdlApplicationRunner ddlApplicationRunner(List<IDdl> ddlList) {
        LOGGER.info("使用自定义运行器.....");
        DdlApplicationRunner ddlApplicationRunner = new DdlApplicationRunner(ddlList);
        // 下面属性自 3.5.11 开始 ...
        // 设置是否自动提交 默认: true
        ddlApplicationRunner.setAutoCommit(false);
        // 设置脚本遇到错误的处理方式 默认: 忽略错误,打印异常 (如果设置为抛出异常,那会终止下一个sql文件处理)
        ddlApplicationRunner.setDdlScriptErrorHandler(DdlScriptErrorHandler.ThrowsErrorHandler.INSTANCE);
        //是否抛出异常中断下个处理器处理 默认: false
        ddlApplicationRunner.setThrowException(true);
        ddlApplicationRunner.setScriptRunnerConsumer(scriptRunner -> {
            scriptRunner.setLogWriter(null);   // 关闭执行日志打印 默认: System.out
            scriptRunner.setErrorLogWriter(null); // 关闭错误日志打印  默认:System.err
            scriptRunner.setStopOnError(true); // 遇到异常是否停止
            scriptRunner.setRemoveCRs(false); //  是否替换\r\n 为 \n 默认: false
        });
        return ddlApplicationRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(DdlMysqlApplication.class, args);
    }
}

