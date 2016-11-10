package edu.epam.mentoring.task10.controller;

import edu.epam.mentoring.task10.model.Employee;
import edu.epam.mentoring.task10.model.Project;
import edu.epam.mentoring.task10.model.Unit;
import edu.epam.mentoring.task10.service.EmployeeService;
import edu.epam.mentoring.task10.service.ProjectService;
import edu.epam.mentoring.task10.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by eugen on 11/10/2016.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UnitService unitService;

    @RequestMapping(value = "/{id}", method = GET, produces = "application/json")
    public @ResponseBody Employee get(@PathVariable long id) {
        return employeeService.get(id);
    }

    @RequestMapping(method = POST, produces = "application/json")
    public @ResponseBody long create(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @RequestMapping(method = PUT, produces = "application/json")
    public @ResponseBody long update(@RequestBody Employee employee) {
        Employee employeeTpUpdate = employeeService.get(employee.getId());
        employeeTpUpdate.setName(employee.getName());
        employeeTpUpdate.setAddress(employee.getAddress());
        employeeTpUpdate.setExternal(employee.isExternal());
        return employeeService.update(employeeTpUpdate);
    }

    @RequestMapping(value = "/{id}", method = DELETE, produces = "application/json")
    public @ResponseBody boolean delete(@PathVariable long id) {
        Employee employee = employeeService.get(id);
        employeeService.delete(employee);
        return true;
    }

    @RequestMapping(value = "/all", method = GET, produces = "application/json")
    public @ResponseBody List<Employee> getAll() {
        return employeeService.getAll();
    }

    @RequestMapping(value = "/{employeeId}/project/{projectId}", method = PUT, produces = "application/json")
    public boolean assignToProject(@PathVariable long employeeId, @PathVariable long projectId) {
        Employee employee = employeeService.get(employeeId);
        Project project = projectService.get(projectId);
        employee.getProjects().add(project);
        employeeService.update(employee);
        return true;
    }

    @RequestMapping(value = "/{employeeId}/unit/{unitId}", method = PUT, produces = "application/json")
    public boolean assignToUnit(@PathVariable long employeeId, @PathVariable long unitId) {
        Employee employee = employeeService.get(employeeId);
        Unit unit= unitService.get(unitId);
        employee.setUnit(unit);
        employeeService.update(employee);
        return true;
    }
}
