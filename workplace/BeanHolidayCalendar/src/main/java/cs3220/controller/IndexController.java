package cs3220.controller;

import cs3220.DataComponent;
import cs3220.model.HolidaysModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Single controller handling all Holiday Calendar routes.
 *
 * Demonstrates:
 *   - @Component DI via @Autowired DataComponent
 *   - @PathVariable for update and delete operations
 */
@Controller
public class IndexController {

    @Autowired
    private DataComponent dataComponent;

    // ----------------------------------------------------------------
    // INDEX — display all holidays sorted by date
    // ----------------------------------------------------------------

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("holidays", dataComponent.getAllSorted());
        return "index";
    }

    // ----------------------------------------------------------------
    // ADD HOLIDAY
    // ----------------------------------------------------------------

    @GetMapping("/addHoliday")
    public String addHolidayForm(Model model) {
        model.addAttribute("error", "");
        model.addAttribute("holidayName", "");
        return "addHoliday";
    }

    @PostMapping("/addHoliday")
    public String addHolidaySubmit(
            @RequestParam("day") String day,
            @RequestParam("month") String month,
            @RequestParam("year") String year,
            @RequestParam("holidayName") String holidayName,
            Model model) {

        // Validation: holiday name must not be blank
        if (holidayName == null || holidayName.trim().isEmpty()) {
            model.addAttribute("error", "All Fields are required");
            model.addAttribute("day", day);
            model.addAttribute("month", month);
            model.addAttribute("year", year);
            model.addAttribute("holidayName", "");
            return "addHoliday";
        }

        LocalDate date = LocalDate.of(
                Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day));

        // Duplicate date check
        if (dataComponent.dateExists(date)) {
            model.addAttribute("error", "Holiday Exist");
            model.addAttribute("day", day);
            model.addAttribute("month", month);
            model.addAttribute("year", year);
            model.addAttribute("holidayName", holidayName.trim());
            return "addHoliday";
        }

        dataComponent.addHoliday(new HolidaysModel(date.toString(), holidayName.trim()));

        // PRG: redirect prevents re-submission on browser refresh
        return "redirect:/";
    }

    // ----------------------------------------------------------------
    // UPDATE HOLIDAY — @PathVariable required by assignment
    // ----------------------------------------------------------------

    @GetMapping("/updateHoliday/{id}")
    public String updateHolidayForm(@PathVariable("id") String id, Model model) {
        Optional<HolidaysModel> opt = dataComponent.findById(id);
        if (opt.isEmpty()) return "redirect:/";

        HolidaysModel h = opt.get();
        model.addAttribute("holiday", h);
        model.addAttribute("day",   String.valueOf(h.getHolidayDate().getDayOfMonth()));
        model.addAttribute("month", String.valueOf(h.getHolidayDate().getMonthValue()));
        model.addAttribute("year",  String.valueOf(h.getHolidayDate().getYear()));
        model.addAttribute("holidayName", "");   // empty → template falls back to holiday.holiday
        model.addAttribute("error", "");
        return "updateHoliday";
    }

    @PostMapping("/updateHoliday/{id}")
    public String updateHolidaySubmit(
            @PathVariable("id") String id,
            @RequestParam("day") String day,
            @RequestParam("month") String month,
            @RequestParam("year") String year,
            @RequestParam("holidayName") String holidayName,
            Model model) {

        Optional<HolidaysModel> opt = dataComponent.findById(id);
        if (opt.isEmpty()) return "redirect:/";

        HolidaysModel h = opt.get();

        // Validation: holiday name required
        if (holidayName == null || holidayName.trim().isEmpty()) {
            model.addAttribute("holiday", h);
            model.addAttribute("day", day);
            model.addAttribute("month", month);
            model.addAttribute("year", year);
            model.addAttribute("holidayName", ""); // blank so template shows error state
            model.addAttribute("error", "All Fields are required");
            return "updateHoliday";
        }

        LocalDate newDate = LocalDate.of(
                Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day));

        // Mutate the existing object in-place (it lives in the DataComponent list)
        h.setHolidayDate(newDate);
        h.setHoliday(holidayName.trim());

        return "redirect:/";
    }

    // ----------------------------------------------------------------
    // DELETE HOLIDAY — @PathVariable required by assignment
    // ----------------------------------------------------------------

    @GetMapping("/deleteHoliday/{id}")
    public String deleteHoliday(@PathVariable("id") String id) {
        dataComponent.deleteById(id);
        return "redirect:/";
    }
}
