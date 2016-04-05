package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;
import java.util.Map;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class UserServiceDataJpaTest extends UserServiceTest {
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void testSave() throws Exception {
        super.testSave();
    }

    @Override
    public void testDuplicateMailSave() throws Exception {
        super.testDuplicateMailSave();
    }

    @Override
    public void testDelete() throws Exception {
        super.testDelete();
    }

    @Override
    public void testNotFoundDelete() throws Exception {
        super.testNotFoundDelete();
    }

    @Override
    public void testGet() throws Exception {
        super.testGet();
    }

    @Override
    public void testGetNotFound() throws Exception {
        super.testGetNotFound();
    }

    @Override
    public void testGetByEmail() throws Exception {
        super.testGetByEmail();
    }

    @Override
    public void testGetAll() throws Exception {
        super.testGetAll();
    }

    @Override
    public void testUpdate() throws Exception {
        super.testUpdate();
    }

    @Test
    public void testGetWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        MATCHER.assertEquals(USER, user);
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.USER_MEALS, user.getMeals());
    }

    @Test
    public void testGetWithMealsAsMap() throws Exception {
        final Map<User, Collection<UserMeal>> pair = service.getWithMealsAsMap(USER_ID);
        Assert.assertTrue(pair.size() == 1);
        for (Map.Entry<User, Collection<UserMeal>> entry : pair.entrySet()) {
            MATCHER.assertEquals(USER, entry.getKey());
            MealTestData.MATCHER.assertCollectionEquals(MealTestData.USER_MEALS, entry.getValue());
        }
    }

    @Test
    public void testGetWithMealsOneRep() throws Exception {
        User user = service.getWithMealsOneRepository(USER_ID);
        MATCHER.assertEquals(USER, user);
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.USER_MEALS, user.getMeals());
    }
}