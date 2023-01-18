package ir.proprog.enrollassist.domain.course;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features ="C:\\Users\\98902\\Desktop\\UTCourse Material_fall_1401\\Test of Software\\HW\\Enrollment-Assist2\\src\\test\\resources\\features"
        ,glue = "ir.proprog.enrollassist.domain.course.MyStepdefs")
public class Runner {
}