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

    public static Double[] grades = {8., 10., 11.5, 12., 13., 14., 15.};

    public static <T> Stream<T> appendToStream(Stream<? extends T> stream, T element) {
        return Stream.concat(stream, Stream.of(element));
    }

    private static Stream<Arguments> parameters() {
        Stream<Arguments> arguments = Stream.of();
        for(String stu_gl : gl1)
            for(GraduateLevel course_gl : gl2)
                for(Double grade : grades)
                    arguments = appendToStream(arguments, Arguments.of(stu_gl, course_gl, grade));
        return arguments;
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