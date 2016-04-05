package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class UserMealServiceDataJpaTest extends UserMealServiceTest {

    @Override
    public void testDelete() throws Exception {
        super.testDelete();
    }

    @Override
    public void testDeleteNotFound() throws Exception {
        super.testDeleteNotFound();
    }

    @Override
    public void testSave() throws Exception {
        super.testSave();
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
    public void testUpdate() throws Exception {
        super.testUpdate();
    }

    @Override
    public void testNotFoundUpdate() throws Exception {
        super.testNotFoundUpdate();
    }

    @Override
    public void testGetAll() throws Exception {
        super.testGetAll();
    }

    @Override
    public void testGetBetween() throws Exception {
        super.testGetBetween();
    }

    @Test
    public void testGetWithUser() throws Exception {
        UserMeal userMeal = service.getWithUser(MEAL1_ID, USER_ID);
        MATCHER.assertEquals(MEAL1, userMeal);
        UserTestData.MATCHER.assertEquals(USER, userMeal.getUser());
    }

    @Test
    public void testGetWithUserOneRepository() throws Exception {
        UserMeal userMeal = service.getWithUserOneRepository(MEAL1_ID, USER_ID);
        MATCHER.assertEquals(MEAL1, userMeal);
        UserTestData.MATCHER.assertEquals(USER, userMeal.getUser());
    }
}
