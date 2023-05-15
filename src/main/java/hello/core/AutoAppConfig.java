package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// excludeFileter = 이 어노테이션은 빼고 스캔하겠다.
@ComponentScan(
        // 위치 지정
        // 미지정시에는 @ComponentScan 어노테이션이 있는 위치부터 탐색한다.
        // 권장하는 방법은 그냥 이 파일을 프로젝트 최상단에 두는 것.
        // includefilter = 추가대상
        // excludefilter = 제외대상
        basePackages = "hello.core",
        basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
