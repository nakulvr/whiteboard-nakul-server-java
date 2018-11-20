package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.*;
import com.example.whiteboardfall2018serverjava.repositories.*;
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
    ListWidgetRepository listWidgetRepository;
    @Autowired
    ImageWidgetRepository ir;
    @Autowired
    LinkWidgetRepository lr;
    @Autowired
    ParagraphWidgetRepository pr;
    @Autowired
    HeadingWidgetRepository hr;

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

//    @PutMapping("/api/widget/{widgetId}")
//    public List<Widget> updateWidget(
//            @PathVariable("widgetId") int widgetId,
//            @RequestBody Widget widget) {
//        Widget widgetToUpdate = findWidgetById(widgetId);
//        widgetToUpdate.setType(widget.getType());
////        widgetToUpdate.setTopic(widget.getTopic());
//        widgetToUpdate.setTitle(widget.getTitle());
//        wr.save(widgetToUpdate);
//        return findAllWidgets();
//    }

    @PutMapping("/api/topic/{topicId}/widget/{widgetId}")
    public List<Widget> updateWidgetRepo(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId,
            @RequestBody Widget widget) {
        wr.deleteById(widgetId);
        if(widget.getType().equals("LIST")) {
            ListWidget listWidget = new ListWidget();
            listWidget.setType(widget.getType());
//            widget.getTopic().setId(topicId);
            listWidget.setTopic(tr.findById(topicId).get());
            listWidget.setTitle(widget.getTitle());
            listWidgetRepository.save(listWidget);
        }
        else if(widget.getType().equals("IMAGE")) {
//            System.out.println(widget.getClass().getName());
            ImageWidget imageWidget = new ImageWidget();
            imageWidget.setType(widget.getType());
//            widget.getTopic().setId(topicId);
            imageWidget.setTopic(tr.findById(topicId).get());
            imageWidget.setTitle(widget.getTitle());
            ir.save(imageWidget);
        }
        else if(widget.getType().equals("LINK")){
            LinkWidget linkWidget = new LinkWidget();
            linkWidget.setType(widget.getType());
//            widget.getTopic().setId(topicId);
            linkWidget.setTopic(tr.findById(topicId).get());
            linkWidget.setTitle(widget.getTitle());
            lr.save(linkWidget);
        }
        else if(widget.getType().equals("PARAGRAPH")) {
            ParagraphWidget paragraphWidget = new ParagraphWidget();
            paragraphWidget.setType(widget.getType());
//            widget.getTopic().setId(topicId);
            paragraphWidget.setTopic(tr.findById(topicId).get());
            paragraphWidget.setTitle(widget.getTitle());
            pr.save(paragraphWidget);
        }
        else {
            HeadingWidget headingWidget = new HeadingWidget();
            headingWidget.setType(widget.getType());
//            widget.getTopic().setId(topicId);
            headingWidget.setTopic(tr.findById(topicId).get());
            headingWidget.setTitle(widget.getTitle());
            hr.save(headingWidget);
        }
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
