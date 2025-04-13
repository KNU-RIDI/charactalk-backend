package knu.ridi.charactalk.story.repository;

import knu.ridi.charactalk.story.domain.Story;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {

    @EntityGraph(attributePaths = {"characters", "storyTags", "storyTags.tag"})
    Story findWithDetailsById(Long id);
}
