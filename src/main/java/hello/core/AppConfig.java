package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // @Configuration이 없으면
    // class가 CGLIB기술을 쓰지 않고 그냥 생 클래스로 등록되기 때문에
    // 설정에 관환 싱글톤을 보장해주지 않는다.
    //@Bean memberService -> new MemorMemberRepository()
    //@Bean orderService -> new MemorymemberREpository() 싱글톤이 깨지지 않는가

    @Bean
    public MemberService memberService(){
        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository()
    {
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        System.out.println("AppConfig.discountPolicy");
        return new RateDiscountPolicy();
    }
}
