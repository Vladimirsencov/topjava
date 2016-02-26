package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        System.out.println(getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000));
/*
.toLocalDate();
.toLocalTime();
*/
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
       // System.out.println("// TODO return filtered list with correctly exceeded field");

        final Map<LocalDate, Integer> sumCaloriesPerDay = new HashMap<>();

        for(UserMeal meal:mealList){
         if(!sumCaloriesPerDay.containsKey(meal.getDateTime().toLocalDate())){
             sumCaloriesPerDay.put(meal.getDateTime().toLocalDate(),meal.getCalories());
         }else {
             int total = sumCaloriesPerDay.get(meal.getDateTime().toLocalDate());
             total+=meal.getCalories();
             sumCaloriesPerDay.put(meal.getDateTime().toLocalDate(),total);
         }
        }

        final List<UserMealWithExceed> mealExceeded = new ArrayList<>( mealList.size());

        for(UserMeal meal : mealList){
            if(TimeUtil.isBetween(meal.getDateTime().toLocalTime(),startTime,endTime)){
                mealExceeded.add(new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),
                        sumCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        return Collections.unmodifiableList(mealExceeded);
    }
}
