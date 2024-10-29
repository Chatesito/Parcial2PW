package com.segundo.parcial.parcial_dos.service;

import com.segundo.parcial.parcial_dos.entity.Subject;
import com.segundo.parcial.parcial_dos.entity.Teacher;
import com.segundo.parcial.parcial_dos.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }

    public Optional<Subject> findByTeacherAndSchedule(Teacher teacher, LocalTime startTime, LocalTime endTime) {
        return subjectRepository.findByTeacherAndStartTimeAndEndTime(teacher, startTime, endTime);
    }

    public List<Subject> findSubjectsByTeacher(Teacher id) {
        return subjectRepository.findByTeacher(id);
    }

}
