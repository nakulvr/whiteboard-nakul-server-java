package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.Course;
import com.example.whiteboardfall2018serverjava.models.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ModuleService {
    @Autowired CourseService courseService;
    @GetMapping("/api/course/{courseId}/module")
    public List<Module> findModulesForCourse(
            @PathVariable("courseId") int courseId) {
        Course course = courseService.findCourseById(courseId);
        return course.getModules();
    }
}
