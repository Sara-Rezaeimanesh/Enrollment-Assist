package ir.proprog.enrollassist.domain.course;

import ir.proprog.enrollassist.domain.EnrollmentRules.PrerequisiteNotTaken;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.program.Program;
import ir.proprog.enrollassist.domain.program.ProgramType;
import ir.proprog.enrollassist.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ir.proprog.enrollassist.Exception.ExceptionList;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import ir.proprog.enrollassist.domain.EnrollmentRules.EnrollmentRuleViolation;
import ir.proprog.enrollassist.domain.student.Student;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MyStepdefs {
    @Autowired
    Course CA;
    @Mock
    Course math1, phys1;
    @Mock
    CourseRepository cr;
    @Mock
    Program p;
    @Mock
    Student s;

    List<EnrollmentRuleViolation> violations;

    @Given("^Student Exists$")
    public void student_exists() throws Exception {
        s = Mockito.mock(Student.class);
        p = Mockito.mock(Program.class);
        s.addProgram(p);
    }

    @Given("Student wants to take a course")
    public void student_wants_to_take_course() throws Exception {
        math1 = Mockito.mock(Course.class);
        phys1 = Mockito.mock(Course.class);
        CA = new Course("1234567", "CA", 3, "Undergraduate").withPre(math1, phys1);
    }

    @Given("Course exists in student program")
    public void course_exists_in_student_program() {
        when(p.hasCourse(any())).thenReturn(true);
        when(p.getProgramType()).thenReturn(ProgramType.Major);
    }

    @Given("Student hasn't passed prereqs")
    public void student_hasnt_passed_prereqs()  throws  ExceptionList {
        when(s.hasPassed(any())).thenReturn(false);
    }

    @When("Student takes the course")
    public void student_takes_the_course() throws ExceptionList {
        violations = CA.canBeTakenBy(s);
    }

    @Then("An error message should pop up")
    public void check_error_is_present(){
        Assertions.assertTrue(violations.get(0) instanceof PrerequisiteNotTaken);
    }
}