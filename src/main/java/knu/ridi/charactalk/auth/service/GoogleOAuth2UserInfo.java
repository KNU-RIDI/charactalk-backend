package knu.ridi.charactalk.auth.service;

import java.util.Map;

public record GoogleOAuth2UserInfo(Map<String, Object> attributes) {

    String getName() {
        return (String) attributes.get("name");
    }

    String getEmail() {
        return (String) attributes.get("email");
    }

    String getPicture() {
        return (String) attributes.get("picture");
    }

    Map<String, Object> getAttributes() {
        return attributes;
    }
}