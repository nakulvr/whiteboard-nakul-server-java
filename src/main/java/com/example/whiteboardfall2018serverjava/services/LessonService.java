package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.Course;
import com.example.whiteboardfall2018serverjava.models.Lesson;
import com.example.whiteboardfall2018serverjava.models.Module;
import com.example.whiteboardfall2018serverjava.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin("*")
public class LessonService {
    @Autowired
    UserService userService;

    @Autowired
    ModuleService moduleService;

    @GetMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson")
    public List<Lesson> findAllLessons(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId) {
        User user = userService.findUserById(userId);
        for(Course course : user.getCourses()) {
            if(course.getId() == courseId) {
                for(Module module: course.getModules()) {
                    if(module.getId() == moduleId) {
                        return module.getLessons();
                    }
                }
            }
        }
        return null;
    }

    @PostMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson")
    public List<Lesson> createLesson(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @RequestBody Lesson lesson) {
        Module module = moduleService.findModuleById(userId, courseId, moduleId);
        module.getLessons().add(lesson);
        return module.getLessons();
    }

    @GetMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}")
    public Lesson findLessonById(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId) {
        Module module = moduleService.findModuleById(userId, courseId, moduleId);
        for(Lesson lesson : module.getLessons()) {
            if(lesson.getId() == lessonId)
                return lesson;
        }
        return null;
    }

    @DeleteMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}")
    public List<Lesson> deleteLesson(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId) {
        Module module = moduleService.findModuleById(userId, courseId, moduleId);
        module.getLessons().removeIf((Lesson lesson) -> lesson.getId() == lessonId);

        return module.getLessons();
    }

    @PutMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}")
    public List<Lesson> updateLesson(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId,
            @RequestBody Lesson lesson) {
        deleteLesson(userId, courseId, moduleId, lessonId);
        createLesson(userId, courseId, moduleId, lesson);
        return findAllLessons(userId, courseId, moduleId);
    }
}
