package com.example.demo.task;

import com.example.demo.service.RedisRankService;
import com.example.demo.util.SpringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class TaskJobRank implements Job {
    RedisRankService redisRankService = SpringUtils.getBean(RedisRankService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("正在更新每日排行......"+new Date());
        long startTime =  System.currentTimeMillis();
        redisRankService.clearDay();
        long endTime =  System.currentTimeMillis();
        long usedTime = (endTime-startTime);
        System.out.println("更新排行完毕,耗时"+usedTime+"毫秒"+new Date());
    }

}
