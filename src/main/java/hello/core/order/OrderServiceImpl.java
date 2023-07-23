package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    @Qualifier("mainDiscountPolicy")
    private final DiscountPolicy rateDiscountPolicy;

    // 생성자가 한개만 있으면 @Autowired를 굳이 지정해주지 않아도 된다.

    /**
     * 수정자주입(Setter)
     * 수정자에다가 Autowired를 넣어줘도 주입이 되긴 함.
     * 하지만 메서드를 사용하는 경우 덮어 쓸 수 있음.
     * 불변의 법칙에 어긋남.
     * 그래서 선택적인, 변경 가능성이 있는 의존관계에 사용함
     * @Autowired(required=false) 사용하면 필수값이 아니게 됨
     * 생성자가 하나만 있으면 생성자에 Autowired 어노테이션을 빼도 됨,.
     */
    // @RequiredArgumentConstructor가 아래 주석 코드를 자동으로 만들어준다.
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }
/*
*  빈이 겹칠 때에 @Autowired를 사용한다면
*  1. Autowired
*  타입으로 처음에 조회하고 두개일 시 변수명으로 매칭한다. 그래서 rateDiscounPolicy라고 해주니까
*  잘 동작하는걿 확인할 수 있따.
*
*  2. @Quilifier 를 사용
*  @Quilifier 추가 구분자를 붕텨주는 방법이다. 주입 시 추가적인 방법을 제공하는 것이지, 빈 이름을 변경하는것은 아니다.
*  @Component 밑에 @Quilifier("aaa")로 지정하고 불러오는 부분에서 똑같이 사용한다.
*  또한 이 이후에 찾지 못하면 1번과 같이 변수명으로도 매칭하는 동작을 이어 수행한다.*
*  단점은 모든 코드에 @Quilifier를 붙여야한다. 하지만 primary는 안붙여줘도 되긴 한다.
*
* 3. @Primary 사용
*  우선순위를 지정하는 방법이다. 같은 타입이 있으면 이 객체가 우선순위가 되어 주입된다.
*
*  위에 2번 3번을 동시에 사용하였을 때에는
*  스프링은 자동보다는 수동이, 넓은범위의 선택권보다는 좁은 범위의 선택권이 우선순위가 높기 때문에
*  Primary보다 Quilifier가 우선순위가 높다.
* */
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = rateDiscountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
