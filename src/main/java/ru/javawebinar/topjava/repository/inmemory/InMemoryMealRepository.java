package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);

        if (userMeals == null) {
            userMeals = new ConcurrentHashMap<>();
        }

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            repository.put(userId, userMeals);

            return meal;
        }

        // treat case: update, but not present in storage
        Meal newMeal = userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        repository.put(userId, userMeals);

        return newMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);

        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);

        return userMeals != null ? userMeals.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);

        return userMeals != null ?
                userMeals.values().stream()
                        .sorted(Comparator.comparing(Meal::getDate).reversed())
                        .collect(Collectors.toList())
                : Collections.emptyList();
    }
}

