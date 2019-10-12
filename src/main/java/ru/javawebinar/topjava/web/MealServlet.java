package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MemoryMealDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        meals = new MemoryMealDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "" : action) {
            case "delete":
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                meals.delete(mealId);
                response.sendRedirect("meals");
                break;
            case "insert":
                setAttributeMealsAndForward(request, response, meals, INSERT_OR_EDIT);
                break;
            case "edit":
                request.setAttribute("meal", meals.get(Integer.parseInt(request.getParameter("mealId"))));
                setAttributeMealsAndForward(request, response, meals, INSERT_OR_EDIT);
                break;
            default:
                setAttributeMealsAndForward(request, response, meals, MEAL_LIST);
                break;
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

        response.sendRedirect("meals");
    }

    private void setAttributeMealsAndForward(HttpServletRequest request, HttpServletResponse response, MealDAO meals, String forwardTo) throws ServletException, IOException {
        request.setAttribute("mealList", MealsUtil.getFiltered(meals.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher(forwardTo).forward(request, response);
    }
}
