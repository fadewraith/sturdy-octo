package com.journalappdemo.cron;

import com.journalappdemo.scheduler.UserScheduler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulersTest {

    @Autowired
    private UserScheduler userScheduler;


    @Disabled
    @Test
    public void testFetchUsersAndSendSaMail(){
        userScheduler.fetchUsersAndSendSentimentAnalysisMail();
    }
}