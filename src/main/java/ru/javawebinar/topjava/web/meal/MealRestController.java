package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.annotation.DateFormat;
import ru.javawebinar.topjava.util.annotation.TimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealRestController {

    public static final String REST_URL = "/rest/meals";

    @Autowired
    public MealRestController(MealService service) {
        super(service);
    }

    @Override
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(path = "/{id}")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal create(@RequestBody Meal meal) {
        return super.create(meal);
    }

    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    @Override
    @GetMapping(path = "/filter/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@DateFormat @RequestParam(value = "start_d", required = false) LocalDate startDate,
                                           @TimeFormat @RequestParam(value = "start_t", required = false) LocalTime startTime,
                                           @DateFormat @RequestParam(value = "end_d", required = false) LocalDate endDate,
                                           @TimeFormat @RequestParam(value = "end_t", required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}