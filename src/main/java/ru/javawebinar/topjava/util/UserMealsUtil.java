package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> totalCaloriesPerDay = new HashMap<>();
        List<UserMeal> filteredMeals = new ArrayList<>();

        for (UserMeal meal : mealList) {
            LocalDate mealLocalDate = meal.getDateTime().toLocalDate();
            Integer caloriesThisDay = totalCaloriesPerDay.getOrDefault(mealLocalDate, 0) + meal.getCalories();
            totalCaloriesPerDay.put(mealLocalDate, caloriesThisDay);

            LocalTime mealLocalTime = meal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(mealLocalTime, startTime, endTime)) {
                filteredMeals.add(meal);
            }
        }

        List<UserMealWithExceed> filteredWithExceeded = new ArrayList<>();
        for (UserMeal filteredMeal : filteredMeals) {
            filteredWithExceeded.add(new UserMealWithExceed(filteredMeal,
                    totalCaloriesPerDay.get(filteredMeal.getDateTime().toLocalDate()) > caloriesPerDay));
        }

        return filteredWithExceeded;
    }
}
