package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    UserMeal findByIdAndUserId(Integer id, Integer userId);

    List<UserMeal> findAllByUserIdOrderByDateTimeDesc(int userId);

    List<UserMeal> findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate,
                                                                        LocalDateTime endDate);

    @Transactional
    int deleteByIdAndUserId(int id, int userId);

    @Transactional
    @Modifying
    @Query(name = UserMeal.UPDATE)
    int update(@Param("id") int id, @Param("userId") int userId, @Param("calories") int calories,
               @Param("description") String description, @Param("dateTime") LocalDateTime dateTime);

    @Transactional
    @Override
    UserMeal save(UserMeal userMeal);

    @Transactional
    @Query("SELECT um FROM UserMeal um LEFT JOIN FETCH um.user WHERE um.id=:id AND um.user.id=:userId")
    UserMeal getWithUser(@Param("id") int id, @Param("userId") int userId);
}