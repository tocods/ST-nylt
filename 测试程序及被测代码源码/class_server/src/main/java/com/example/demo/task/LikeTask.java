package com.example.demo.task;

import com.example.demo.service.CollectService;
import com.example.demo.service.RedisRankService;
import com.example.demo.service.RedisService;
import com.example.demo.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 收藏的定时任务
 */
@Component
public class LikeTask implements Job {

    RedisService redisService = SpringUtils.getBean(RedisService.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("正在将收藏数据写入数据库......"+new Date());
        long startTime =  System.currentTimeMillis();
        long endTime =  System.currentTimeMillis();
        long usedTime = (endTime-startTime);
        System.out.println("收藏数据写入数据库完毕，耗时"+usedTime+"毫秒"+new Date());
    }
}