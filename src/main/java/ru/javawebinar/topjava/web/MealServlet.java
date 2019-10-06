package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String MEAL_LIST = "/meals.jsp";
    private MealDAO meals;

    public MealServlet() {
        super();
        meals = new MealDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            if (action.equalsIgnoreCase("delete")) {
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                meals.delete(mealId);
                response.sendRedirect("meals");

            } else if (action.equalsIgnoreCase("insert")) {
                request.setAttribute("mealList", MealsUtil.getFiltered(meals.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);

            } else if (action.equalsIgnoreCase("edit")) {
                request.setAttribute("meal", meals.get(Integer.parseInt(request.getParameter("mealId"))));
                request.setAttribute("mealList", MealsUtil.getFiltered(meals.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
            }
        } else {
            request.setAttribute("mealList", MealsUtil.getFiltered(meals.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            request.getRequestDispatcher(MEAL_LIST).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Meal newMeal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            meals.add(newMeal);
        } else {
            newMeal.setId(Integer.parseInt(mealId));
            meals.update(Integer.parseInt(mealId), newMeal);
        }

        request.setAttribute("mealList", MealsUtil.getFiltered(meals.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher(MEAL_LIST).forward(request, response);
    }
}
