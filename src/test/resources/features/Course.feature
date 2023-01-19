Feature: Has the course created
  System want to know if the course created or not.

  Scenario: Course title is empty
    Given invalid course title
    Given courseNumber for course
    Given credit for course
    Given graduateLevel for course
    When Course created
    Then should throw Course must have a name error

  Scenario: Course courseNumber is empty
    Given title for course
    Given invalid empty courseNumber
    Given credit for course
    Given graduateLevel for course
    When Course created
    Then should throw Course number cannot be empty error

  Scenario: Course courseNumber must contain 7 numbers
    Given title for course
    Given invalid less than 7 numbers courseNumber
    Given credit for course
    Given graduateLevel for course
    When Course created
    Then should throw Course number must contain 7 numbers error

  Scenario: Course courseNumber must be number
    Given title for course
    Given invalid string courseNumber
    Given credit for course
    Given graduateLevel for course
    When Course created
    Then should throw Course number must be number error

  Scenario: Course credit is more than 4
    Given title for course
    Given courseNumber for course
    Given invalid course credits
    Given graduateLevel for course
    When Course created
    Then should throw Credit is not valid error

  Scenario: Course graduateLevel is not valid
    Given title for course
    Given courseNumber for course
    Given credit for course
    Given invalid course graduateLevel
    When Course created
    Then should throw Graduate level is not valid error