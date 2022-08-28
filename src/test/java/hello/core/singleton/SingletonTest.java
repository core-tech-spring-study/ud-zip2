package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        //1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //3. 참조값이 다른 것을 확인
        System.out.println(memberService1);
        System.out.println(memberService2);

        // memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을적용한 객체 사용")
    void singletonServiceTest() {
        SingletonServiceTest singletonServiceTest1 = SingletonServiceTest.getInstance();
        SingletonServiceTest singletonServiceTest2 = SingletonServiceTest.getInstance();

        System.out.println("singletonService1 : "+ singletonServiceTest1);
        System.out.println("singletonService2 : "+ singletonServiceTest2);

        // 객체가 진짜로 같은 객체인지 구별하기 위해서는 isSameAs, isNotSameAs
        assertThat(singletonServiceTest1).isSameAs(singletonServiceTest2);
    }
}
