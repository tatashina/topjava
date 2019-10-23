package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Meal USER_MEAL_1 = new Meal(START_SEQ + 2, LocalDateTime.of(2015, Month.MAY, 30,10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL_2 = new Meal(START_SEQ + 3, LocalDateTime.of(2015, Month.MAY, 30,13, 0), "Обед", 1000);
    public static final Meal USER_MEAL_3 = new Meal(START_SEQ + 4, LocalDateTime.of(2015, Month.MAY, 30,20, 0), "Ужин", 500);
    public static final Meal USER_MEAL_4 = new Meal(START_SEQ + 5, LocalDateTime.of(2015, Month.MAY, 31,10, 0), "Завтрак", 1000);
    public static final Meal USER_MEAL_5 = new Meal(START_SEQ + 6, LocalDateTime.of(2015, Month.MAY, 31,13, 0), "Обед", 500);
    public static final Meal USER_MEAL_6 = new Meal(START_SEQ + 7, LocalDateTime.of(2015, Month.MAY, 31,20, 0), "Ужин", 510);

    public static final Meal ADMIN_MEAL_1 = new Meal(START_SEQ + 8, LocalDateTime.of(2015, Month.JUNE, 1,14, 0), "Админ ланч", 510);
    public static final Meal ADMIN_MEAL_2 = new Meal(START_SEQ + 9, LocalDateTime.of(2015, Month.JUNE, 1,21, 0), "Админ ужин", 1500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
