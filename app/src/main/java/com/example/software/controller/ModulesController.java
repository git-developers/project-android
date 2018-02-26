package com.example.software.controller;

import android.content.Context;

import com.example.software.dao.implement.ModulesDaoImplement;
import com.example.software.entity.Communication;
import com.example.software.entity.Courses;
import com.example.software.entity.Modules;
import com.example.software.entity.Notice;
import com.example.software.entity.Semester;
import com.example.software.entity.TaskEstadoList;
import com.example.software.entity.TaskList;
import com.example.software.entity.User;
import com.example.software.utils.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModulesController extends BaseController {


    private static final String TAG = "ModulesController";
    private Context context;
    private ModulesDaoImplement dao;

    public ModulesController(Context context) {
        this.context = context;
        this.dao = new ModulesDaoImplement(this.context);
    }

    public HashMap wsCreateGrade(Courses course, User user, Semester semester, String examenMensual, String examenBimestral, String notaProfesor) {
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Const.PARAMETER_STUDENT_USERNAME, user.getUsername());
            params.put(Const.PARAMETER_COURSE_ID, String.valueOf(course.getId()));
            params.put(Const.PARAMETER_BIMESTER, String.valueOf(semester.getId()));
            params.put(Const.PARAMETER_GRADE_MONTHLY, examenMensual);
            params.put(Const.PARAMETER_GRADE_BIMONTHLY, examenBimestral);
            params.put(Const.PARAMETER_GRADE_TEACHER, notaProfesor);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public HashMap wsCreateNotice(String username, Courses course, String message, int noticeType) {
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Const.PARAMETER_USERNAME, username);
            params.put(Const.PARAMETER_MESSAGE, message);
            params.put(Const.PARAMETER_NOTICE_TYPE, String.valueOf(noticeType));
            params.put(Const.PARAMETER_COURSE_ID, String.valueOf(course.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public HashMap wsCreateTask(String username, Courses course, String taskId,  String tarea, String fechaEntrega, int notaProfesor, int estado) {
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put(Const.PARAMETER_USERNAME, username);
            params.put(Const.PARAMETER_COURSE_ID, String.valueOf(course.getId()));
            params.put(Const.PARAMETER_TASK_ID, taskId);
            params.put(Const.PARAMETER_TAREA, tarea);
            params.put(Const.PARAMETER_FECHA_ENTREGA, fechaEntrega);
            params.put(Const.PARAMETER_NOTA_PROFESOR, String.valueOf(notaProfesor));
            params.put(Const.PARAMETER_NOTA_ESTADO, String.valueOf(estado));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public long insertSemester(String username, Semester semester) {
        long idInserted = dao.insertSemester(username, semester);
        dao.closeDb();
        return idInserted;
    }

    public Semester findLastSemesterSelected() {
        Semester semester = dao.findLastSemesterSelected();
        dao.closeDb();
        return semester;
    }

    public List<Modules> getModulesListObjects() {

        List<Modules> listObject = new ArrayList<Modules>();

        Modules module1 = new Modules();
        module1.setId(Communication.NOTICE);
        module1.setName("Comunicado");
        module1.setImage("ic_chat");
        listObject.add(module1);

        Modules module2 = new Modules();
        module2.setId(Communication.ATTENDANCE);
        module2.setName("Asistencia");
        module2.setImage("ic_person");
        listObject.add(module2);

        Modules module3 = new Modules();
        module3.setId(Communication.GRADES);
        module3.setName("Notas");
        module3.setImage("ic_repeat_one_light");
        listObject.add(module3);

        Modules module4 = new Modules();
        module4.setId(Communication.TASK);
        module4.setName("Tareas");
        module4.setImage("ic_touch_app");
        listObject.add(module4);

        return listObject;
    }

    public List<TaskList> getTaskListOptions() {

        List<TaskList> listObject = new ArrayList<TaskList>();

        TaskList taskList1 = new TaskList();
        taskList1.setId(TaskList.CREATE_NEW);
        taskList1.setName("Crear nuevo");
        taskList1.setImage("ic_library_books2");
        listObject.add(taskList1);

        TaskList taskList2 = new TaskList();
        taskList2.setId(TaskList.LIST_TASK);
        taskList2.setName("Listar tareas");
        taskList2.setImage("ic_playlist_play2");
        listObject.add(taskList2);

        return listObject;
    }

    public List<Semester> getSemesters() {

        List<Semester> listObject = new ArrayList<Semester>();

        Semester semester1 = new Semester();
        semester1.setId(Semester.ONE);
        semester1.setName("Bimestre 1");
        semester1.setImage("ic_looks_one");
        listObject.add(semester1);

        Semester semester2 = new Semester();
        semester2.setId(Semester.TWO);
        semester2.setName("Bimestre 2");
        semester2.setImage("ic_looks_two");
        listObject.add(semester2);

        Semester semester3 = new Semester();
        semester3.setId(Semester.THREE);
        semester3.setName("Bimestre 3");
        semester3.setImage("ic_looks_three");
        listObject.add(semester3);

        Semester semester4 = new Semester();
        semester4.setId(Semester.FOUR);
        semester4.setName("Bimestre 4");
        semester4.setImage("ic_looks_four");
        listObject.add(semester4);

        return listObject;
    }

    public List<String> getSpinnerNoticeType() {
        List<String> noticeType = new ArrayList<String>();
        noticeType.add("[ Seleccione ]");
        noticeType.add(Notice.MENSAJE);
        noticeType.add(Notice.CITACION);
        noticeType.add(Notice.INCIDENTE);
        return noticeType;
    }

    public List<String> getTaskEstadoList() {

        List<String> estado = new ArrayList<String>();
        estado.add(TaskEstadoList.PENDIENTE);
        estado.add(TaskEstadoList.NO_PENDIENTE);

        return estado;
    }

    public List<String> getSpinnerNotas() {
        List<String> noticeType = new ArrayList<String>();
        noticeType.add("01");
        noticeType.add("02");
        noticeType.add("03");
        noticeType.add("04");
        noticeType.add("05");
        noticeType.add("06");
        noticeType.add("07");
        noticeType.add("08");
        noticeType.add("09");
        noticeType.add("10");
        noticeType.add("11");
        noticeType.add("12");
        noticeType.add("13");
        noticeType.add("14");
        noticeType.add("15");
        noticeType.add("16");
        noticeType.add("17");
        noticeType.add("18");
        noticeType.add("19");
        noticeType.add("20");
        return noticeType;
    }

}
