package hello.core.lifecycle;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url){
        this.url = url;
    }

    //서비스 시작 시 호출
    public void connect(){
        System.out.println("connect = " + url);
    }

    public void call(String message){
        System.out.println("call = " + url + " message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect(){
        System.out.println("close = " + url);
    }
    /*
    * 애노테이션을 사용하는 방법.
    * javax.annotation 패키지를 사용하기때문에 스프링이 아닌 다른컨테이너에서도 잘 동작한다.
    * 유일한 단점은 외부 라이브러리에는 적용하지 못한다는 것이다.
    * */
    @PostConstruct
    public void init() {
        // 의존관계 주입이 끝나면 호출
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.destroyz");
        disconnect();
    }
}
