package edu.epam.mentoring.task7.service;

import edu.epam.mentoring.task7.annotation.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by eugen on 27.09.2016.
 */
@Component("wordCountService")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class SentenceWordLengthCounter implements Service {
    @Autowired
    private Service self;

    @Override
    @Logging
    public void doWork(String some) {
        Arrays.stream(some.split("\\s+")).map(self::doInternalWork).reduce(0, (e1, e2) -> e1 + e2);
    }

    @Override
    @Logging
    public int doInternalWork(String another) {
        return another.length();
    }

    public void setSelf(Service self) {
        this.self = self;
    }
}
