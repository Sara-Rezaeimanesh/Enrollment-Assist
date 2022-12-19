package ir.proprog.enrollassist.controller.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.domain.course.AddCourseService;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseRepository cr;
    @MockBean
    private AddCourseService acs;
    private JacksonTester<CourseMajorView> jt;
    private CourseMajorView cmw;

    @BeforeEach
    public void setup() throws ExceptionList {
        Course c = new Course("9234567", "AD", 4, "Masters");
        cmw = new CourseMajorView(c, new HashSet<>(), new HashSet<>());
        ArrayList<Course> courses = new ArrayList<>() {{
            add(new Course("1234567", "AI", 4, "Masters"));
            add(new Course("1234568", "DL", 4, "Masters"));
            add(new Course("1234569", "NLP", 4, "Masters"));
        }};
        given(this.cr.findById(1L)).willReturn(Optional.ofNullable(courses.get(1)));
        given(this.cr.findAll()).willReturn(courses);
        given(this.acs.addCourse(any())).willReturn(c);

        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void allWithNoCoursesShouldReturnNoCourseViews() throws Exception {
        given(this.cr.findAll()).willReturn(new ArrayList<>());
        mockMvc.perform(get("/courses"))
                .andExpect(jsonPath("$[*]").isEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void allWithThreeCoursesShouldReturnAllCourseViews() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(jsonPath("$[*].courseTitle", containsInAnyOrder("AI", "DL", "NLP")))
                .andExpect(jsonPath("$[*].graduateLevel", containsInAnyOrder("Masters", "Masters", "Masters")))
                .andExpect(jsonPath("$[*].courseNumber.courseNumber", containsInAnyOrder("1234568", "1234567", "1234569")))
                .andExpect(jsonPath("$[*].courseCredits", containsInAnyOrder(4, 4, 4)))
                .andExpect(status().isOk());
    }

    @Test
    public void oneShouldReturnCourseViewNumberOne() throws Exception {
        mockMvc.perform(get("/courses/1"))
                .andExpect(jsonPath("$.courseNumber.courseNumber").value("1234568"))
                .andExpect(jsonPath("$.courseTitle").value("DL"))
                .andExpect(jsonPath("$.graduateLevel").value("Masters"))
                .andExpect(jsonPath("$.courseCredits").value(4))
                .andExpect(status().isOk());
    }

    @Test
    public void oneWithInvalidIdShouldReturnResponseStatusException() throws Exception {
        mockMvc.perform(get("/courses/3"))
                .andExpect(status().reason(containsString("Course not found")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addNewCourseShouldReturnCourseView() throws Exception {
        mockMvc.perform(post("/courses/").contentType(MediaType.APPLICATION_JSON)
                .content(jt.write(cmw).getJson()))
                .andExpect(jsonPath("$.courseNumber.courseNumber").value("9234567"))
                .andExpect(jsonPath("$.courseTitle").value("AD"))
                .andExpect(jsonPath("$.graduateLevel").value("Masters"))
                .andExpect(jsonPath("$.courseCredits").value(4))
                .andExpect(status().isOk());
    }
}