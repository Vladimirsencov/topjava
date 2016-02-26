package ru.javawebinar.topjava.model;

import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMeal implements Comparable<UserMeal> {
    protected final LocalDateTime dateTime;

    protected final String description;

    protected final int calories;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dateTime", dateTime)
                .add("description", description)
                .add("calories", calories)
                .toString();
    }


    @Override
    public int compareTo(UserMeal o) {
        return Integer.valueOf(calories).compareTo(o.calories) != 0 ?
                Integer.valueOf(calories).compareTo(o.calories) : description.compareTo(o.description) != 0 ?
                description.compareTo(o.description) : dateTime.compareTo(o.dateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserMeal)) return false;
        UserMeal userMeal = (UserMeal) o;
        return getCalories() == userMeal.getCalories() &&
                Objects.equals(getDateTime(), userMeal.getDateTime()) &&
                Objects.equals(getDescription(), userMeal.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDateTime(), getDescription(), getCalories());
    }
}
