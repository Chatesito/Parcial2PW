package com.segundo.parcial.parcial_dos.controller;

import com.segundo.parcial.parcial_dos.entity.Subject;
import com.segundo.parcial.parcial_dos.entity.Teacher;
import com.segundo.parcial.parcial_dos.repository.UserRepository;
import com.segundo.parcial.parcial_dos.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/subject")
public class RectorController {


    @Autowired
    private SubjectService subService;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/add")
    public String addSubject(Model model) {
        Subject newSub = new Subject();
        newSub.setTeacher(new Teacher());

        model.addAttribute("newSub", newSub);
        model.addAttribute("valor", "Añadir");

        return "modifySubjectForm";
    }

    @PostMapping("/insert")
    public String insertSubject(@Valid @ModelAttribute("newSub") Subject subject, Errors error, Model model) {

        Optional<Subject> optionalSubject = subService.findByTeacherAndSchedule(subject.getTeacher(), subject.getStartTime(), subject.getEndTime());

        if (optionalSubject.isPresent()) {
            model.addAttribute("teacherError", "El profesor ya tiene una clase a esa hora");
            model.addAttribute("valor", subject.getId() == null ? "Añadir" : "Modificar");
            return "modifySubjectForm";
        }

        if (error.hasErrors()) {
            model.addAttribute("valor", subject.getId() == null ? "Añadir" : "Modificar");
            return "modifySubjectForm";
        } else {
            subService.save(subject);
            return "redirect:/subject/list";
        }
    }

    @GetMapping("/modify/{id}")
    public String modifySubject(@PathVariable Long id, Model model) {
        Subject modSubject = subService.findById(id).orElse(null);

        model.addAttribute("newSub", modSubject);
        model.addAttribute("valor", "Modificar");
        return "modifySubjectForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subService.delete(id);
        return "redirect:/subject/list";
    }
}
