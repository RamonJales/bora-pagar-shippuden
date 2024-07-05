package com.borathings.borapagar.course.subject;

import com.borathings.borapagar.course.CourseService;
import com.borathings.borapagar.subject.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class SubjectCourseService {
    @Autowired private SubjectCourseRepository subjectCourseRepository;

    @Autowired private CourseService courseService;
    @Autowired private SubjectService subjectService;

    public SubjectCourseEntity addSubjectToCourseSchedule(
            Long courseId, SubjectCourseEntity subjectCourseEntity) {
        Long subjectId = subjectCourseEntity.getKeyId().getSubjectId();

        subjectCourseEntity.setKeyId(new SubjectCourseKey(subjectId, courseId));
        subjectCourseEntity.setSubject(subjectService.findById(subjectId));
        subjectCourseEntity.setCourse(courseService.getCourseById(courseId));

        Optional<SubjectCourseEntity> subjectCourse =
                subjectCourseRepository.findByCourseIdAndSubjectId(courseId, subjectId);
        if (subjectCourse.isPresent()) {
            throw new DuplicateKeyException("Subject already exists in course schedule");
        }

        return subjectCourseRepository.save(subjectCourseEntity);
    }

    public SubjectCourseEntity getSubjectInfoFromCourseSchedule(Long courseId, Long subjectId) {
        Optional<SubjectCourseEntity> subjectCourse =
                subjectCourseRepository.findByCourseIdAndSubjectId(courseId, subjectId);

        if (subjectCourse.isEmpty()) {
            throw new EntityNotFoundException("Subject not found in course schedule");
        }
        return subjectCourse.get();
    }

    public List<SubjectCourseEntity> getAllSubjectsFromCourseSchedule(Long courseId) {
        return subjectCourseRepository.findByCourseId(courseId);
    }

    public SubjectCourseEntity updateSubjectInfoFromCourseSchedule(
            Long courseId, Long subjectId, SubjectCourseEntity subjectCourseEntity) {
        subjectCourseEntity.setCourse(courseService.getCourseById(courseId));
        subjectCourseEntity.setSubject(subjectService.findById(subjectId));
        getSubjectInfoFromCourseSchedule(courseId, subjectId);
        return subjectCourseRepository.save(subjectCourseEntity);
    }

    public void deleteSubjectFromCourseSchedule(Long courseId, Long subjectId) {
        subjectCourseRepository.deleteByCourseIdAndSubjectId(courseId, subjectId);
    }
}
