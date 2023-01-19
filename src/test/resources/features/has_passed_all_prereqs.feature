Feature: Has the student passed all prerequisites of a course?
  System want to know if the student can take a course.

  Scenario: Student has not passed the prereqs
    Given Student Exists
    Given Student wants to take a course
    Given Student hasn't passed prereqs
    When Student takes the course
    Then An PrerequisiteNotTaken error message should pop up

  Scenario: Student passed the prereqs
    Given Student Exists
    Given Student wants to take a course
    Given Student has passed prereqs
    When Student takes the course
    Then course should be added