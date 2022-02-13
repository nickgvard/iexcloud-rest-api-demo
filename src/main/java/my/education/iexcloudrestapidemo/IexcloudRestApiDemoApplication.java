package my.education.iexcloudrestapidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IexcloudRestApiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IexcloudRestApiDemoApplication.class, args);
    }

}
