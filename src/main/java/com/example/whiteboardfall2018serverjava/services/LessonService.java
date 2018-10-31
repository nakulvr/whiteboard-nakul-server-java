package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*", allowCredentials = "true" , allowedHeaders = "*")
public class LessonService {
    @Autowired
    UserService userService;

    @Autowired
    ModuleService moduleService;

    @Autowired
    CourseService courseService;

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
    public List<Course> createLesson(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @RequestBody Lesson lesson) {
        Module module = moduleService.findModuleById(userId, courseId, moduleId);
        module.getLessons().add(lesson);
        return courseService.findAllCourses(userId);
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
    public List<Course> deleteLesson(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId) {
        Module module = moduleService.findModuleById(userId, courseId, moduleId);
        module.getLessons().removeIf((Lesson lesson) -> lesson.getId() == lessonId);
        if(module.getLessons().size() == 0){
            Lesson lesson = new Lesson("New Lesson");
            List<Topic> topics = new ArrayList<>();
            Topic topic = new Topic("New Topic");
            topics.add(topic);
            lesson.setTopics(topics);
            createLesson(userId, courseId, moduleId, lesson);
        }
        return courseService.findAllCourses(userId);
    }

    @PutMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}")
    public List<Course> updateLesson(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId,
            @RequestBody Lesson lesson) {
//        deleteLesson(userId, courseId, moduleId, lessonId);
//        createLesson(userId, courseId, moduleId, lesson);
        Lesson lessonToEdit = findLessonById(userId, courseId, moduleId, lessonId);
        lessonToEdit.setTopics(lesson.getTopics());
        lessonToEdit.setTitle(lesson.getTitle());
        return courseService.findAllCourses(userId);
    }
}
