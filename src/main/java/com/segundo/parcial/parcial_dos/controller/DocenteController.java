package com.segundo.parcial.parcial_dos.controller;

import com.segundo.parcial.parcial_dos.entity.Subject;
import com.segundo.parcial.parcial_dos.service.SubjectService;
import com.segundo.parcial.parcial_dos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teacher")
public class DocenteController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    // MOstrar asignatura del docente actual
    @GetMapping("/subjects")
    public String listTeacherSubjects(Model model) {
        Long id = userService.getCurrentUser().getId();
        List<Subject> subjects = subjectService.findSubjectsByTeacher(id);

        model.addAttribute("subjects", subjects);
        return "teacherSubjectList"; // Vista para listar las asignaturas
    }

    // Horario de edicion de horario
    @GetMapping("/subject/edit/{id}")
    public String editSubjectSchedule(@PathVariable Long id, Model model) {
        Optional<Subject> subjectOptional = subjectService.findById(id);

        if (subjectOptional.isPresent()) {
            Subject subject = subjectOptional.get();
            model.addAttribute("subject", subject);
            return "editSubjectSchedule"; // Vista para editar el horario
        } else {
            return "redirect:/teacher/subjects";
        }
    }

    // Guardar cambios al cambiar asignatura
    @PostMapping("/subject/updateSchedule")
    public String updateSubjectSchedule(@ModelAttribute("subject") Subject subject, Errors error) {

        if (error.hasErrors()) {
            return "editSubjectSchedule";
        }

        // Guardamos los cambios en la asignatura
        subjectService.save(subject);
        return "redirect:/teacher/subjects";
    }
}
