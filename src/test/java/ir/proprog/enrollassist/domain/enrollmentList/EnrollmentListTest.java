package ir.proprog.enrollassist.domain.enrollmentList;

import ir.proprog.enrollassist.domain.EnrollmentRules.EnrollmentRuleViolation;
import ir.proprog.enrollassist.domain.EnrollmentRules.PrerequisiteNotTaken;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.major.Major;
import ir.proprog.enrollassist.domain.program.Program;
import ir.proprog.enrollassist.domain.section.Section;
import ir.proprog.enrollassist.domain.student.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnrollmentListTest {
    private static EnrollmentList el;
    @BeforeAll
    static void setup() throws Exception {
        Major m = new Major("10", "CS", "Engineering");
        Program p = new Program(m, "Masters", 0, 20, "Major");
        Course c = new Course("1234567", "AI", 3, "Undergraduate");
        Course c2 = new Course("1234568", "DL", 3, "Masters");
        Course c4 = new Course("1234570", "ML", 3, "Masters");
        Course c5 = new Course("1234571", "IS", 3, "Masters");
        Course c6 = new Course("1234572", "LA", 3, "Masters");
        Course c3 = new Course("1234569", "NLP", 3, "PHD");
        Set<String> hs = new HashSet<String>();
        c2.setPrerequisites(new HashSet (Arrays.asList(c, c4)));
        c4.setPrerequisites(new HashSet (Arrays.asList(c5, c6)));
        c5.setPrerequisites(new HashSet (Arrays.asList(c6)));
        c3.setPrerequisites(new HashSet (Arrays.asList(c, c2)));
        p.addCourse(c, c2, c3, c4, c5, c6);
        Section se = new Section(c4, "1");
//        Section se2 = new Section(c2, "2");
//        Section se3 = new Section(c3, "3");
        Student s = new Student("810198576", "Masters");
        s.setGrade("00001", c6, 9);
        s.setGrade("00002", c, 19);
        s.addProgram(p);
        el = new EnrollmentList("list", s);
        el.addSections(se);
    }

    @Test
    public void add_course_fails() {
        List<EnrollmentRuleViolation> evs =  el.checkEnrollmentRules();
        assertTrue(evs.get(0) instanceof PrerequisiteNotTaken && evs.get(1) instanceof PrerequisiteNotTaken);
    }
}