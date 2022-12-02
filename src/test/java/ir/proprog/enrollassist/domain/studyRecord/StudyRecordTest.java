package ir.proprog.enrollassist.domain.studyRecord;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.domain.course.Course;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static java.lang.Math.min;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(Theories.class)
public class StudyRecordTest {

    public StudyRecordTest() {}

    @DataPoints
    public static String[] gl1() {
        return new String[]{"Undergraduate", "Masters", "PHD"};
    }

    @DataPoints
    public static GraduateLevel[] gl2() {
        return new GraduateLevel[]{GraduateLevel.Undergraduate, GraduateLevel.Masters, GraduateLevel.PHD};
    }

    @DataPoints
    public static Double[] grade() {
        return new Double[]{8., 10., 11.5, 12., 13., 14., 15.};
    }

    @Theory
    public void fail_test(String gl1, GraduateLevel gl2, Double grade) throws ExceptionList {
        assumeTrue(grade < min(GraduateLevel.valueOf(gl1).getMinValidGrade() , gl2.getMinValidGrade()));
        Course c = new Course("1234567", "DB", 2, gl1);
        StudyRecord studyRecord = new StudyRecord("00001", c, grade);
        assertFalse(studyRecord.isPassed(gl2));
    }

    @Theory
    public void pass_test(String gl1, GraduateLevel gl2, Double grade) throws ExceptionList {
        assumeTrue(grade > min(GraduateLevel.valueOf(gl1).getMinValidGrade() , gl2.getMinValidGrade()));
        Course c = new Course("1234567", "1", 2, gl1);
        StudyRecord studyRecord = new StudyRecord("00001", c, grade);
        assertTrue(studyRecord.isPassed(gl2));
    }
}