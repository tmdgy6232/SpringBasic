package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Prototype scope bean
 * 싱글톤 빈은 스프링 컨테이너 생성 시점에 초기화 메서드가 실행되지만, 프로토타입 스코프의 빈은
 * 스프링 컨테이너에서 빈을 조회할 때 생성되고, 초기화 메서드도 실행된다.
 *
 * 프로토타입 빈을 2번 조회했으므로 완전히 다른 스프링 빈이 생성되고 초기화도 2번 된것을 알 수 있다.
 *
 * 싱글톤 빈은 스프링 컨테이너가 관리하기에 스프링 컨테이너가 종료될 때 빈의 종료 메서드가 실행되지만
 * 프로토타입 빈은 직접 종료 메서드를 호출해줘야 한다.
 * */
public class PrototypeTest {
    @Test
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
        assertThat(bean1).isNotSameAs(bean2);
        bean1.destroy();
        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean{
        @PostConstruct
        public void init(){
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("SingletonBean.destroy");
        }
    }
}
