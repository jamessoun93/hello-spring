package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("james");

        // when
        Long newId = memberService.signUp(member);

        // then
        Member result = memberService.findOne(newId).get();
        assertThat(result.getName()).isEqualTo(member.getName());
    }

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("james");

        Member member2 = new Member();
        member2.setName("james");

        // when
        memberService.signUp(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.signUp(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }
}