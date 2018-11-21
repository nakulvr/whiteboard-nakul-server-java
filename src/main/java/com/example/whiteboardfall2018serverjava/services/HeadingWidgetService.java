package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.HeadingWidget;
import com.example.whiteboardfall2018serverjava.models.ImageWidget;
import com.example.whiteboardfall2018serverjava.models.Topic;
import com.example.whiteboardfall2018serverjava.models.Widget;
import com.example.whiteboardfall2018serverjava.repositories.HeadingWidgetRepository;
import com.example.whiteboardfall2018serverjava.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins="*")
public class HeadingWidgetService {
    @Autowired
    TopicRepository tr;
    @Autowired
    HeadingWidgetRepository hr;
    @Autowired
    WidgetService widgetService;
    @Autowired
    TopicService topicService;

    @PostMapping("/api/topic/{topicId}/heading/widget")
    public List<Widget> createHeadingWidget(
            @PathVariable("topicId") int topicId,
            @RequestBody HeadingWidget headingWidget) {
        headingWidget.setType("HEADING");
        Topic topic = tr.findById(topicId).get();
        headingWidget.setTopic(topic);
        hr.save(headingWidget);
        return tr.findById(topicId).get().getWidgets();
    }

    @GetMapping("/api/topic/{topicId}/heading/widget")
    public List<Widget> findAllHeadingWidget(
            @PathVariable("topicId") int topicId){
        return tr.findById(topicId).get().getWidgets().stream()
                .filter(w -> w.getType().equals("HEADING")).collect(Collectors.toList());
    }

    @GetMapping("/api/topic/{topicId}/heading/widget/{widgetId}")
    public Widget findHeadingWidgetById(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId) {
//        tr.findById(topicId).get().getWidgets().stream().filter(w -> w.getType().equals("LIST")).collect(Collectors.toList());
        return tr.findById(topicId).get().getWidgets()
                .stream().filter(w -> w.getId() == widgetId).findAny().orElse(null);
    }
    @PutMapping("/api/topic/{topicId}/heading/widget/{widgetId}")
    public List<Widget> updateHeadingWidget(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId,
            @RequestBody HeadingWidget headingWidget){
//        HeadingWidget widgetToUpdate = findHeadingWidgetById(topicId, widgetId);
        HeadingWidget headingWidgetToUpdate = hr.findById(widgetId).get();
        headingWidgetToUpdate.setText(headingWidget.getText());
        headingWidgetToUpdate.setSize(headingWidget.getSize());
        headingWidgetToUpdate.setTitle(headingWidget.getTitle());
//        widgetToUpdate.setTitle(headingWidget.getTitle());
//        widgetToUpdate.setType(headingWidget.getType());
//        widgetService.updateWidgetRepo(widgetToUpdate, widget);
        hr.save(headingWidgetToUpdate);
        return topicService.findWidgetForTopic(topicId);
    }

    @DeleteMapping("/api/topic/{topicId}/heading/widget/{widgetId}")
    public List<Widget> deleteHeadingWidget(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId) {
        hr.deleteById(widgetId);
        return topicService.findWidgetForTopic(topicId);
    }
}