package com.example.software.dao.interfaces;

import com.example.software.entity.Courses;
import com.example.software.entity.Semester;

public interface ISemester {

    public long insertSemester(String username, Semester semester);
    public Semester findLastSemesterSelected();
}
