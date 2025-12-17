// src/main/java/au/com/telstra/simcardactivator/ActuatorService.java
package au.com.telstra.simcardactivator;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ActuatorService {

  private static final String ACTUATOR_URL = "http://localhost:8444/actuate";
  private final RestTemplate restTemplate;

  public ActuatorService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public boolean attemptActivation(String iccid) {

    IccidRequest iccidRequest = new IccidRequest(iccid);

    try {
      // Actuator Microservice에 POST 요청을 보냅니다.
      ResponseEntity<ActuatorResponse> response = restTemplate.postForEntity(
          ACTUATOR_URL,
          iccidRequest,
          ActuatorResponse.class
      );

      // HTTP 상태 코드가 200 OK이고 응답 본문의 success 플래그를 반환합니다.
      if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        return response.getBody().isSuccess();
      }
      return false;

    } catch (Exception e) {
      // 통신 오류 발생 시 실패로 간주합니다.
      System.err.println("Error calling Actuator service for ICCID " + iccid + ": " + e.getMessage());
      return false;
    }
  }
}