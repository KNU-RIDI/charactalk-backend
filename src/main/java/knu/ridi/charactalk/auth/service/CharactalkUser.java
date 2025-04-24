package knu.ridi.charactalk.auth.service;

import knu.ridi.charactalk.member.domain.Member;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CharactalkUser extends OAuth2User {

    Member getMember();
}
