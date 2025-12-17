// src/main/java/au/com/telstra/simcardactivator/ActivationController.java
package au.com.telstra.simcardactivator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sim")
public class ActivationController {

  private final ActuatorService actuatorService;

  public ActivationController(ActuatorService actuatorService) {
    this.actuatorService = actuatorService;
  }

  @PostMapping("/activate")
  public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request) {

    boolean success = actuatorService.attemptActivation(request.getIccid());

    // 과제 요구사항: 성공 또는 실패를 콘솔에 출력합니다.
    if (success) {
      System.out.println("SIM Card Activation SUCCESS for ICCID: " + request.getIccid() + " (Email: " + request.getCustomerEmail() + ")");
      return ResponseEntity.ok("SIM activation successful.");
    } else {
      System.err.println("SIM Card Activation FAILURE for ICCID: " + request.getIccid());
      return ResponseEntity.status(500).body("SIM activation failed.");
    }
  }
}