package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.*;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        startDate = startDate != null ? startDate : LocalDate.MIN;
        endDate = endDate != null ? endDate : LocalDate.MAX;
        startTime = startTime != null ? startTime : LocalTime.MIN;
        endTime = endTime != null ? endTime : LocalTime.MAX;

        return MealsUtil.getFilteredTos(service.getFilteredByDate(authUserId(), startDate, endDate),
                authUserCaloriesPerDay(),
                startTime,
                endTime);
    }

    public Meal get(int id) {
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        return service.create(meal, authUserId());
    }

    public void update(Meal meal) {
        service.update(meal, authUserId());
    }

    public void delete(int id) {
        service.delete(id, authUserId());
    }

}