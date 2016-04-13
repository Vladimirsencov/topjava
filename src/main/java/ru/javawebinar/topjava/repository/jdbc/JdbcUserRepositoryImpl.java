package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus ts = transactionManager.getTransaction(def);
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());
        try {


            if (user.isNew()) {
                Number newKey = insertUser.executeAndReturnKey(map);
                user.setId(newKey.intValue());
                insertRoles(user);
            } else {
                namedParameterJdbcTemplate.update(
                        "UPDATE users SET name=:name, email=:email, password=:password, " +
                                "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
                insertRoles(user);
            }
        } catch (Exception ex) {
            transactionManager.rollback(ts);
            throw new NotFoundException("Not success save" + this.getClass().getSimpleName());
        }
        transactionManager.commit(ts);
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
    }


    private int[] insertRoles(final User user) {
        return jdbcTemplate.batchUpdate("INSERT INTO user_roles (role, user_id) VALUES (?,?)",
                new BatchPreparedStatementSetter() {

                    /**
                     * Set parameter values on the given PreparedStatement.
                     *
                     * @param ps the PreparedStatement to invoke setter methods on
                     * @param i  index of the statement we're issuing in the batch, starting from 0
                     * @throws SQLException if a SQLException is encountered
                     *                      (i.e. there is no need to catch SQLException)
                     */
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        List<Role> roles = new ArrayList<Role>(user.getRoles());
                        Role role = roles.get(i);
                        ps.setString(1, role.toString());
                        ps.setInt(2, user.getId());
                    }

                    /**
                     * Return the size of the batch.
                     *
                     * @return the number of statements in the batch
                     */
                    @Override
                    public int getBatchSize() {
                        return user.getRoles().size();
                    }
                });
    }
}
