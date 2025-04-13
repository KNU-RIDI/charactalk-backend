package knu.ridi.charactalk.story.api;

import knu.ridi.charactalk.story.api.dto.StoryCardResponse;
import knu.ridi.charactalk.story.api.dto.StoryResponse;
import knu.ridi.charactalk.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoryController implements StoryControllerDocs {

    private final StoryService storyService;

    @GetMapping("/{storyId}")
    public ResponseEntity<StoryResponse> getStory(
        @PathVariable Long storyId
    ) {
        return ResponseEntity.ok(storyService.getStory(storyId));
    }

    @GetMapping
    public ResponseEntity<List<StoryCardResponse>> getStories() {
        return ResponseEntity.status(HttpStatus.OK).body(storyService.getStories());
    }
}
