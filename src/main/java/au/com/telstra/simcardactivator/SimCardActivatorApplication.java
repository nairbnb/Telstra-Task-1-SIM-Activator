// src/main/java/au/com/telstra/simcardactivator/SimCardActivatorApplication.java
package au.com.telstra.simcardactivator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SimCardActivatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimCardActivatorApplication.class, args);
  }

  // RestTemplate Bean 등록 (외부 API 통신 도구)
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}