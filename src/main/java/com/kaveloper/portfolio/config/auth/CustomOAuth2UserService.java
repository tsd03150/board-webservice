package com.kaveloper.portfolio.config.auth;

import com.kaveloper.portfolio.config.auth.dto.OAuthAttributes;
import com.kaveloper.portfolio.config.auth.dto.SessionMember;
import com.kaveloper.portfolio.entity.Member;
import com.kaveloper.portfolio.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        // oAuth2User 객체에 실질적인 로그인 한 사용자의 정보가 담겨있다
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        // 현재 진행중인 소셜 서비스를 구분하는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 각 소셜 서비스마다 유니크한 필드명이 있다 (구글은 sub, 네이버는 id)
        // 각 소셜 서비스가 모두 같은 이름의 필드명을 가졌다면 이렇게 할 필요가 없지만 다르기 때문에
        // 여기서 그 필드명을 찾아서 그 값을 userNameAttributeName는에 저장한 것이다
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes =
                OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        // 해당 파라미터에 데이터들을 통해서 oAuth2User를 attributes로 변환(엔티티를 dto로 변환하듯이)
        // 즉 여기서는 로그인한 사용자가(OAuth2User가 구글(또는 네이버)형태의 attributes로 변환)
        Member member = saveOrUpdate(attributes);
        log.info("member={} 님이 접속했습니다.", member);
        httpSession.setAttribute("member", new SessionMember(member));
        // httpSession에 "member" 라는 키로 SessionUser객체를 넣음

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmailAndWebCode(attributes.getEmail(), attributes.getRegistrationId())
                .map(entity -> entity.updateProfile(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        // 여기서는 구글 형태의 attributes(OAuth2 사용자의 정보)가 오게 되고
        // 이 사용자의 정보가 기존에 있을 경우에는 데이터를 업데이트 시켜주고
        // 없을 경우는 attributes를 member로 변환 후 새롭게 생성

        return memberRepository.save(member);
        // 데이터베이스에 로그인 한 사용자의 정보가 저장됨
    }
}
