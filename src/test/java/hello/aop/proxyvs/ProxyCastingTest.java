package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy(){
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        factory.setProxyTargetClass(false); //JDK 동적 프록시

        //JDK 동적 프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) factory.getProxy();

        //JDK 동적 프록시를 구현 클래스로 캐스팅 실패
        Assertions.assertThrows(ClassCastException.class, () -> {
                    MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
                });
    }

    @Test
    void cglibProxy(){
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        factory.setProxyTargetClass(true); //CGLIB 프록시

        //CGLIB 프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) factory.getProxy();

        //CGLIB 프록시를 구현 클래스로 캐스팅 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
}
