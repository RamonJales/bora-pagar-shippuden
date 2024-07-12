package com.borathings.borapagar.course.subject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest
@AutoConfigureWebMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubjectCourseTests {

    @Test
    public void shouldAddSubjectToSchedule() {}

    @Test
    public void addToScheduleShouldVerifyIfEntitiesExist() {}

    @Test
    public void addToScheduleShouldVerifyIfThereExistsDuplicates() {}

    @Test
    public void addToScheduleShouldValidateFields() {}

    @Test
    public void shouldGetAllSubjectsFromSchedule() {}

    @Test
    public void getAllSubjectsShouldVerifyIfEntitiesExist() {}

    @Test
    public void shouldGetSubjectInfoFromSchedule() {}

    @Test
    public void getSubjectInfoShouldVerifyIfEntitiesExist() {}

    @Test
    public void shouldUpdateSubjectInfoFromSchedule() {}

    @Test
    public void updateSubjectInfoShouldVerifyIfEntitiesExist() {}

    @Test
    public void updateSubjectInfoShouldValidateFields() {}

    @Test
    public void shouldRemoveSubjectFromSchedule() {}
}
