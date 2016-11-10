package edu.epam.mentoring.task10.controller;

import edu.epam.mentoring.task10.model.Project;
import edu.epam.mentoring.task10.service.ProjectService;
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
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/test", method = POST, produces = "application/json")
    public @ResponseBody boolean testCreateSomeProjectsAndReturn() {
        Project project1 = new Project();
        project1.setName("project1");
        projectService.save(project1);

        Project project2 = new Project();
        project2.setName("project2");
        projectService.save(project2);

        Project project3 = new Project();
        project3.setName("project3");
        projectService.save(project3);

        return true;
    }

    @RequestMapping(value = "/all", method = GET, produces = "application/json")
    public @ResponseBody List<Project> getAll() {
        return projectService.getAll();
    }

    @RequestMapping(value = "/internal", method = GET, produces = "application/json")
    public @ResponseBody List<Project> getInternalEmployeesProjects() {
        return projectService.getAllInternalEmployeesProjects();
    }
}
