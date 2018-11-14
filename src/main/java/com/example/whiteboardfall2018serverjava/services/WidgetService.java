package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.*;
import com.example.whiteboardfall2018serverjava.repositories.ListWidgetRepository;
import com.example.whiteboardfall2018serverjava.repositories.TopicRepository;
import com.example.whiteboardfall2018serverjava.repositories.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true" , allowedHeaders = "*")
public class WidgetService {
    @Autowired
    UserService userService;

    @Autowired
    WidgetRepository wr;
    @Autowired
    TopicRepository tr;
    @Autowired
    ListWidgetRepository lr;

    public void updateWidgetRepo(Widget widget) {
        wr.deleteById(widget.getId());
        if(widget.getWidgetType().equals("LIST")) {
            ListWidget listWidget = new ListWidget();
            listWidget.setWidgetType(widget.getWidgetType());
            listWidget.setTopic(widget.getTopic());
            listWidget.setTitle(widget.getTitle());
            lr.save(listWidget);
        }
        else
            wr.save(widget);
    }

    @GetMapping("/api/widget")
    public List<Widget> findAllWidgets() {
        return (List<Widget>) wr.findAll();
    }

    @GetMapping("/api/widget/{widgetId}")
    public Widget findWidgetById(
            @PathVariable("widgetId") int widgetId) {
        return wr.findById(widgetId).get();
    }

    @DeleteMapping("/api/widget/{widgetId}")
    public List<Widget> deleteWidget(
            @PathVariable("widgetId") int widgetId) {
        wr.deleteById(widgetId);
        return findAllWidgets();
    }

    @PutMapping("/api/widget/{widgetId}")
    public List<Widget> updateWidget(
            @PathVariable("widgetId") int widgetId,
            @RequestBody Widget widget) {
        Widget widgetToUpdate = findWidgetById(widgetId);
        widgetToUpdate.setWidgetType(widget.getWidgetType());
//        widgetToUpdate.setTopic(widget.getTopic());
        widgetToUpdate.setTitle(widget.getTitle());
        wr.save(widgetToUpdate);
        return findAllWidgets();
    }

    @GetMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic/{topicId}/widget")
    public List<Widget> findTopicsForTopicId(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId,
            @PathVariable("topicId") int topicId) {
        User user =  userService.findUserById(userId);
        for(Course course: user.getCourses()) {
            if(course.getId() == courseId) {
                for(Module module : course.getModules()) {
                    if(module.getId() == moduleId) {
                        for(Lesson lesson : module.getLessons()) {
                            if(lesson.getId() == lessonId) {
                                for(Topic topic: lesson.getTopics()){
                                    if(topic.getId() == topicId) {
                                        return topic.getWidgets();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
