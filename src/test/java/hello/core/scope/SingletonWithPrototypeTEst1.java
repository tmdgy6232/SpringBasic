package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTEst1 {
    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);
        ProtoTypeBean bean1 = ac.getBean(ProtoTypeBean.class);
        bean1.addCount();
        assertThat(bean1.getCount()).isEqualTo(1);
        ProtoTypeBean bean2 = ac.getBean(ProtoTypeBean.class);
        bean2.addCount();
        assertThat(bean2.getCount()).isEqualTo(1);

        ac.close();
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class, ClientBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean{
        private final ProtoTypeBean protoTypeBean; // 생성시점에 주입

        // DI가 아닌 DL을 위해 사용하는 객 스프링에 의존적임
//        @Autowired
//        private ObjectProvider<ProtoTypeBean> protoTypeBeanObjectProvider;
        @Autowired
        private Provider<ProtoTypeBean> protoTypeBeanObjectProvider;

        @Autowired
        public ClientBean(ProtoTypeBean protoTypeBean) {
            this.protoTypeBean = protoTypeBean;
        }

        public int logic(){
            ProtoTypeBean object = protoTypeBeanObjectProvider.get();
            object.addCount();
            return object.getCount();
        }
    }

    @Scope("prototype")
    static class ProtoTypeBean{
        private int count= 0;

        public void addCount(){
            count++;
        }

        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("ProtoTypeBean.init" + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("ProtoTypeBean.destroy");
        }
    }
}
