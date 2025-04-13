package knu.ridi.charactalk.story.domain;

import knu.ridi.charactalk.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StoryReader {

    private static final String STORY_NOT_FOUND = "스토리를 찾을 수 없습니다.";
    private final StoryRepository storyRepository;

    public Story readById(Long storyId) {
        Story story = storyRepository.findWithDetailsById(storyId);
        if (story == null) {
            throw new IllegalArgumentException(STORY_NOT_FOUND);
        }
        return story;
    }

    public List<Story> readAllStories() {
        return storyRepository.findAll();
    }
}
