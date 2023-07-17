package hello.core;

import hello.core.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoreApplicationTests {

	// 필드주입은 테스트나, config 설정에서는 사용해도 되지만 웬만하면 생성자 주입을 사용한다.
	@Autowired
	OrderService orderService;
	
	@Test
	void contextLoads() {
	}

}
