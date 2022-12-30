package ir.proprog.enrollassist.domain.enrollmentList;

import ir.proprog.enrollassist.domain.EnrollmentRules.*;
import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.section.Section;
import ir.proprog.enrollassist.domain.student.Student;
import ir.proprog.enrollassist.domain.studyRecord.Grade;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CheckValidGPALimitTest {
    private static final int MIN_ERR = 1;
    private static final int MAX_CREDIT_FIRST_SEM = 20;
    private static final int MAX_CREDIT_FAILED = 14;
    private static final int MAX_ERR = 0;

    private static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of(GraduateLevel.Undergraduate, 20, 10, 0, MIN_ERR, 1, new ArrayList<>(){{add(0);}} ),
                Arguments.of(GraduateLevel.Undergraduate, 0, 14, 1, MIN_ERR, 0, new ArrayList<>(){{add(0);}} ),
                Arguments.of(GraduateLevel.Undergraduate, 0, 16, 0, MAX_ERR, 1, new ArrayList<>(){{add(MAX_CREDIT_FAILED);}}),
                Arguments.of(GraduateLevel.Undergraduate, 18, 21, 0, MAX_ERR, 0, new ArrayList<>(){{add(MAX_CREDIT_FIRST_SEM);}}),
                Arguments.of(GraduateLevel.Undergraduate, 16, 20, 0, 0, 0, new ArrayList<>()),
                Arguments.of(GraduateLevel.Masters, 16, 12, 0, 0, 0, new ArrayList<>()),
                Arguments.of(GraduateLevel.Undergraduate, 0, 20, 0, MAX_ERR, 1, new ArrayList<>(){{add(MAX_CREDIT_FAILED);}}),
                Arguments.of(GraduateLevel.PHD, 18, 6, 0, MIN_ERR, 0, new ArrayList<>(){{add(0);}}),
                Arguments.of(GraduateLevel.Masters, 20, 7, 0, MIN_ERR, 1, new ArrayList<>(){{add(0);}}),
                Arguments.of(GraduateLevel.Undergraduate, 0, 21, 0, MAX_ERR, 1, new ArrayList<>(){{add(MAX_CREDIT_FIRST_SEM);}}),
                Arguments.of(GraduateLevel.Masters, 20, 10, 0, MIN_ERR, 0, new ArrayList<>(){{add(0);}}),
                Arguments.of(GraduateLevel.Undergraduate, 10, 15, 0, MAX_ERR, 1, new ArrayList<>(){{add(MAX_CREDIT_FAILED);}}),
                Arguments.of(GraduateLevel.Undergraduate, 16, 21, 0, MAX_ERR, 1, new ArrayList<>(){{add(MAX_CREDIT_FIRST_SEM);}}),
                Arguments.of(GraduateLevel.Undergraduate, 16, 28, 0, MAX_ERR, 2, new ArrayList<>(){{add(MAX_CREDIT_FIRST_SEM);add(24);}}),
                Arguments.of(GraduateLevel.Masters, 16, 14, 0, MAX_ERR, 1, new ArrayList<>(){{add(12);}})
        );
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void limitTest(GraduateLevel gl, double gpa, int totalCredit, int totalCreditTaken, int cls, int errNum, ArrayList<Integer> exp_fail_max) throws Exception {
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

        EnrollmentList el = new EnrollmentList("12345", stu);
        el.addSections(
                new Section(new Course("1234567", "AI", credits[0], "Masters"), "1"),
                new Section(new Course("1234568", "AI", credits[1], "Masters"), "2"),
                new Section(new Course("1234569", "AI", credits[2], "Masters"), "3"),
                new Section(new Course("1234561", "AI", credits[3], "Masters"), "4"),
                new Section(new Course("1234562", "AI", credits[4], "Masters"), "5"),
                new Section(new Course("1234563", "AI", credits[5], "Masters"), "6"),
                new Section(new Course("1234564", "AI", credits[6], "Masters"), "7")
        );

        List<EnrollmentRuleViolation> evs = el.checkValidGPALimit();
        assertEquals(errNum, evs.size());
        int i = 0;
        for(EnrollmentRuleViolation v : evs) {
            if (cls == MIN_ERR)
                assertTrue(v instanceof MinCreditsRequiredNotMet);
            else
                assertEquals(v.toString(), (new MaxCreditsLimitExceeded(exp_fail_max.get(i))).toString());
            i++;
        }
    }
}