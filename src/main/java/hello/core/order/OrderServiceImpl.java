package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private final DiscountPolicy discountPolicy;

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
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
