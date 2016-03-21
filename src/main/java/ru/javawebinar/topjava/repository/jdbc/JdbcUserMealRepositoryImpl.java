package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.TimeUtil.isBetween;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {
    private static final BeanPropertyRowMapper<UserMeal> ROW_MAPPER
            = BeanPropertyRowMapper.newInstance(UserMeal.class);
    private final SimpleJdbcInsert userMealInsert;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.userMealInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("USER_MEALS");
        //.usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        boolean isNew = false;
        if (userMeal.isNew()) {
            isNew = true;
            userMeal.setId(userId);
        }
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("meal_id", userMeal.getId())
                .addValue("description", userMeal.getDescription())
                .addValue("dateTime", userMeal.getDateTime())
                .addValue("calories", userMeal.getCalories());
        if (isNew) {
            userMealInsert.execute(map);
        } else {
            userMeal = update(userMeal, map);
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM user_meals WHERE meal_id=?", id) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return jdbcTemplate.query("SELECT *FROM user_meals " +
                "WHERE meal_id=?", ROW_MAPPER, id)
                .stream()
                .findAny()
                .orElse(new UserMeal(0, LocalDateTime.MIN, "", 0));
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return getBetween(LocalDateTime.MIN, LocalDateTime.MAX, userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate
                .query("SELECT * FROM user_meals WHERE meal_id = ? ORDER BY datetime ", ROW_MAPPER, userId)
                .stream().peek(System.out::println)
                .filter(u -> isBetween(u.getDateTime().toLocalDate(), startDate.toLocalDate(), endDate.toLocalDate()))
                .collect(toList());
    }

    protected UserMeal update(UserMeal userMeal, MapSqlParameterSource map) {
        namedParameterJdbcTemplate.update(
                "UPDATE user_meals SET " +
                        "description=:description," +
                        " calories=:calories," +
                        " dateTime=:dateTime"
                , map);
        return userMeal;
    }
}
