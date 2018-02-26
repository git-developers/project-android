package com.example.software.utils;

/**
 * Created by jafeth on 3/31/17.
 */

public class Const {

    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_WARNING = 2;
    public static final int STATUS_ERROR = 3;

    public static final int VERSION = 3;
    public static final int HTTP_STATUS_200 = 200;
    public static final int SOCKET_TIMEOUT = 4000;
    public static final String IS_LOGGED = "is_logged";
    public static final String LAST_ACTIVITY = "last_activity";
    public static final String ROLE_FATHER = "ROLE_FATHER";
    public static final String ROLE_TEACHER = "ROLE_TEACHER";

//    public static final String HTTP = "http://ff0c1c96.ngrok.io/";
    public static final String HTTP = "http://grupocazaperu.com/";
    public static final String BASE_ROUTE = HTTP + "tesis/web/app_dev.php/api/";
    public static final String ROUTE_LOGIN = BASE_ROUTE + "user/login";
    public static final String ROUTE_COURSES = BASE_ROUTE + "courses/list-by-user";
    public static final String ROUTE_COURSES_LIST_STUDENTS = BASE_ROUTE + "user/list-users-by-course";
    public static final String ROUTE_MODULE_CREATE_NOTICE = BASE_ROUTE + "notices/create-notice";
    public static final String ROUTE_MODULE_GRADES_CREATE = BASE_ROUTE + "grades/create-grade";
    public static final String ROUTE_MODULE_TASK_CREATE = BASE_ROUTE + "task/create";
    public static final String ROUTE_MODULE_TASK_LIST = BASE_ROUTE + "task/list-by-course";
    public static final String ROUTE_MODULE_GRADES_LIST = BASE_ROUTE + "grades/list-grade";
    public static final String ROUTE_MODULE_ATTENDANCE_CREATE = BASE_ROUTE + "attendance/create";
    public static final String ROUTE_MODULE_ATTENDANCE_GETSTATUS = BASE_ROUTE + "attendance/get-status";
    public static final String ROUTE_LIST_CHILDREN = BASE_ROUTE + "user/list-children";
    public static final String ROUTE_COURSES_BY_USERNAME = BASE_ROUTE + "courses/list-courses-by-username";
    public static final String ROUTE_LIST_NOTICE_FATHER = BASE_ROUTE + "notices/list-notice-by-father";
    public static final String ROUTE_REPORTS_PIECHART = BASE_ROUTE + "reports/piechart";
    public static final String ROUTE_REPORTS_STEPPEDAREACHART = BASE_ROUTE + "reports/steppedareachart";
    public static final String ROUTE_REPORTS_GAUGE = BASE_ROUTE + "reports/gauge";

    public static final String CONTENT_TYPE = "application/json";
    public static final String PREFERENCES = "preferences";

    public static final String PARAMETER_USERNAME = "username";
    public static final String PARAMETER_STUDENT_USERNAME = "student_username";
    public static final String PARAMETER_PASSWORD = "password";
    public static final String PARAMETER_REGISTRATION_ID = "registration_id";
    public static final String PARAMETER_MESSAGE = "message";
    public static final String PARAMETER_NOTICE_TYPE = "notice_type";
    public static final String PARAMETER_COURSE_ID = "course_id";
    public static final String PARAMETER_STATUS = "estado";
    public static final String PARAMETER_BIMESTER = "bimester";
    public static final String PARAMETER_GRADE_MONTHLY = "grade_monthly";
    public static final String PARAMETER_GRADE_BIMONTHLY = "grade_bimonthly";
    public static final String PARAMETER_GRADE_TEACHER = "grade_teacher";
    public static final String PARAMETER_ATTENDANCE = "attendance";
    public static final String PARAMETER_ATTENDANCE_STATUS = "attendance_status";
    public static final String PARAMETER_DATE_SELECTED = "date_selected";
    public static final String PARAMETER_TASK_ID = "task_id";
    public static final String PARAMETER_TAREA = "tarea";
    public static final String PARAMETER_FECHA_ENTREGA = "fecha_entrega";
    public static final String PARAMETER_NOTA_PROFESOR = "nota_profesor";
    public static final String PARAMETER_NOTA_ESTADO = "estado";

    public static final String JSON_KEY_USER = "user";
    public static final String JSON_KEY_USERS_BY_COURSE = "users_by_course";
    public static final String JSON_KEY_COURSES = "courses";
    public static final String JSON_KEY_CHILDREN = "children";
    public static final String JSON_KEY_NOTICES = "notices";
    public static final String JSON_KEY_GRADES = "grades";
    public static final String JSON_KEY_TASK = "task";
    public static final String JSON_KEY_ATTENDANCE_STATUS = "attendance_status";

    public static final int ACTIVITY_LOGIN = 1;
    public static final int ACTIVITY_COURSES = 2;
    public static final int ACTIVITY_MODULE_NOTICE_TEACHER = 3;
    public static final int ACTIVITY_MODULE_ATTENDANCE = 4;
    public static final int ACTIVITY_MODULE_GRADES = 5;
    public static final int ACTIVITY_MODULE_GRADES_LIST_STUDENTS = 6;
    public static final int ACTIVITY_MODULE_GRADES_CREATE = 7;
    public static final int ACTIVITY_MODULE_TASK = 8;
    public static final int ACTIVITY_MODULE_ATTENDANCE_CREATE = 9;
    public static final int ACTIVITY_LIST_CHILDREN = 10;
    public static final int ACTIVITY_COURSES_BY_USER = 11;
    public static final int ACTIVITY_MODULE_NOTICE_FATHER = 12;
    public static final int ACTIVITY_MODULE_GRADES_FATHER = 13;
    public static final int ACTIVITY_MODULE_ATTENDANCE_STATUS = 14;
    public static final int ACTIVITY_MODULE_TASK_CREATE = 15;
    public static final int ACTIVITY_MODULE_TASK_TEACHER_LIST = 16;
    public static final int ACTIVITY_MODULE_TASK_FATHER_LIST = 17;

    public static final String BUNDLE_ROUTE = "route";
    public static final String BUNDLE_DATE_SELECTED = "date_selected";

    public static final int ROUTE_REPORTS_PIECHART_INT = 1;
    public static final int ROUTE_REPORTS_STEPPEDAREACHART_INT = 2;
    public static final int ROUTE_REPORTS_GAUGE_INT = 3;

    public static final String PENDIENTE = "pendiente";
    public static final String NO_PENDIENTE = "no_pendiente";

}
