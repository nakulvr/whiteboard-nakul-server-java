package com.example.whiteboardfall2018serverjava.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.whiteboardfall2018serverjava.models.ListWidget;
import com.example.whiteboardfall2018serverjava.models.Topic;
import com.example.whiteboardfall2018serverjava.models.Widget;
import com.example.whiteboardfall2018serverjava.repositories.ListWidgetRepository;
import com.example.whiteboardfall2018serverjava.repositories.TopicRepository;

@RestController
@CrossOrigin(origins="*")
public class ListWidgetService {
    @Autowired
    TopicRepository tr;
    @Autowired
    ListWidgetRepository listWidgetRepository;
    @Autowired
    WidgetService widgetService;
    @Autowired
    TopicService topicService;

    @PostMapping("/api/topic/{topicId}/list/widget")
    public List<Widget> createListWidget(
            @PathVariable("topicId") int topicId,
            @RequestBody ListWidget listWidget) {
        listWidget.setType("LIST");
        Topic topic = tr.findById(topicId).get();
        listWidget.setTopic(topic);
        listWidget = listWidgetRepository.save(listWidget);
        return tr.findById(topicId).get().getWidgets();
    }

    @GetMapping("/api/topic/{topicId}/list/widget")
    public List<Widget> findAllListWidget(
            @PathVariable("topicId") int topicId){
        return tr.findById(topicId).get().getWidgets().stream()
                .filter(w -> w.getType().equals("LIST")).collect(Collectors.toList());
    }

    @GetMapping("/api/topic/{topicId}/list/widget/{widgetId}")
    public Widget findListWidgetById(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId) {
//        tr.findById(topicId).get().getWidgets().stream().filter(w -> w.getType().equals("LIST")).collect(Collectors.toList());
        return tr.findById(topicId).get().getWidgets()
                .stream().filter(w -> w.getId() == widgetId).findAny().orElse(null);
    }
    @PutMapping("/api/topic/{topicId}/list/widget/{widgetId}")
    public List<Widget> updateListWidget(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId,
            @RequestBody ListWidget listWidget){
//        Widget widgetToUpdate = findListWidgetById(topicId, widgetId);
//        widgetToUpdate.setTitle(widget.getTitle());
//        widgetToUpdate.setType(widget.getType());
//        widgetService.updateWidgetRepo(widgetToUpdate, widget);
        ListWidget listWidgetToUpdate = listWidgetRepository.findById(widgetId).get();
        listWidgetToUpdate.setItems(listWidget.getItems());
        listWidgetToUpdate.setTitle(listWidget.getTitle());
        return topicService.findWidgetForTopic(topicId);
    }

    @DeleteMapping("/api/topic/{topicId}/list/widget/{widgetId}")
    public List<Widget> deleteListWidget(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId) {
        listWidgetRepository.deleteById(widgetId);
        return topicService.findWidgetForTopic(topicId);
    }
}