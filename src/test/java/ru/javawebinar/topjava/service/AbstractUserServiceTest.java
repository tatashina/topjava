package ru.javawebinar.topjava.service;

import org.junit.Assume;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static ru.javawebinar.topjava.UserTestData.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Autowired
    protected UserService service;

    @Test
    public void create() throws Exception {
        User newUser = getNew();
        User created = service.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        assertMatch(created, newUser);
        assertMatch(service.get(newId), newUser);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN));
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(ADMIN_ID);
        service.get(ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    public void update() throws Exception {
        User updated = getUpdated();
        service.update(updated);
        //assertMatch(service.get(USER_ID), updated);
        assertMatch(service.getAll(), updated, USER);
    }

    @Test
    public void updateGetAll() throws Exception {
        List<User> all = service.getAll();
        assertMatch(all, ADMIN, USER);
    }

    @Test
    public void createWithException() throws Exception {
        Assume.assumeFalse(isJdbcProfile());
        validateRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER, Role.ROLE_ADMIN)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", 9, true, new Date(), Set.of())), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", 10001, true, new Date(), Set.of())), ConstraintViolationException.class);
    }
}