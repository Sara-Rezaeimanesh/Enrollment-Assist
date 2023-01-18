Feature: Has the student passed all prerequisites of a course?
  System want to know if the student can take a course.

  Scenario: Student has not passed the prereqs
    Given Student Exists
    Given Student wants to take a course
    Given Course exists in student program
    Given Student hasn't passed prereqs
    When Student takes the course
    Then An error message should pop up
