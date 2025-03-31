package knu.ridi.charactalk.auth.service;

import knu.ridi.charactalk.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
public class GoogleOAuth2User implements OAuth2User {

    private Member member;
    private GoogleOAuth2UserInfo userInfo;

    @Override
    public String getName() {
        return this.member.getEmail();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return userInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final String role = member.getRole().name();
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }
}
