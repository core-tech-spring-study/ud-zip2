package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean p1 = ac.getBean(PrototypeBean.class);
        p1.addCount();
        Assertions.assertThat(p1.getCount()).isEqualTo(1);

        PrototypeBean p2 = ac.getBean(PrototypeBean.class);
        p2.addCount();
        Assertions.assertThat(p2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean c1 = ac.getBean(ClientBean.class);
        int count1 = c1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean c2 = ac.getBean(ClientBean.class);
        int count2 = c2.logic();
        Assertions.assertThat(count2).isEqualTo(2);
    }

    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean prototypeBean;

        ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy " + this);
        }
    }
}
