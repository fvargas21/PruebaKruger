package sasf.net.kevaluacion.util;

/**
 * Global constants for the Kevaluacion application
 */
public final class Constants {
    
    // Private constructor to prevent instantiation
    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    // ========== VALIDATION MESSAGES ==========
    
    // Employee validation messages
    public static final String EMPLOYEE_FULL_NAME_REQUIRED = "Full name is required";
    public static final String EMPLOYEE_EMAIL_REQUIRED = "Email is required";
    public static final String EMPLOYEE_EMAIL_INVALID = "Email should be valid";
    
    // Course validation messages
    public static final String COURSE_NAME_REQUIRED = "Course name is required";
    public static final String COURSE_MAX_CAPACITY_REQUIRED = "Max capacity is required";
    public static final String COURSE_MAX_CAPACITY_POSITIVE = "Max capacity must be positive";
    
    // Session validation messages
    public static final String SESSION_SCHEDULED_DATE_REQUIRED = "Scheduled date is required";
    public static final String SESSION_SCHEDULED_DATE_FUTURE = "Scheduled date must be in the future";
    public static final String SESSION_CAPACITY_REQUIRED = "Capacity is required";
    public static final String SESSION_CAPACITY_POSITIVE = "Capacity must be positive";
    
    // Enrollment validation messages
    public static final String ENROLLMENT_EMPLOYEE_ID_REQUIRED = "Employee ID is required";
    public static final String ENROLLMENT_GRADE_REQUIRED = "Grade is required";
    public static final String ENROLLMENT_GRADE_RANGE = "Grade must be between 0 and 10";
    
    // ========== SWAGGER DOCUMENTATION MESSAGES ==========
    
    // Employee operations
    public static final String EMPLOYEE_TAG_NAME = "Employees";
    public static final String EMPLOYEE_TAG_DESCRIPTION = "Employee management operations";
    
    public static final String EMPLOYEE_CREATE_SUMMARY = "Create a new employee";
    public static final String EMPLOYEE_CREATE_DESCRIPTION = "Creates a new employee in the system";
    public static final String EMPLOYEE_GET_ALL_SUMMARY = "Get all employees";
    public static final String EMPLOYEE_GET_ALL_DESCRIPTION = "Retrieves a list of all employees";
    public static final String EMPLOYEE_GET_BY_ID_SUMMARY = "Get employee by ID";
    public static final String EMPLOYEE_GET_BY_ID_DESCRIPTION = "Retrieves a specific employee by their ID";
    public static final String EMPLOYEE_UPDATE_SUMMARY = "Update employee";
    public static final String EMPLOYEE_UPDATE_DESCRIPTION = "Updates an existing employee";
    
    // Course operations
    public static final String COURSE_TAG_NAME = "Courses";
    public static final String COURSE_TAG_DESCRIPTION = "Course management operations";
    
    public static final String COURSE_CREATE_SUMMARY = "Create a new course";
    public static final String COURSE_CREATE_DESCRIPTION = "Creates a new course in the system";
    public static final String COURSE_GET_ALL_SUMMARY = "Get all courses with sessions";
    public static final String COURSE_GET_ALL_DESCRIPTION = "Retrieves a list of all courses with their sessions";
    public static final String COURSE_GET_BY_ID_SUMMARY = "Get course by ID";
    public static final String COURSE_GET_BY_ID_DESCRIPTION = "Retrieves a specific course by its ID";
    public static final String COURSE_UPDATE_SUMMARY = "Update course";
    public static final String COURSE_UPDATE_DESCRIPTION = "Updates an existing course";
    
    // Session operations
    public static final String SESSION_CREATE_SUMMARY = "Create a new session for a course";
    public static final String SESSION_CREATE_DESCRIPTION = "Creates a new session for an existing course";
    public static final String SESSION_GET_SESSIONS_SUMMARY = "Get sessions for a course";
    public static final String SESSION_GET_SESSIONS_DESCRIPTION = "Retrieves all sessions for a specific course";
    public static final String SESSION_UPDATE_SUMMARY = "Update session";
    public static final String SESSION_UPDATE_DESCRIPTION = "Updates an existing session";
    
    // Enrollment operations
    public static final String ENROLLMENT_TAG_NAME = "Enrollments";
    public static final String ENROLLMENT_TAG_DESCRIPTION = "Enrollment management operations";
    
