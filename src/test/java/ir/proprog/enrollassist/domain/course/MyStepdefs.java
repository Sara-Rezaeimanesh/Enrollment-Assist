package ir.proprog.enrollassist.domain.course;

import ir.proprog.enrollassist.domain.EnrollmentRules.PrerequisiteNotTaken;
import ir.proprog.enrollassist.domain.GraduateLevel;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private String title;
    private String graduateLevel;
    private String courseNumber;
    private Integer credits;
    private Course course;
    private ExceptionList exception;


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

    @Given("Student hasn't passed prereqs")
    public void student_hasnt_passed_prereqs()  throws  ExceptionList {
        when(s.hasPassed(any())).thenReturn(false);
    }

    @Given("Student has passed prereqs")
    public void student_has_passed_prereqs()  throws  ExceptionList {
        when(s.hasPassed(any())).thenReturn(true);
    }

    @When("Student takes the course")
    public void student_takes_the_course() throws ExceptionList {
        violations = CA.canBeTakenBy(s);
    }

    @Then("A PrerequisiteNotTaken error message should pop up")
    public void check_error_is_present(){
        Assertions.assertTrue(violations.get(0) instanceof PrerequisiteNotTaken);
    }

    @Then("No PrerequisiteNotTaken error message should pop up")
    public void errors_array_should_be_empty(){
        assertEquals(0, violations.size());
    }

    //Course constructor
    @Given("invalid course title")
    public void set_invalid_title() throws Exception {
        title = "";
    }

    @Given("invalid empty courseNumber")
    public void set_invalid_empty_courseNumber() throws Exception {
        courseNumber = "";
    }

    @Given("invalid less than 7 numbers courseNumber")
    public void set_invalid_less_than_7_numberscourseNumber() throws Exception {
        courseNumber = "111";
    }

    @Given("invalid string courseNumber")
    public void set_invalid_string_courseNumber() throws Exception {
        courseNumber = "as";
    }

    @Given("invalid course credits")
    public void set_invalid_credit() throws Exception {
        credits = 5;
    }

    @Given("invalid course graduateLevel")
    public void set_invalid_graduateLevel() throws Exception {
        graduateLevel = "";
    }

    @Given("title for course")
    public void set_valid_title() throws Exception {
        title = "AP";
    }

    @Given("courseNumber for course")
    public void set_valid_courseNumber() throws Exception {
        courseNumber = "6666666";
    }

    @Given("credit for course")
    public void set_valid_credit() throws Exception {
        credits = 3;
    }

    @Given("graduateLevel for course")
    public void set_valid_graduateLevel() throws Exception {
        graduateLevel = "Undergraduate";
    }

    @When("Course created")
    public void create_course() {
        exception = assertThrows( ExceptionList.class,
                () -> {course = new Course(courseNumber, title, credits, graduateLevel);});
    }

    @Then("should throw Course must have a name error")
    public void course_must_have_a_name(){
        assertEquals("Course must have a name.", exception.getExceptions().get(0).getMessage());
    }

    @Then("should throw Course number must be number error")
    public void course_number_must_be_number(){
        assertEquals("Course number must be number.", exception.getExceptions().get(0).getMessage());
    }

    @Then("should throw Course number cannot be empty error")
    public void course_number_cannot_be_empty(){
        assertEquals("Course number cannot be empty.", exception.getExceptions().get(0).getMessage());
    }

    @Then("should throw Course number must contain 7 numbers error")
    public void course_number_must_contain_7_numbers(){
        assertEquals("Course number must contain 7 numbers.", exception.getExceptions().get(0).getMessage());
    }

    @Then("should throw Graduate level is not valid error")
    public void graduate_level_is_not_valid(){
        assertEquals("Graduate level is not valid.", exception.getExceptions().get(0).getMessage());
    }

    @Then("should throw Credit is not valid error")
    public void credit_is_not_valid(){
        assertEquals("Credit must be one of the following values: 0, 1, 2, 3, 4.", exception.getExceptions().get(0).getMessage());
    }

}