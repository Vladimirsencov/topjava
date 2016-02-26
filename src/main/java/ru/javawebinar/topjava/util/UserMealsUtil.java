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
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        System.out.println(getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
/*
.toLocalDate();
.toLocalTime();
*/
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        // System.out.println("// TODO return filtered list with correctly exceeded field");
        List<UserMealWithExceed> list = new ArrayList<>();
        //создаем карту для хранения по датам
        HashMap<LocalDate, ArrayList<UserMeal>> mapMealByDate = new HashMap<>();

        //идем по списку из параметра и сортируем по дате добавляя в карту
        for(UserMeal meal : mealList){
            //создаем временный лист и временную переменную с датой
            LocalDate tempLocalDate = meal.getDateTime().toLocalDate();
            ArrayList<UserMeal> temporaryList = new ArrayList<>();
            //если карта содержит уже ключ с этой датой
            if(mapMealByDate.keySet().contains(tempLocalDate)){
                temporaryList = mapMealByDate.get(tempLocalDate);
                temporaryList.add(meal);
            }else {
                temporaryList.add(meal);
            }
            mapMealByDate.put(tempLocalDate, temporaryList);
        }

        Map<LocalDate,Integer> mapMeal=new HashMap<>(mapMealByDate.size());
        for(Map.Entry<LocalDate, ArrayList<UserMeal>> entry:mapMealByDate.entrySet()){
            mapMeal.put(entry.getKey(),getTotalCalories(entry.getValue()));
        }

        //идем по карте проходим по каждому айтему циклом суммируя калории
        //потом добавляем вторым циклом в результирующий список фильтруя по дате
        for(UserMeal meal:mealList){
            if(TimeUtil.isBetween(meal.getDateTime().toLocalTime(),startTime,endTime)){
                list.add(new UserMealWithExceed(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        mapMeal.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return list;
    }

    private static int getTotalCalories(List<UserMeal> list){
        int total = 0;
        for(UserMeal m: list){
            total+=m.getCalories();
        }
        return total;
    }
}