    public static final String ENROLLMENT_CREATE_SUMMARY = "Enroll employee in session";
    public static final String ENROLLMENT_CREATE_DESCRIPTION = "Enrolls an employee in a specific session";
    public static final String ENROLLMENT_UPDATE_GRADE_SUMMARY = "Update enrollment grade";
    public static final String ENROLLMENT_UPDATE_GRADE_DESCRIPTION = "Updates the grade for a specific enrollment";
    public static final String ENROLLMENT_GET_BY_EMPLOYEE_SUMMARY = "Get employee enrollments";
    public static final String ENROLLMENT_GET_BY_EMPLOYEE_DESCRIPTION = "Retrieves all enrollments for a specific employee";
    public static final String ENROLLMENT_GET_BY_SESSION_SUMMARY = "Get session enrollments";
    public static final String ENROLLMENT_GET_BY_SESSION_DESCRIPTION = "Retrieves all enrollments for a specific session";
    
    // ========== API RESPONSE MESSAGES ==========
    
    // Success responses
    public static final String RESPONSE_201_CREATED = "Created successfully";
    public static final String RESPONSE_200_SUCCESS = "Operation completed successfully";
    public static final String RESPONSE_200_LIST_RETRIEVED = "List retrieved successfully";
    
    // Error responses
    public static final String RESPONSE_400_INVALID_INPUT = "Invalid input data";
    public static final String RESPONSE_404_NOT_FOUND = "Resource not found";
    public static final String RESPONSE_409_CONFLICT = "Resource conflict";
    
    // Specific error messages
    public static final String EMPLOYEE_EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String EMPLOYEE_ALREADY_ENROLLED = "Employee already enrolled";
    public static final String SESSION_FULL = "Session is full";
    public static final String CAPACITY_EXCEEDS_LIMIT = "Capacity exceeds course limit";
    
    // ========== PARAMETER DESCRIPTIONS ==========
    
    public static final String PARAM_EMPLOYEE_ID = "Employee ID";
    public static final String PARAM_COURSE_ID = "Course ID";
    public static final String PARAM_SESSION_ID = "Session ID";
    public static final String PARAM_ENROLLMENT_ID = "Enrollment ID";
    
    // ========== BUSINESS ERROR CODES ==========
    
    public static final String ERROR_EMAIL_ALREADY_EXISTS = "EMAIL_ALREADY_EXISTS";
    public static final String ERROR_ALREADY_ENROLLED = "ALREADY_ENROLLED";
    public static final String ERROR_SESSION_FULL = "SESSION_FULL";
    public static final String ERROR_INVALID_CAPACITY = "INVALID_CAPACITY";
    public static final String ERROR_RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
    public static final String ERROR_BUSINESS_ERROR = "BUSINESS_ERROR";
    public static final String ERROR_VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String ERROR_DATA_INTEGRITY_ERROR = "DATA_INTEGRITY_ERROR";
    public static final String ERROR_INTERNAL_ERROR = "INTERNAL_ERROR";
    public static final String ERROR_EMPLOYEE_NOT_FOUND = "EMPLOYEE_NOT_FOUND";
    public static final String ERROR_COURSE_NOT_FOUND = "COURSE_NOT_FOUND";
    public static final String ERROR_SESSION_NOT_FOUND = "SESSION_NOT_FOUND";
    public static final String ERROR_ENROLLMENT_NOT_FOUND = "ENROLLMENT_NOT_FOUND";
    public static final String ERROR_ENROLLMENT_ALREADY_EXISTS = "ENROLLMENT_ALREADY_EXISTS";
    public static final String ERROR_INVALID_DATE_RANGE = "INVALID_DATE_RANGE";
    public static final String ERROR_SESSION_CAPACITY_EXCEEDED = "SESSION_CAPACITY_EXCEEDED";
    
    // ========== SPECIFIC ERROR MESSAGES ==========
    
    public static final String EMPLOYEE_NOT_FOUND = "Employee not found";
    public static final String COURSE_NOT_FOUND = "Course not found";
    public static final String SESSION_NOT_FOUND = "Session not found";
    public static final String ENROLLMENT_NOT_FOUND = "Enrollment not found";
}
