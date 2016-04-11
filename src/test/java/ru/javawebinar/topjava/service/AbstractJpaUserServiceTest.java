package ru.javawebinar.topjava.service;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

/**
 * Created by Sentsov on 11.04.2016.
 */
public abstract class AbstractJpaUserServiceTest extends AbstractUserServiceTest {
    @SuppressAjWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}