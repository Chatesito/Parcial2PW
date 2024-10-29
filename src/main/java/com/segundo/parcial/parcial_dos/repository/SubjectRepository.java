package com.segundo.parcial.parcial_dos.repository;

import com.segundo.parcial.parcial_dos.entity.Subject;
import com.segundo.parcial.parcial_dos.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    // Para que un profe no tenga clases superpuestas
    Optional<Subject> findByTeacherAndStartTimeAndEndTime(Teacher teacher, LocalTime startTime, LocalTime endTime);

    // Profesor especifico
    List<Subject> findByTeacher(Teacher teacher);

    List<Subject> findByTeacherId(Long teacherId);
}
