package ir.proprog.enrollassist.controller.student;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.controller.section.SectionView;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.section.Section;
import ir.proprog.enrollassist.domain.student.Student;
import ir.proprog.enrollassist.domain.student.StudentNumber;
import ir.proprog.enrollassist.domain.user.User;
import ir.proprog.enrollassist.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
class StudentControllerTest {

    private StudentController stuCtrl;

    private StudentRepository studentRepository;

    private CourseRepository courseRepository;

    private SectionRepository sectionRepository;

    private EnrollmentListRepository enrollmentListRepository;

    private UserRepository userRepository;

    private List<StudentView> listViewStudent;

    private List<Student> listStudent;

    private Student stu;

    StudentControllerTest() {
    }

    @BeforeEach
    public void setup() throws ExceptionList {
        studentRepository = Mockito.mock(StudentRepository.class);
        stu = Mockito.mock(Student.class);
        sectionRepository = Mockito.mock(SectionRepository.class);
        userRepository = Mockito.mock(UserRepository.class);

        listStudent = new ArrayList<>();
        Student s1 = new Student("810100000", "Masters");
        Student s2 = new Student("810100001");
        Student s3 = new Student("810100002");
        listStudent.add(s1);
        listStudent.add(s2);
        listStudent.add(s3);
        listViewStudent = new ArrayList<>();
        listViewStudent.add(new StudentView(s1));
        listViewStudent.add(new StudentView(s2));
        listViewStudent.add(new StudentView(s3));
        stuCtrl = new StudentController(studentRepository, courseRepository, sectionRepository, enrollmentListRepository, userRepository);
    }

    @Test
    public void all_test_expect_student_view(){
        when(studentRepository.findAll()).thenReturn(listStudent);
        assertThat(listViewStudent)
                .usingRecursiveComparison()
                .isEqualTo(stuCtrl.all());
    }

    @Test
    public void one_test_expect_student_view(){
        when(studentRepository.findByStudentNumber(new StudentNumber("810100000"))).thenReturn(Optional.ofNullable(listStudent.get(0)));
        assertThat(listViewStudent.get(0))
                .usingRecursiveComparison()
                .isEqualTo(stuCtrl.one("810100000"));
    }

    @Test
    public void one_test_expect_student_not_found(){
        Throwable exception = assertThrows(ResponseStatusException.class,  () -> stuCtrl.one("810155555"));
        assertEquals("404 NOT_FOUND \"Student not found\"", exception.getMessage());
    }

    @Test
    public void find_takeable_test_section_view() throws ExceptionList {
        List<Section> sections = new ArrayList<>();
        sections.add(new Section(new Course("1234567", "AI", 4, "Masters"), "1"));

        List<SectionView> sectionViews = new ArrayList<>();
        sectionViews.add(new SectionView(sections.get(0)));

        when(stu.getStudentNumber()).thenReturn(new StudentNumber("810100000"));
        when(stu.getTakeableSections(sectionRepository.findAll())).thenReturn(sections);

        when(studentRepository.findByStudentNumber(new StudentNumber(stu.getStudentNumber().getNumber()))).thenReturn(Optional.of(stu));

        assertThat(sectionViews)
                .usingRecursiveComparison()
                .isEqualTo(stuCtrl.findTakeableSectionsByMajor(stu.getStudentNumber().getNumber()));
    }

    @Test
    public void addStudent_test_user_not_found(){
        Throwable exception = assertThrows(ResponseStatusException.class,  () -> stuCtrl.addStudent(listViewStudent.get(0)));
        assertEquals(HttpStatus.NOT_FOUND + " \"User with id: " + listViewStudent.get(0).getUserId() + " was not found.\"", exception.getMessage());
    }

    @Test
    public void addStudent_test_student_already_exist(){
        when(userRepository.findByUserId(listViewStudent.get(0).getUserId())).thenReturn(Optional.of(new User("Narges", "10")));
        when(studentRepository.findByStudentNumber(new StudentNumber("810100000"))).thenReturn(Optional.ofNullable(listStudent.get(0)));
        Throwable exception = assertThrows(ResponseStatusException.class,  () -> stuCtrl.addStudent(listViewStudent.get(0)));
        assertEquals(HttpStatus.BAD_REQUEST + " \"This student already exists.\"", exception.getMessage());
    }

    @Test
    public void addStudent_test_successful() {
        listViewStudent.get(0).setUserId("1");
        when(userRepository.findByUserId(listViewStudent.get(0).getUserId())).thenReturn(Optional.of(new User("Narges", "1")));

        listViewStudent.get(0).setUserId("1");
        assertThat(listViewStudent.get(0))
                .usingRecursiveComparison()
                .isEqualTo(stuCtrl.addStudent(listViewStudent.get(0)));
    }
}