package com.example.software.dao.interfaces;

import com.example.software.entity.Courses;
import com.example.software.entity.User;

import java.util.ArrayList;

public interface ICourses {

    public long insert(String username, Courses courses);
}
