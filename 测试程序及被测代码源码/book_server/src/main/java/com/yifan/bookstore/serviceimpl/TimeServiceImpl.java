package com.yifan.bookstore.serviceimpl;

import com.yifan.bookstore.service.TimeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
@Service
@Scope(value = "session")
public class TimeServiceImpl implements TimeService {
StopWatch stopWatch=new StopWatch();
    @Override
    public void start(String username) {
        stopWatch.start(username);
    }

    @Override
    public String end() {
stopWatch.stop();
System.out.println(stopWatch.prettyPrint());
System.out.println(stopWatch.getTotalTimeMillis());
return stopWatch.getTotalTimeMillis()+"";
    }
}
