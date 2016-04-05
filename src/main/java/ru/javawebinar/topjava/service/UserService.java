package ru.javawebinar.topjava.service;


import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public interface UserService {

    User save(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    List<User> getAll();

    void update(User user);

    void evictCache();

    User getWithMeals(int userId);

    Map<User, Collection<UserMeal>> getWithMealsAsMap(int userId);

    User getWithMealsOneRepository(int userId);
}
