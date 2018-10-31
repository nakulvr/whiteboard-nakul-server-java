package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.Course;
import com.example.whiteboardfall2018serverjava.models.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*", allowCredentials = "true" , allowedHeaders = "*")
@CrossOrigin
public class ModuleService {
    @Autowired
    CourseService courseService;

    @GetMapping("/api/user/{userId}/course/{courseId}/module")
    public List<Module> findAllModules(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId) {
        Course course = courseService.findCourseById(userId, courseId);
        return course.getModules();
    }

    @PostMapping("/api/user/{userId}/course/{courseId}/module")
    public List<Course> createModule(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @RequestBody Module module) {
        Course course = courseService.findCourseById(userId, courseId);
        course.getModules().add(module);
//        return course.getModules();
        return courseService.findAllCourses(userId);
    }

    @GetMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}")
    public Module findModuleById(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId) {
        Course course = courseService.findCourseById(userId, courseId);
        for(Module module : course.getModules()) {
            if(module.getId() == moduleId)
                return module;
        }
        return null;
    }

    @DeleteMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}")
    public List<Course> deleteModule(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId) {
        Course course = courseService.findCourseById(userId, courseId);
//        Iterator<Module> iter = course.getModules().iterator();
//        while (iter.hasNext()) {
//            Module module = iter.next();
//            if(module.getId() == moduleId) {
//               iter.remove();
//            }
//        }
//        return findAllModules(userId, courseId);
        course.getModules().removeIf((Module module) -> module.getId() == moduleId);
        return courseService.findAllCourses(userId);
    }

    @PutMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}")
    public List<Course> updateModule(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @RequestBody Module module) {
            deleteModule(userId, courseId, moduleId);
            createModule(userId, courseId, module);
        return courseService.findAllCourses(userId);
    }
}
