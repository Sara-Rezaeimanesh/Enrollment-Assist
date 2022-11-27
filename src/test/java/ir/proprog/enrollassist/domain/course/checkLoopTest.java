package ir.proprog.enrollassist.domain.course;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.controller.course.CourseMajorView;
import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CheckLoopTest {

    private Course c;
    private Course c1;
    private Course c2;
    private Set<Course> pre_c1;
    private Set<Course> pre_c2;

    private AddCourseService acs;
    private CourseMajorView cmw;
    private Set<Long> pres;

    private CourseRepository courseRepository;
    private ProgramRepository programRepository;

    @BeforeEach
    public void setup() {
        c = Mockito.mock(Course.class);
        c1 = Mockito.mock(Course.class);
        c2 = Mockito.mock(Course.class);

        courseRepository = Mockito.mock(CourseRepository.class);
        when(courseRepository.findById(0L)).thenReturn(Optional.ofNullable(c));
        when(courseRepository.findById(1L)).thenReturn(Optional.ofNullable(c1));
        when(courseRepository.findById(2L)).thenReturn(Optional.ofNullable(c2));

        programRepository = Mockito.mock(ProgramRepository.class);
        Set<Course> pre_c = new HashSet<>() {{add(c1);}{add(c2);}};
        when(c.getPrerequisites()).thenReturn(pre_c);
        when(c.getCourseNumber()).thenReturn(new CourseNumber());
        when(c.getTitle()).thenReturn("DA");
        when(c.getId()).thenReturn(0L);
        when(c1.getTitle()).thenReturn("DM");
        when(c1.getId()).thenReturn(1L);
        when(c2.getTitle()).thenReturn("DS");
        when(c2.getId()).thenReturn(2L);
        when(c.getGraduateLevel()).thenReturn(GraduateLevel.Masters);

    }

    @Test
    public void no_loop_exists_returns_nothing() throws ExceptionList {
        Set<Long> prog_dummy = new HashSet<>();
        pres = new HashSet<>();
        cmw = new CourseMajorView(c, pres, prog_dummy);
        acs = new AddCourseService(courseRepository, programRepository);
        acs.addCourse(cmw);
    }

    @Test
    public void loop_exists_returns_exception() {
        pre_c1 = new HashSet<>(){{add(c);}};
        when(c1.getPrerequisites()).thenReturn(pre_c1);
        pre_c2 = new  HashSet<>(){};
        when(c2.getPrerequisites()).thenReturn(pre_c2);

        Set<Long> prog_dummy = new HashSet<>();
        pres = new HashSet<>();
        cmw = new CourseMajorView(c, pres, prog_dummy);
        acs = new AddCourseService(courseRepository, programRepository);

        ExceptionList e_expected = new ExceptionList();
        List<Exception> es = new ArrayList<>(){{
            add(new Exception(String.format("%s has made a loop in prerequisites.", "DM")));
        }};
        e_expected.addExceptions(es);

        ExceptionList exception = assertThrows(ExceptionList.class, () -> acs.addCourse(cmw));
        assertTrue(check_exceptions_list_equals(e_expected, exception));
    }

    @Test
    public void two_loops_exist_returns_two_exception() {
        pre_c1 = new HashSet<>(){{add(c);}};
        when(c1.getPrerequisites()).thenReturn(pre_c1);

        pre_c2 = new  HashSet<>(){{add(c);}};
        when(c2.getPrerequisites()).thenReturn(pre_c2);

        Set<Long> prog_dummy = new HashSet<>();
        pres = new HashSet<>();
        cmw = new CourseMajorView(c, pres, prog_dummy);
        acs = new AddCourseService(courseRepository, programRepository);

        ExceptionList e_expected = new ExceptionList();
        List<Exception> es = new ArrayList<>(){
            {add(new Exception(String.format("%s has made a loop in prerequisites.", "DA")));}
            {add(new Exception(String.format("%s has made a loop in prerequisites.", "DM")));}
            {add(new Exception(String.format("%s has made a loop in prerequisites.", "DS")));}
            {add(new Exception(String.format("%s has made a loop in prerequisites.", "DA")));}
        };
        e_expected.addExceptions(es);

        ExceptionList exception = assertThrows(ExceptionList.class, () -> acs.addCourse(cmw));
        assertTrue(check_exceptions_list_equals(e_expected, exception));
    }

    private boolean check_exceptions_list_equals(ExceptionList e_expected, ExceptionList exception) {
        if (e_expected.getExceptions().size() != exception.getExceptions().size())
            return false;

        boolean isEqual = true;
        for(int i = 0; i < e_expected.getExceptions().size(); i++) {
            isEqual &= (e_expected.getExceptions().get(i).getMessage().equals(exception.getExceptions().get(i).getMessage()));
        }
        return isEqual;
    }
}