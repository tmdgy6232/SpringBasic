package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeabLifecycleTest {

    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        /* bean 콜백 생명주기 생성 후, 소멸 전 메서드 사용 예제
         * 특징
         * 1. 메서드 이름을 자유롭게 할 수 있음.
         * 2. 스프링 빈이 스프링 코드에 의존하지 않음
         * 3. 코드가 아니라 설정정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화,
         *  종료 메서드 적용 가능함
         *
         * 종료메서드 추론
         * destroyMethod 속성에는 특별한 기능이 있음.
         * 라이브러리는 대부분 close, shutdown 이라는 종료메서드 이름을 사용함.
         * @bean의 디스트로이 메서드는 기본값이 (inferred)로 등록되어있음
         * 이 기능은 close, shutdown라는 이름의 메서드를 자동으로 호출.
         * 따라서 스프링 빈으로 등록하면 종료메서드 따로 적어주지 않아도 잘 동작.
         * 추론기능을 사용하기 싫으면 destroyMethod="" 로 작성하면 됨 .
         */
        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
