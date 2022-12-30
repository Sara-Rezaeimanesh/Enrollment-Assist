package ir.proprog.enrollassist.domain.studyRecord;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.domain.course.Course;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static java.lang.Math.min;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

public class StudyRecordTest {
    public static  String[] gl1 = {"Undergraduate", "Masters", "PHD"};

    public static GraduateLevel[] gl2 = {GraduateLevel.Undergraduate, GraduateLevel.Masters, GraduateLevel.PHD};

    public static Double[] grade = {8., 10., 11.5, 12., 13., 14., 15.};

    private static Stream<Arguments> parameters() {

        return Stream.of(
                Arguments.of("Undergraduate", GraduateLevel.Undergraduate, 8.0),
                Arguments.of("Undergraduate", GraduateLevel.Undergraduate, 10.0),
                Arguments.of("Undergraduate", GraduateLevel.Undergraduate, 11.5),
                Arguments.of("Undergraduate", GraduateLevel.Undergraduate, 12.0),
                Arguments.of("Undergraduate", GraduateLevel.Undergraduate, 13.0),
                Arguments.of("Undergraduate", GraduateLevel.Undergraduate, 14.0),
                Arguments.of("Undergraduate", GraduateLevel.Undergraduate, 15.0),

                Arguments.of("Undergraduate", GraduateLevel.Masters, 8.0),
                Arguments.of("Undergraduate", GraduateLevel.Masters, 10.0),
                Arguments.of("Undergraduate", GraduateLevel.Masters, 11.5),
                Arguments.of("Undergraduate", GraduateLevel.Masters, 12.0),
                Arguments.of("Undergraduate", GraduateLevel.Masters, 13.0),
                Arguments.of("Undergraduate", GraduateLevel.Masters, 14.0),
                Arguments.of("Undergraduate", GraduateLevel.Masters, 15.0),

                Arguments.of("Undergraduate", GraduateLevel.PHD, 8.0),
                Arguments.of("Undergraduate", GraduateLevel.PHD, 10.0),
                Arguments.of("Undergraduate", GraduateLevel.PHD, 11.5),
                Arguments.of("Undergraduate", GraduateLevel.PHD, 12.0),
                Arguments.of("Undergraduate", GraduateLevel.PHD, 13.0),
                Arguments.of("Undergraduate", GraduateLevel.PHD, 14.0),
                Arguments.of("Undergraduate", GraduateLevel.PHD, 15.0),

                Arguments.of("Masters", GraduateLevel.Undergraduate, 8.0),
                Arguments.of("Masters", GraduateLevel.Undergraduate, 10.0),
                Arguments.of("Masters", GraduateLevel.Undergraduate, 11.5),
                Arguments.of("Masters", GraduateLevel.Undergraduate, 12.0),
                Arguments.of("Masters", GraduateLevel.Undergraduate, 13.0),
                Arguments.of("Masters", GraduateLevel.Undergraduate, 14.0),
                Arguments.of("Masters", GraduateLevel.Undergraduate, 15.0),

                Arguments.of("Masters", GraduateLevel.Masters, 8.0),
                Arguments.of("Masters", GraduateLevel.Masters, 10.0),
                Arguments.of("Masters", GraduateLevel.Masters, 11.5),
                Arguments.of("Masters", GraduateLevel.Masters, 12.0),
                Arguments.of("Masters", GraduateLevel.Masters, 13.0),
                Arguments.of("Masters", GraduateLevel.Masters, 14.0),
                Arguments.of("Masters", GraduateLevel.Masters, 15.0),

                Arguments.of("Masters", GraduateLevel.PHD, 8.0),
                Arguments.of("Masters", GraduateLevel.PHD, 10.0),
                Arguments.of("Masters", GraduateLevel.PHD, 11.5),
                Arguments.of("Masters", GraduateLevel.PHD, 12.0),
                Arguments.of("Masters", GraduateLevel.PHD, 13.0),
                Arguments.of("Masters", GraduateLevel.PHD, 14.0),
                Arguments.of("Masters", GraduateLevel.PHD, 15.0),

                Arguments.of("PHD", GraduateLevel.Undergraduate, 8.0),
                Arguments.of("PHD", GraduateLevel.Undergraduate, 10.0),
                Arguments.of("PHD", GraduateLevel.Undergraduate, 11.5),
                Arguments.of("PHD", GraduateLevel.Undergraduate, 12.0),
                Arguments.of("PHD", GraduateLevel.Undergraduate, 13.0),
                Arguments.of("PHD", GraduateLevel.Undergraduate, 14.0),
                Arguments.of("PHD", GraduateLevel.Undergraduate, 15.0),

                Arguments.of("PHD", GraduateLevel.Masters, 8.0),
                Arguments.of("PHD", GraduateLevel.Masters, 10.0),
                Arguments.of("PHD", GraduateLevel.Masters, 11.5),
                Arguments.of("PHD", GraduateLevel.Masters, 12.0),
                Arguments.of("PHD", GraduateLevel.Masters, 13.0),
                Arguments.of("PHD", GraduateLevel.Masters, 14.0),
                Arguments.of("PHD", GraduateLevel.Masters, 15.0),

                Arguments.of("PHD", GraduateLevel.PHD, 8.0),
                Arguments.of("PHD", GraduateLevel.PHD, 10.0),
                Arguments.of("PHD", GraduateLevel.PHD, 11.5),
                Arguments.of("PHD", GraduateLevel.PHD, 12.0),
                Arguments.of("PHD", GraduateLevel.PHD, 13.0),
                Arguments.of("PHD", GraduateLevel.PHD, 14.0),
                Arguments.of("PHD", GraduateLevel.PHD, 15.0)
        );

    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void fail_test(String gl1, GraduateLevel gl2, Double grade) throws ExceptionList {
        Assumptions.assumeTrue(grade < min(GraduateLevel.valueOf(gl1).getMinValidGrade() , gl2.getMinValidGrade()));
        Course c = new Course("1234567", "DB", 2, gl1);
        StudyRecord studyRecord = new StudyRecord("00001", c, grade);
        assertFalse(studyRecord.isPassed(gl2));
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void pass_test(String gl1, GraduateLevel gl2, Double grade) throws ExceptionList {
        Assumptions.assumeTrue(grade > min(GraduateLevel.valueOf(gl1).getMinValidGrade() , gl2.getMinValidGrade()));
        Course c = new Course("1234567", "1", 2, gl1);
        StudyRecord studyRecord = new StudyRecord("00001", c, grade);
        assertTrue(studyRecord.isPassed(gl2));
    }
}