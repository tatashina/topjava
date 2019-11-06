package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Collections;
import java.util.Date;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({DATAJPA})
public class DatajpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        assertMatch(USER, user);
        MealTestData.assertMatch(user.getMeals(), MealTestData.MEALS);
    }

    @Test
    public void getWithEmptyMeals() throws Exception {
        User newUser = service.create(new User(null, "New", "new@gmail.com", "newPass", 1500, false, new Date(), Collections.singleton(Role.ROLE_USER)));
        User userWithEmptyMeals = service.getWithMeals(newUser.getId());
        MealTestData.assertMatch(userWithEmptyMeals.getMeals(), Collections.emptyList());
    }
}
