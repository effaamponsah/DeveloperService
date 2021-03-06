package io.turnatabl.DevelopersService.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.turnatabl.DevelopersService.dao.ProjectLoggingDAO;
import io.turnatabl.DevelopersService.models.ProjectLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class ProjectLoggingDaoImpl implements ProjectLoggingDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ApiOperation("ADD HOURS")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/addHours")
    @Override
    public void addHours(@RequestBody ProjectLogging projectLogging) {
        this.jdbcTemplate.update(
                "insert into projectlogging(project_hours,logged_date, emp_id, project_id,volunteering_hours) values(?,?::date,?,?,?)",
                 projectLogging.getProject_hours(), projectLogging.getLogged_date(), projectLogging.getEmp_id(), projectLogging.getProject_id(), projectLogging.getVolunteering_hours()
        );
    }


    @ApiOperation("GET ALL PROJECTS LOGS")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/log")
    @Override
    public List<ProjectLogging> getAllProjectLogging(){
        return this.jdbcTemplate.query("select projectlogging.projectlogging_id, projectlogging.logged_date, projectlogging.project_hours, projectlogging.emp_id, projectlogging.project_id, projectlogging.vacation, projectlogging.sick, projectlogging.volunteering_hours, projects.title, employees.emp_name from projectlogging INNER JOIN projects ON projects.project_id = projectlogging.project_id INNER JOIN employees on employees.emp_id =projectlogging.emp_id",
                BeanPropertyRowMapper.newInstance(ProjectLogging.class));
    }


    @ApiOperation("ADD SICK")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/addSick")
    @Override
    public void addSick(@RequestBody ProjectLogging projectLogging) {
        this.jdbcTemplate.update(
                "insert into projectlogging(logged_date, emp_id,sick) values(?::date,?,?)",
               projectLogging.getLogged_date(), projectLogging.getEmp_id(), projectLogging.getSick()
        );
    }

    @ApiOperation("ADD VACATION")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/addVacation")
    @Override
    public void addVacation(@RequestBody ProjectLogging projectLogging) {
        this.jdbcTemplate.update(
                "insert into projectlogging(logged_date, emp_id,vacation) values(?::date,?,?)",
                projectLogging.getLogged_date(), projectLogging.getEmp_id(), projectLogging.getVacation()
        );
    }

    @ApiOperation("Get an assigned project logging for a Developer By id")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/projectlogged/dev/{emp_id}")
    @Override
    public List<ProjectLogging> getCurrentProjectByDevId(@PathVariable("emp_id") Integer emp_id) {

//        List<ProjectLogging>  projectLoggings = jdbcTemplate.query("select projectlogging.projectlogging_id, projectlogging.logged_date, projectlogging.project_hours, projectlogging.emp_id, projectlogging.project_id, projectlogging.vacation, projectlogging.sick, projectlogging.volunteering_hours, projects.title from projectlogging INNER JOIN projects ON projects.project_id = projectlogging.project_id INNER JOIN employees on employees.emp_id =projectlogging.emp_id where employees.emp_id = ?",
//                new Object[]{emp_id},
//                BeanPropertyRowMapper.newInstance(ProjectLogging.class));
//        return projectLoggings.get(0);

        return this.jdbcTemplate.query("select projectlogging.projectlogging_id, projectlogging.logged_date, projectlogging.project_hours, projectlogging.emp_id, projectlogging.project_id, projectlogging.vacation, projectlogging.sick, projectlogging.volunteering_hours, projects.title from projectlogging INNER JOIN projects ON projects.project_id = projectlogging.project_id INNER JOIN employees on employees.emp_id =projectlogging.emp_id where employees.emp_id = ?",
                new Object[]{emp_id},
                BeanPropertyRowMapper.newInstance(ProjectLogging.class));
    }





//    @ApiOperation("GET ASSIGNED TASK BY DEVELOPER_ID")
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//    @GetMapping("/dev/assign/{emp_id}")
//    @Override
//    public Develop getTaskByID(@PathVariable("emp_id") Integer emp_id) {
////        return this.jdbcTemplate.query("SELECT projects.title from projects INNER JOIN currentprojects ON " +
////                        "currentprojects.project_id = projects.project_id INNER JOIN employees ON currentprojects.emp_id" +
////                        " = employees.emp_id where employees.emp_id = ?",
////                new Object[]{emp_id + "%"},
////                BeanPropertyRowMapper.newInstance(Develop.class));
//        List<Develop> develops = jdbcTemplate.query("SELECT projects.title from projects INNER JOIN currentprojects ON currentprojects.project_id = projects.project_id INNER JOIN employees ON currentprojects.emp_id = employees.emp_id where employees.emp_id = ?",
//                new Object[]{emp_id},
//                BeanPropertyRowMapper.newInstance(Develop.class));
//        return develops.get(0);
//
//    }


}
