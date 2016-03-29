package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = UserMeal.GET, query = "SELECT m FROM UserMeal m WHERE m.user.id=:userId"),
        @NamedQuery(name = UserMeal.ALL_SORTED, query
                = "SELECT m FROM UserMeal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC "),
        @NamedQuery(name = UserMeal.DELETE_ALL,
                query = "DELETE FROM UserMeal m WHERE m.user.id=:userId"),
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE  FROM UserMeal m  WHERE m.id=:id and m.user.id=:userId"),
        @NamedQuery(name = UserMeal.GET_BETWEN,
                query = "SELECT m FROM UserMeal m WHERE m.user.id=:userId and m.dateTime >=:after" +
                        " and m.dateTime<=:before ORDER BY m.dateTime DESC")

})

@Entity
@Table(name = "meals",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"},
                name = "meals_unique_user_datetime_idx")})
public class UserMeal extends BaseEntity {
    public static final String
            GET = "UserMeal.get",
            ALL_SORTED = "UserMeal.getAll",
            DELETE = "UserMeal.delete",
            DELETE_ALL = "UserMeal.deleteAll",
            GET_BETWEN = "UserMeal.getBeetwen";

    @Column(name = "calories", nullable = false, columnDefinition = "default 0")
    @Digits(fraction = 0, integer = 4)
    protected int calories;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
