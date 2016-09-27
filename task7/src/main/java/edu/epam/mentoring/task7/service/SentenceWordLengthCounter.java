package edu.epam.mentoring.task7.service;

import edu.epam.mentoring.task7.annotation.Logging;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by eugen on 27.09.2016.
 */
public class SentenceWordLengthCounter implements Service {
    @Resource(name = "wordCountService")
    private Service self;

    @Override
    @Logging
    public void doWork(String some) {
        doInternalWork("ololo");
        Arrays.stream(some.split("\\s+")).map(self::doInternalWork).reduce(0, (e1, e2) -> e1 + e2);
    }

    @Override
    @Logging
    public int doInternalWork(String another) {
        return another.length();
    }
}
