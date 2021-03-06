package ru.javawebinar.topjava.service;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.TimingRules;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static ru.javawebinar.topjava.util.ValidationUtil.getRootCause;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
abstract public class AbstractServiceTest {
    @ClassRule
    public static ExternalResource summary = TimingRules.SUMMARY;

    @Rule
    public TestName name = new TestName();

    @Rule
    public Stopwatch stopwatch = TimingRules.STOPWATCH;

    @Autowired
    public Environment env;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private Environment environment;

    private List<String> activeProfiles;

    @Before
    public void assuming() {
        if (activeProfiles == null) {
            activeProfiles = Arrays.asList(environment.getActiveProfiles());
        }
        boolean assumeFlag = activeProfiles.contains("jdbc") & name.getMethodName().equals("testValidation");
        Assume.assumeFalse(assumeFlag);
    }


    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    public boolean isJpaBased() {
//        return Arrays.stream(env.getActiveProfiles()).noneMatch(Profiles.JDBC::equals);
        return env.acceptsProfiles(Profiles.JPA, Profiles.DATAJPA);
    }

    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    public <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        try {
            runnable.run();
            Assert.fail("Expected " + exceptionClass.getName());
        } catch (Exception e) {
            Assert.assertThat(getRootCause(e), instanceOf(exceptionClass));
        }
    }
}