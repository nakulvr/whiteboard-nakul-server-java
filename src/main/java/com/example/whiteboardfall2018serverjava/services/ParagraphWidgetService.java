package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.ParagraphWidget;
import com.example.whiteboardfall2018serverjava.models.Topic;
import com.example.whiteboardfall2018serverjava.models.Widget;
import com.example.whiteboardfall2018serverjava.repositories.ParagraphWidgetRepository;
import com.example.whiteboardfall2018serverjava.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins="*")
public class ParagraphWidgetService {
    @Autowired
    TopicRepository tr;
    @Autowired
    ParagraphWidgetRepository pr;
    @Autowired
    WidgetService widgetService;
    @Autowired
    TopicService topicService;

    @PostMapping("/api/topic/{topicId}/paragraph/widget")
    public List<Widget> createParagraphWidget(
            @PathVariable("topicId") int topicId,
            @RequestBody ParagraphWidget paragraphWidget) {
        paragraphWidget.setType("PARAGRAPH");
        Topic topic = tr.findById(topicId).get();
        paragraphWidget.setTopic(topic);
        pr.save(paragraphWidget);
        return tr.findById(topicId).get().getWidgets();
    }

    @GetMapping("/api/topic/{topicId}/paragraph/widget")
    public List<Widget> findAllParagraphWidget(
            @PathVariable("topicId") int topicId){
        return tr.findById(topicId).get().getWidgets().stream()
                .filter(w -> w.getType().equals("PARAGRAPH")).collect(Collectors.toList());
    }

    @GetMapping("/api/topic/{topicId}/paragraph/widget/{widgetId}")
    public Widget findParagraphWidgetById(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId) {
//        tr.findById(topicId).get().getWidgets().stream().filter(w -> w.getType().equals("LIST")).collect(Collectors.toList());
        return tr.findById(topicId).get().getWidgets()
                .stream().filter(w -> w.getId() == widgetId).findAny().orElse(null);
    }
    @PutMapping("/api/topic/{topicId}/paragraph/widget/{widgetId}")
    public List<Widget> updateParagraphWidget(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId,
            @RequestBody ParagraphWidget paragraphWidget){
//        Widget widgetToUpdate = findParagraphWidgetById(topicId, widgetId);
//        widgetToUpdate.setTitle(widget.getTitle());
//        widgetToUpdate.setType(widget.getType());
//        widgetService.updateWidgetRepo(widgetToUpdate, widget);
        ParagraphWidget paragraphWidgetToUpdate = pr.findById(widgetId).get();
        paragraphWidgetToUpdate.setText(paragraphWidget.getText());
        paragraphWidgetToUpdate.setTitle(paragraphWidget.getTitle());
        return topicService.findWidgetForTopic(topicId);
    }

    @DeleteMapping("/api/topic/{topicId}/paragraph/widget/{widgetId}")
    public List<Widget> deleteParagraphWidget(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId) {
        pr.deleteById(widgetId);
        return topicService.findWidgetForTopic(topicId);
    }
}