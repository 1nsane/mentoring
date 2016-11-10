package edu.epam.mentoring.task10.controller;

import edu.epam.mentoring.task10.model.Unit;
import edu.epam.mentoring.task10.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by eugen on 11/10/2016.
 */
@Controller
@RequestMapping("/unit")
public class UnitController {
    @Autowired
    private UnitService unitService;

    @RequestMapping(value = "/test", method = POST, produces = "application/json")
    public @ResponseBody boolean testCreateSomeUnitsAndReturn() {
        Unit unit1 = new Unit();
        unit1.setName("unit1");
        unitService.save(unit1);

        Unit unit2 = new Unit();
        unit2.setName("unit2");
        unitService.save(unit2);

        Unit unit3 = new Unit();
        unit3.setName("unit3");
        unitService.save(unit3);

        return true;
    }

    @RequestMapping(value = "/all", method = GET, produces = "application/json")
    public @ResponseBody List<Unit> getAll() {
        return unitService.getAll();
    }
}
