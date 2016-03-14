package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private static final Comparator<UserMeal> USER_MEAL_COMPARATOR = comparing(UserMeal::getDateTime).reversed();

    private final Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach((userMeal -> save(userMeal, userMeal.getUserID())));
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userID) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
            userMeal.setUserID(userID);
        }
        if (userMeal.getUserID() == null) throw new IllegalArgumentException("Eater not defined");
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public UserMeal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll(final int userID) {
        return unmodifiableCollection(repository.values()
                .parallelStream().filter(userMeal -> userMeal.getUserID() == userID)
                .sorted(USER_MEAL_COMPARATOR)
                .collect(toList()));
    }

    @Override
    public Collection<UserMeal> getBetween(LocalDate startDate, LocalDate endDate, int userID) {
        return unmodifiableCollection(
                getAll(userID).stream()
                        .filter(userMeal -> TimeUtil.isBetweenDate(userMeal.getDateTime().toLocalDate(), startDate, endDate)

                                .collect(toList()));
    }
}

