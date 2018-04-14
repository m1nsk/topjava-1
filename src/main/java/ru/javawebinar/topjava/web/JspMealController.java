package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.Util.orElse;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    @Autowired
    private MealService service;

    @GetMapping("/delete")
    public String deleteMeal (@RequestParam("id") int id) {
        int userId = AuthorizedUser.id();
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String createMeal (Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String updateMeal (@RequestParam("id") int id, Model model) {
        int userId = AuthorizedUser.id();
        final Meal meal = service.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }
    @GetMapping
    public String allMeal (Model model) {
        int userId = AuthorizedUser.id();
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

//    @GetMapping("")
//    public String meals(@RequestParam("action") String action, @RequestParam("id") int id, Model model) {
//        int userId = AuthorizedUser.id();
//
//        switch (action == null ? "all" : action) {
//            case "delete":
//                service.delete(id, userId);
//                return "meals";
//            case "create":
//            case "update":
//                final Meal meal = "create".equals(action) ?
//                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
//                        service.get(id, userId);
//                model.addAttribute("meal", meal);
//                return "mealForm";
//            case "all":
//            default:
//                model.addAttribute("meals", service.getAll(userId));
//                return "meals";
//        }
//        model.addAttribute("meals", service.getAll());
//        return "meals";
//    }

    @PostMapping("")
    public String setMeal(
            @RequestParam("id") Integer id,
            @RequestParam("dateTime") String dateTime,
            @RequestParam("calories") int calories,
            @RequestParam("description") String description
    ) {
        int userId = AuthorizedUser.id();
        Meal meal = new Meal(
                LocalDateTime.parse(dateTime),
                description,
                calories);

        if (id == null) {
            checkNew(meal);
            service.create(meal, userId);
        } else {
            assureIdConsistent(meal, id);
            service.update(meal, userId);
        }
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filterMeal(
            @RequestParam("startDate") String startDateParam,
            @RequestParam("endDate") String endDateParam,
            @RequestParam("startTime") String startTimeParam,
            @RequestParam("endTime") String endTimeParam,
            Model model) {
        int userId = AuthorizedUser.id();
        LocalDate startDate = parseLocalDate(startDateParam);
        LocalDate endDate = parseLocalDate(endDateParam);
        LocalTime startTime = parseLocalTime(startTimeParam);
        LocalTime endTime = parseLocalTime(endTimeParam);
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        List<Meal> mealsDateFiltered = service.getBetweenDates(
                orElse(startDate, DateTimeUtil.MIN_DATE), orElse(endDate, DateTimeUtil.MAX_DATE), userId);

        return MealsUtil.getFilteredWithExceeded(mealsDateFiltered, AuthorizedUser.getCaloriesPerDay(),
                orElse(startTime, LocalTime.MIN), orElse(endTime, LocalTime.MAX)
        );
    }
}
