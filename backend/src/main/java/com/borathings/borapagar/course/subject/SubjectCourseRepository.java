package com.borathings.borapagar.course.subject;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectCourseRepository
        extends JpaRepository<SubjectCourseEntity, SubjectCourseKey> {
    public List<SubjectCourseEntity> findByCourseId(Long courseId);

    public Optional<SubjectCourseEntity> findByCourseIdAndSubjectId(Long courseId, Long subjectId);

    public void deleteByCourseIdAndSubjectId(Long courseId, Long subjectId);
}
