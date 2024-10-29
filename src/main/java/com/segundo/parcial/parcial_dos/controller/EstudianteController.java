package com.segundo.parcial.parcial_dos.controller;

import com.segundo.parcial.parcial_dos.entity.Subject;
import com.segundo.parcial.parcial_dos.entity.User;
import com.segundo.parcial.parcial_dos.repository.UserRepository;
import com.segundo.parcial.parcial_dos.service.SubjectService;
import com.segundo.parcial.parcial_dos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class EstudianteController {

    @Autowired
    private SubjectService subService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showIndex() {
        return "index";
    }

    @GetMapping("/subject/list")
    public String showSubjectList(Model model) {
        List<Subject> subjects = subService.findAll();
        model.addAttribute("subjects", subjects);
        return "subjectList";
    }

    @GetMapping("/loginPage")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/teacher/list")
    public String listTeachers(Model model) {
        List<User> teachers = userService.findTeachers();
        model.addAttribute("teachers", teachers);
        return "teacherList";
    }
}
