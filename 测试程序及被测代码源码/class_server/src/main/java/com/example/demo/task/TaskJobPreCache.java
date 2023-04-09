package com.example.demo.task;

import com.example.demo.service.RedisService;
import com.example.demo.util.SpringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class TaskJobPreCache implements Job {
    RedisService redisService = SpringUtils.getBean(RedisService.class);

    @Override
    public  void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("正在填充缓存" + new Date());
        long startTime =  System.currentTimeMillis();
        redisService.getIds();
        long endTime =  System.currentTimeMillis();
        long usedTime = (endTime-startTime);
        System.out.println("填充缓存完毕......耗时"+usedTime+"秒 "+new Date());
    }

}
