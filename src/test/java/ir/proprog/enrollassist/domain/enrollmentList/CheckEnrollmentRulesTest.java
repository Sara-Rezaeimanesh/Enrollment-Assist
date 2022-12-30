package ir.proprog.enrollassist.domain.enrollmentList;
import ir.proprog.enrollassist.domain.EnrollmentRules.*;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.major.Major;
import ir.proprog.enrollassist.domain.program.Program;
import ir.proprog.enrollassist.domain.section.ExamTime;
import ir.proprog.enrollassist.domain.section.PresentationSchedule;
import ir.proprog.enrollassist.domain.section.Section;
import ir.proprog.enrollassist.domain.student.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

enum CourseException{
    prerequisite, hasAlrPassed, requestedTwice, examTimeConflict, scheduleConflict, maxCreditLimit
}

public class CheckEnrollmentRulesTest {
    private static EnrollmentList el;
    private static Major m;
    private static Program p;
    private static Student s;
    private static List<List<Integer>> sec_params;
    public static List<Section> test_sections;
    private static List<Course> courses;
    private static List<Section> sections;

    public CourseException expected_error;
    public int expected_length;




    private static Stream<Arguments> parameters() {
        sec_params = new ArrayList<>(){{
            add(new ArrayList<>(){{add(1);}});
            add(new ArrayList<>(){{add(2);}});
            add(new ArrayList<>(){{add(4);add(4);}});
            add(new ArrayList<>(){{add(4);add(5);add(6);}});
            add(new ArrayList<>(){{add(7);add(8);}});
            add(new ArrayList<>(){{add(8);add(9);}});
            add(new ArrayList<>(){{add(4);add(5);}});
        }};

        return Stream.of(
                Arguments.of(CourseException.prerequisite, 2, sec_params.get(0)),
                Arguments.of(CourseException.hasAlrPassed, 1, sec_params.get(1)),
                Arguments.of(CourseException.requestedTwice, 1, sec_params.get(2)),
                Arguments.of(CourseException.maxCreditLimit, 1, sec_params.get(3)),
                Arguments.of(CourseException.examTimeConflict, 1, sec_params.get(4)),
                Arguments.of(CourseException.examTimeConflict, 1, sec_params.get(4)),
                Arguments.of(CourseException.scheduleConflict, 1, sec_params.get(5)),
                Arguments.of(null, 0, sec_params.get(6))
        );
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void enrollmentTest(CourseException error, int length, List<Integer> sec_numbers) throws Exception {
        m = new Major("10", "CS", "Engineering");
        p = new Program(m, "Masters", 1, 20, "Major");
        courses = new ArrayList<>(){{
            add(new Course("1234567", "AI", 4, "Masters"));
            add(new Course("1234568", "DL", 4, "Masters"));
            add(new Course("1234569", "NLP", 4, "Masters"));
            add(new Course("1234570", "ML", 4, "Masters"));
            add(new Course("1234571", "IS", 4, "Masters"));
            add(new Course("1234572", "LA", 4, "Masters"));
            add(new Course("1234573", "DB", 4, "Masters"));
            add(new Course("1234574", "SE", 4, "Masters"));
            add(new Course("1234575", "AA", 4, "Masters"));
            add(new Course("1234576", "DA", 4, "Masters"));
            add(new Course("1234577", "DLD", 4, "Masters"));
            add(new Course("0000000", "dump", 4, "Masters"));
        }};
        courses.get(1).setPrerequisites(new HashSet<>(Arrays.asList(courses.get(0), courses.get(3))));
        courses.get(3).setPrerequisites(new HashSet<>(Arrays.asList(courses.get(4), courses.get(5))));
        courses.get(4).setPrerequisites(new HashSet<>(List.of(courses.get(0))));
        courses.get(2).setPrerequisites(new HashSet<>(Arrays.asList(courses.get(0), courses.get(1))));
        p.addCourse(courses.get(0), courses.get(1), courses.get(2), courses.get(3), courses.get(4), courses.get(6), courses.get(11));

        sections = new ArrayList<>();
        test_sections = new ArrayList<>();
        sections.add(new Section(courses.get(3), "1"));
        sections.add(new Section(courses.get(0), "2"));
        sections.add(new Section(courses.get(4), "6"));
        sections.add(new Section(courses.get(5), "3"));
        sections.add(new Section(courses.get(6), "4"));
        sections.add(new Section(courses.get(7), "5"));
        sections.add(new Section(courses.get(8), "6"));
        sections.add(new Section(courses.get(9), "7"));
        sections.add(new Section(courses.get(10), "8"));
        sections.add(new Section(courses.get(11),"4" ));
        s = new Student("810198576", "Masters");
        s.setGrade("00001", courses.get(5), 9);
        s.setGrade("00002", courses.get(0), 19);
        s.addProgram(p);
        el = new EnrollmentList("list", s);
        el.addSections(sections.get(9));
        sections.get(6).setExamTime(new ExamTime());
        sections.get(7).setExamTime(new ExamTime());
        sections.get(7).setPresentationSchedule(new HashSet<>(List.of(new PresentationSchedule())));
        sections.get(8).setPresentationSchedule(new HashSet<>(List.of(new PresentationSchedule())));

        sections.get(3).setExamTime(new ExamTime("2021-06-21T08:00", "2021-06-21T11:00"));
        sections.get(4).setExamTime(new ExamTime("2021-06-21T14:00", "2021-06-21T17:00"));
        PresentationSchedule ps1 = new PresentationSchedule("Saturday", "09:00", "10:30");
        PresentationSchedule ps2 = new PresentationSchedule("Sunday", "09:00", "10:30");

        sections.get(3).setPresentationSchedule(new HashSet<>(List.of(ps1)));
        sections.get(4).setPresentationSchedule(new HashSet<>(List.of(ps2)));

        for(Integer i : sec_numbers)
            test_sections.add(sections.get(i-1));
        this.expected_error = error;
        this.expected_length = length;

        for(Section s : test_sections)
            el.addSections(s);
        List<EnrollmentRuleViolation> evs =  el.checkEnrollmentRules();
        for(EnrollmentRuleViolation v : evs)
            switch (expected_error) {
                case prerequisite -> assertTrue(v instanceof PrerequisiteNotTaken);
                case hasAlrPassed -> assertTrue(v instanceof RequestedCourseAlreadyPassed);
                case requestedTwice -> assertTrue(v instanceof CourseRequestedTwice);
                case maxCreditLimit -> assertTrue(v instanceof MaxCreditsLimitExceeded);
                case examTimeConflict -> assertTrue(v instanceof ExamTimeCollision);
                case scheduleConflict -> assertTrue(v instanceof ConflictOfClassSchedule);
            }
        assertEquals(expected_length, evs.size());
    }

    @AfterAll
    static void tearDown(){
        el = null;
        m = null;
        p = null;
        test_sections = null;
        s = null;
        sec_params = null;
        courses = null;
        sections = null;
    }
}