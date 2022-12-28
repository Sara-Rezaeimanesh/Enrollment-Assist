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
    private static final int MAX_CREDIT_FIRST_SEM = 20;
    private static final int MAX_CREDIT_FAILED = 14;
    private static final int MAX_ERR = 0;
    private final int errorClass;
    private final int expected_length;
    private final ArrayList<Integer> exp_total;
    public CheckValidGPALimitTest(GraduateLevel gl, double gpa, int totalCredit, int totalCreditTaken, int cls, int errNum, ArrayList<Integer> exp_fail_max) throws Exception {
        Student stu = Mockito.mock(Student.class);
        when(stu.getGraduateLevel()).thenReturn(gl);
        when(stu.calculateGPA()).thenReturn(new Grade(gpa));
        when(stu.getTotalTakenCredits()).thenReturn(totalCreditTaken);

        int [] credits = {totalCredit/7, totalCredit/7, totalCredit/7,
                totalCredit/7, totalCredit/7, totalCredit/7, totalCredit/7};
        for(int i = 0; totalCredit%7 > 0; i++) {
            credits[i] += 1;
            totalCredit -= 1;
        }

        el = new EnrollmentList("12345", stu);
        el.addSections(
                new Section(new Course("1234567", "AI", credits[0], "Masters"), "1"),
                new Section(new Course("1234568", "AI", credits[1], "Masters"), "2"),
                new Section(new Course("1234569", "AI", credits[2], "Masters"), "3"),
                new Section(new Course("1234561", "AI", credits[3], "Masters"), "4"),
                new Section(new Course("1234562", "AI", credits[4], "Masters"), "5"),
                new Section(new Course("1234563", "AI", credits[5], "Masters"), "6"),
                new Section(new Course("1234564", "AI", credits[6], "Masters"), "7")
        );
        errorClass = cls;
        expected_length = errNum;
        exp_total = exp_fail_max;
    }
    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object [][]{
                {GraduateLevel.Undergraduate, 20, 10, 0, MIN_ERR, 1, new ArrayList<>(){{add(0);}} },
                {GraduateLevel.Undergraduate, 0, 14, 1, MIN_ERR, 0, new ArrayList<>(){{add(0);}} },
                {GraduateLevel.Undergraduate, 0, 16, 0, MAX_ERR, 1, new ArrayList<>(){{add(MAX_CREDIT_FAILED);}}},
                {GraduateLevel.Undergraduate, 18, 21, 0, MAX_ERR, 0, new ArrayList<>(){{add(MAX_CREDIT_FIRST_SEM);}}},
                {GraduateLevel.Undergraduate, 16, 20, 0, 0, 0, new ArrayList<>()},
                {GraduateLevel.Masters, 16, 12, 0, 0, 0, new ArrayList<>()},
                {GraduateLevel.Undergraduate, 0, 20, 0, MAX_ERR, 1, new ArrayList<>(){{add(MAX_CREDIT_FAILED);}}},
                {GraduateLevel.PHD, 18, 6, 0, MIN_ERR, 0, new ArrayList<>(){{add(0);}}},
                {GraduateLevel.Masters, 20, 7, 0, MIN_ERR, 1, new ArrayList<>(){{add(0);}}},
                {GraduateLevel.Undergraduate, 0, 21, 0, MAX_ERR, 1, new ArrayList<>(){{add(MAX_CREDIT_FIRST_SEM);}}},
                {GraduateLevel.Masters, 20, 10, 0, MIN_ERR, 0, new ArrayList<>(){{add(0);}}},
                {GraduateLevel.Undergraduate, 10, 15, 0, MAX_ERR, 1, new ArrayList<>(){{add(MAX_CREDIT_FAILED);}}},
                {GraduateLevel.Undergraduate, 16, 21, 0, MAX_ERR, 1, new ArrayList<>(){{add(MAX_CREDIT_FIRST_SEM);}}},
                {GraduateLevel.Undergraduate, 16, 28, 0, MAX_ERR, 2, new ArrayList<>(){{add(MAX_CREDIT_FIRST_SEM);add(24);}}},
                {GraduateLevel.Masters, 16, 14, 0, MAX_ERR, 1, new ArrayList<>(){{add(12);}}}
        });
    }
    @Test
    public void limitTest() {
        List<EnrollmentRuleViolation> evs = el.checkValidGPALimit();
        assertEquals(expected_length, evs.size());
        int i = 0;
        for(EnrollmentRuleViolation v : evs) {
            if (errorClass == MIN_ERR)
                assertTrue(v instanceof MinCreditsRequiredNotMet);
            else
                assertEquals(v.toString(), (new MaxCreditsLimitExceeded(exp_total.get(i))).toString());
            i++;
        }
    }
}