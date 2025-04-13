package knu.ridi.charactalk.story.domain;

import jakarta.persistence.*;
import knu.ridi.charactalk.character.domain.Character;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private StoryType storyType;

    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "story_id")
    private List<Character> characters = new ArrayList<>();

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StoryTag> storyTags = new HashSet<>();

    @Builder
    public Story(String title, String description, String imageUrl, StoryType storyType) {
        this.title = title;
        this.description = description;
        this.storyType = storyType;
        this.imageUrl = imageUrl;
    }
}
