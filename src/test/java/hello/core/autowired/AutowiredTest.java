package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {
    @Test
    void AutowiredOption(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean {
        // 의존관계가 없으면 메서드 자체가 호출이 되지 않는다.
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1 = " + noBean1);
        }

        // 의존관계가 없으면 메서드는 호출되지만 null이 들어가있다.
        @Autowired
        public void setNoBean2(@Nullable Member noBean1){
            System.out.println("noBean2 = " + noBean1);
        }

        // 의존관계가 없으면 Optional.empty값이 들어가있다.
        @Autowired
        public void setNoBean3(Optional<Member> noBean1){
            System.out.println("noBean3 = " + noBean1);
        }
    }
}
