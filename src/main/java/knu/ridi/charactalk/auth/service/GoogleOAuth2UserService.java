package knu.ridi.charactalk.auth.service;

import knu.ridi.charactalk.member.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberReader memberReader;
    private final MemberWriter memberWriter;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo(user.getAttributes());

        String email = userInfo.getEmail();
        String picture = userInfo.getPicture();
        String name = userInfo.getName();
        MemberInfo memberInfo = new MemberInfo(picture, name);

        Member member = updateOrWrite(email, memberInfo);

        return new GoogleOAuth2User(member, userInfo);
    }

    private Member updateOrWrite(final String email, final MemberInfo info) {
        if (memberReader.existsByEmail(email)) {
            Member member = memberReader.readByEmail(email);
            member.updateInfo(info);
            return member;
        }
        Member member = Member.builder()
            .email(email)
            .info(info)
            .role(MemberRole.ROLE_USER)
            .build();
        return memberWriter.write(member);
    }
}
