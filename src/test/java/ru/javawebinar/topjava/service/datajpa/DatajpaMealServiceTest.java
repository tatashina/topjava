package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({DATAJPA})
public class DatajpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    public void getMealWithUser() {
        Meal meal = service.getWithUser(MEAL1_ID, USER_ID);
        assertMatch(meal, MEAL1);
        UserTestData.assertMatch(meal.getUser(), UserTestData.USER);
    }
}
