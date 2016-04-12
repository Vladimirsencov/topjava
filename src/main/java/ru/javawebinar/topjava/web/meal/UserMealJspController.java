package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.lang.Integer.valueOf;
import static ru.javawebinar.topjava.model.UserMeal.getDefaultUserMeal;

/**
 * Created by Vladimir_Sentso on 12.04.2016.
 */
@Controller
@RequestMapping(value = "/meals")
public class UserMealJspController extends AbstractUserMealController {

    @RequestMapping(method = RequestMethod.GET)
    public String mealList(Model model) {
        model.addAttribute("mealList", super.getAll());
        return "mealList";
    }

    /*@RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request){
        super.delete(getId(request));
        return "redirect:meals";
    }

    private int getId(HttpServletRequest request) {
        return Integer
                .valueOf(Objects
                        .requireNonNull(request
                                .getParameter("id")));
    }*/

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") String id) {
        super.delete(valueOf(id));
        return "redirect:/meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("meal", getDefaultUserMeal());
        return "mealEdit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(@RequestParam(value = "id") String id, Model model) {
        model.addAttribute("meal", super.get(valueOf(id)));
        return "mealEdit";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateOrCreate(Model model,
                                 @RequestParam(value = "id") String id,
                                 @RequestParam(value = "dateTime") String dateTime,
                                 @RequestParam(value = "calories") String calories,
                                 @RequestParam(value = "description") String description) {
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : valueOf(id),
                LocalDateTime.parse(dateTime), description, valueOf(calories));
        if (userMeal.isNew()) {
            super.create(userMeal);
        } else super.update(userMeal, userMeal.getId());
        return "redirect:meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String getBetween(Model model,
                             @RequestParam(value = "startDate") String startDate,
                             @RequestParam(value = "endDate") String endDate,
                             @RequestParam(value = "startTime") String startTime,
                             @RequestParam(value = "endTime") String endTime) {
        LocalDate startD = TimeUtil.parseLocalDate(startDate);
        LocalDate endD = TimeUtil.parseLocalDate(endDate);
        LocalTime startT = TimeUtil.parseLocalTime(startTime);
        LocalTime endT = TimeUtil.parseLocalTime(endTime);
        model.addAttribute("mealList",
                super.getBetween(startD, startT, endD, endT));
        return "mealList";
    }

}
