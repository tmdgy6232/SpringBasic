package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appconfig = new AppConfig();

        // 1. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService1 = appconfig.memberService();
        // 2. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService2 = appconfig.memberService();
        
        // 참조값이 다른것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonTest(){
        SingletonService singletonService = SingletonService.getInstance();
        SingletonService singletonService2= SingletonService.getInstance();

        // 참조값이 다른것을 확인
        System.out.println("singletonService = " + singletonService);
        System.out.println("singletonService2 = " + singletonService2);

        Assertions.assertThat(singletonService).isSameAs(singletonService2);
        // same ==
        // equal


    }
}
