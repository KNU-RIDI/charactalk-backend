package knu.ridi.charactalk.story.service;

import knu.ridi.charactalk.story.api.dto.StoryCardResponse;
import knu.ridi.charactalk.story.api.dto.StoryResponse;
import knu.ridi.charactalk.story.domain.Story;
import knu.ridi.charactalk.story.domain.StoryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryService {

    private final StoryReader storyReader;

    public StoryResponse getStory(Long storyId) {
        Story story = storyReader.readById(storyId);
        return StoryResponse.from(story);
    }

    public List<StoryCardResponse> getStories() {
        return storyReader.readAllStories().stream()
                .map(StoryCardResponse::from)
                .toList();
    }

}
