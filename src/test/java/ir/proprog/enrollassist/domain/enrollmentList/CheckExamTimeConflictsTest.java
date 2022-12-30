package ir.proprog.enrollassist.domain.enrollmentList;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.section.ExamTime;
import ir.proprog.enrollassist.domain.section.Section;
import ir.proprog.enrollassist.domain.student.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;



public class CheckExamTimeConflictsTest {

    private static Stream<Arguments> parameters() {
        List<List<Integer>> sec_params;
        sec_params = new ArrayList<>(){{
            add(new ArrayList<>(){{add(-1); add(-1); add(-1); add(-1); add(-1); add(-1);}});//0
            add(new ArrayList<>(){{add(0); add(3); add(-1); add(-1); add(-1); add(-1);}});//1
            add(new ArrayList<>(){{add(-1); add(-1); add(0); add(3); add(-1); add(-1);}});//2
            add(new ArrayList<>(){{add(0); add(3); add(4); add(7); add(-1); add(-1);}});//3
            add(new ArrayList<>(){{add(0); add(3); add(1); add(4); add(-1); add(-1);}});//4
            add(new ArrayList<>(){{add(-1); add(-1); add(-1); add(-1); add(0); add(3);}});//5
            add(new ArrayList<>(){{add(-1); add(-1); add(1); add(4); add(0); add(3);}});//6
            add(new ArrayList<>(){{add(-1); add(-1); add(1); add(4); add(5); add(8);}});//7
            add(new ArrayList<>(){{add(1); add(4);  add(-1); add(-1); add(0); add(3);}});//8
            add(new ArrayList<>(){{add(0); add(3); add(-1); add(-1); add(4); add(7);}});//9

            add(new ArrayList<>(){{add(0); add(3); add(1); add(4); add(5); add(8);}});//10 تداخل داشتن
            add(new ArrayList<>(){{add(0); add(3); add(5); add(8); add(1); add(4);}});//11
            add(new ArrayList<>(){{add(0); add(3); add(4); add(7); add(5); add(8);}});//12
            add(new ArrayList<>(){{add(0); add(3); add(1); add(4); add(2); add(5);}});//13
            add(new ArrayList<>(){{add(1); add(4); add(3); add(6); add(0); add(2);}});//14
            add(new ArrayList<>(){{add(3); add(6); add(1); add(4);  add(0); add(2);}});//15
            add(new ArrayList<>(){{add(0); add(2); add(3); add(6); add(1); add(4);}});//16
            add(new ArrayList<>(){{add(0); add(1); add(2); add(3); add(4); add(5);}});//17 تداخل نداشتن
        }};

        return Stream.of(
                Arguments.of(sec_params.get(0), 1, 0),
                Arguments.of(sec_params.get(1), 1, 0),
                Arguments.of(sec_params.get(0), 2, 0),
                Arguments.of(sec_params.get(1), 2, 0),
                Arguments.of(sec_params.get(2), 2, 0),
                Arguments.of(sec_params.get(3), 2, 0),
                Arguments.of(sec_params.get(4), 2, 1),
                Arguments.of(sec_params.get(0), 3, 0),
                Arguments.of(sec_params.get(5), 3, 0),
                Arguments.of(sec_params.get(2), 3, 0),

                Arguments.of(sec_params.get(6), 3, 1),
                Arguments.of(sec_params.get(7), 3, 0),
                Arguments.of(sec_params.get(1), 3, 0),
                Arguments.of(sec_params.get(1), 2, 0),
                Arguments.of(sec_params.get(8), 3, 1),
                Arguments.of(sec_params.get(9), 3, 0),
                Arguments.of(sec_params.get(3), 3, 0),
                Arguments.of(sec_params.get(4), 3, 1),
                Arguments.of(sec_params.get(10), 3, 1),
                Arguments.of(sec_params.get(11), 3, 1),

                Arguments.of(sec_params.get(12), 3, 1),
                Arguments.of(sec_params.get(13), 3, 3),
                Arguments.of(sec_params.get(14), 3, 2),
                Arguments.of(sec_params.get(15), 3, 2),
                Arguments.of(sec_params.get(16), 3, 2),
                Arguments.of(sec_params.get(17), 3, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void checkExamTimeConflictsTest(List<Integer> sec_numbers, int numSec,int numError) throws Exception {
        List<Course> courses = new ArrayList<>() {{
            add(new Course("1234567", "AI", 4, "Masters"));
            add(new Course("1234568", "DL", 4, "Masters"));
            add(new Course("1234569", "NLP", 4, "Masters"));
        }};
        List<Section> sections = new ArrayList<>();
        sections.add(new Section(courses.get(0), "1"));//null
        sections.add(new Section(courses.get(1), "2"));
        sections.add(new Section(courses.get(2), "3"));

        List<String> times = new ArrayList<>() {{
            add("2021-06-21T08:00");//0
            add("2021-06-21T09:00");//1
            add("2021-06-21T10:00");//2
            add("2021-06-21T11:00");//3
            add("2021-06-21T12:00");//4
            add("2021-06-21T13:00");//5
            add("2021-06-21T14:00");//6
            add("2021-06-21T15:00");//7
            add("2021-06-21T16:00");//8
            add("2021-06-21T17:00");//9
            add("2021-06-21T18:00");//10
            add("2021-06-21T19:00");//11
            add("2021-06-21T20:00");//12
        }};
        Student stu = new Student("810100000", "Masters");
        EnrollmentList list = new EnrollmentList("12345", stu);
        for(int i = 0 ; i < numSec ; i++)
            if(sec_numbers.get(2*i) != -1 || sec_numbers.get(2*i + 1) != -1) {
                sections.get(i).setExamTime(new ExamTime(times.get(sec_numbers.get(2*i)), times.get(sec_numbers.get(2*i+1))));
            }
        list.addSections(sections.get(0), sections.get(1), sections.get(2));
        Assertions.assertEquals(list.checkExamTimeConflicts().size(), numError);
    }
}