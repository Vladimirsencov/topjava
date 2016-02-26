package ru.javawebinar.topjava.model;

import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed implements Comparable<UserMealWithExceed> {
    protected final LocalDateTime dateTime;

    protected final String description;

    protected final int calories;

    protected final boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;

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

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dateTime", dateTime)
                .add("description", description)
                .add("calories", calories)
                .add("exceed", exceed)
                .toString();
    }


    @Override
    public int compareTo(UserMealWithExceed o) {
        return Integer.valueOf(calories).compareTo(o.calories) != 0 ?
                Integer.valueOf(calories).compareTo(o.calories) : description.compareTo(o.description) != 0 ?
                description.compareTo(o.description) : dateTime.compareTo(o.dateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserMealWithExceed)) return false;
        UserMealWithExceed that = (UserMealWithExceed) o;
        return getCalories() == that.getCalories() &&
                isExceed() == that.isExceed() &&
                java.util.Objects.equals(getDateTime(), that.getDateTime()) &&
                java.util.Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(getDateTime(), getDescription(), getCalories(), isExceed());
    }
}
