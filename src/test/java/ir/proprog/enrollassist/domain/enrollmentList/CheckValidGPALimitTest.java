package ir.proprog.enrollassist.domain.enrollmentList;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.domain.EnrollmentRules.*;
import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.section.Section;
import ir.proprog.enrollassist.domain.student.Student;
import ir.proprog.enrollassist.domain.studyRecord.Grade;
import ir.proprog.enrollassist.domain.studyRecord.StudyRecord;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.lang.Math.min;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class CheckValidGPALimitTest {
    private final EnrollmentList el;
    private static final int MIN_ERR = 1;
    private static final int MAX_ERR = 0;
    private final int errorClass;
    private final int expected_length;
    public CheckValidGPALimitTest(GraduateLevel gl, double gpa, int totalCredit, int totalCreditTaken, int cls, int errNum) throws Exception {
        Student stu = Mockito.mock(Student.class);
        when(stu.getGraduateLevel()).thenReturn(gl);
        when(stu.calculateGPA()).thenReturn(new Grade(gpa));
        when(stu.getTotalTakenCredits()).thenReturn(totalCreditTaken);

        el = new EnrollmentList("12345", stu);
        el.addSections(
            new Section(new Course("1234567", "AI", totalCredit/7, "Masters"), "1"),
            new Section(new Course("1234568", "AI", totalCredit/7, "Masters"), "2"),
            new Section(new Course("1234569", "AI", totalCredit/7, "Masters"), "3"),
            new Section(new Course("1234561", "AI", totalCredit/7, "Masters"), "4"),
            new Section(new Course("1234562", "AI", totalCredit/7, "Masters"), "5"),
            new Section(new Course("1234563", "AI", totalCredit/7, "Masters"), "6"),
            new Section(new Course("1234564", "AI", totalCredit/7+totalCredit%7, "Masters"), "7")
        );
        errorClass = cls;
        expected_length = errNum;
    }
    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object [][]{
                    {GraduateLevel.Undergraduate, 20, 10, 0, MIN_ERR, 1},
                    {GraduateLevel.Masters, 20, 7, 0, MIN_ERR, 1},
                    {GraduateLevel.Undergraduate, 0, 21, 0, MAX_ERR, 1},
                    {GraduateLevel.Masters, 20, 10, 0, MIN_ERR, 0},
                    {GraduateLevel.Undergraduate, 10, 15, 0, MAX_ERR, 1},
                    {GraduateLevel.Undergraduate, 16, 21, 0, MAX_ERR, 1},
                    {GraduateLevel.Undergraduate, 16, 28, 0, MAX_ERR, 2},
                    {GraduateLevel.Masters, 16, 14, 0, MAX_ERR, 1}
        });
    }
    @Test
    public void limitTest() {
        List<EnrollmentRuleViolation> evs = el.checkValidGPALimit();
        assertEquals(expected_length, evs.size());
        for(EnrollmentRuleViolation v : evs)
            if(errorClass == MIN_ERR)
                assertTrue(v instanceof MinCreditsRequiredNotMet);
            else
                assertTrue(v instanceof MaxCreditsLimitExceeded);

    }



}