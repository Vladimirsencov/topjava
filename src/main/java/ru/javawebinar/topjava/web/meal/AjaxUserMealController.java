package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Vladimir_Sentso on 26.04.2016.
 */
@RestController
@RequestMapping(value = "/ajax/profile/meals")
public class AjaxUserMealController extends AbstractUserMealController {
    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed>
    getBetween(@RequestParam(value = "startDate", required = false) LocalDate startDate,
               @RequestParam(value = "startTime", required = false) LocalTime startTime,
               @RequestParam(value = "endDate", required = false) LocalDate endDate,
               @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        startDate = startDate != null ? startDate : TimeUtil.MIN_DATE;
        endDate = endDate != null ? endDate : TimeUtil.MAX_DATE;
        startTime = startTime != null ? startTime : LocalTime.MIN;
        endTime = endTime != null ? endTime : LocalTime.MAX;
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createOrUpdateUserMeal(@RequestParam("id") int id,
                                       @RequestParam("datetime")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       LocalDateTime dateTime,
                                       @RequestParam("calories") int calories,
                                       @RequestParam("description") String description) {
        UserMeal userMeal = new UserMeal(id, dateTime, description, calories);
        if (id == 0) create(userMeal);
        else update(userMeal, id);
    }
}
