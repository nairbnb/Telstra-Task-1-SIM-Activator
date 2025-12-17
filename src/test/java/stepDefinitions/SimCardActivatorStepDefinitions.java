// test/java/stepDefinitions/SimCardActivatorStepDefinitions.java

package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Spring Context 및 Cucumber 연동을 위한 상위 설정 클래스가
// 이미 프로젝트에 제공되어 있다고 가정합니다.

public class SimCardActivatorStepDefinitions {

    // Spring Boot Test Context에서 자동 주입될 것으로 예상
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8080";

    // 응답 저장을 위한 필드
    private ResponseEntity<String> activationResponse;
    private ResponseEntity<String> queryResponse;

    // --- Given Steps ---

    @Given("the service is running")
    public void the_service_is_running() {
        // 서비스가 실행 중인지 확인하는 로직 (선택 사항)
        // 예를 들어, 헬스 체크 엔드포인트를 호출하여 HTTP 200 응답 확인
        System.out.println("Microservice is ready at: " + baseUrl);
    }

    // --- When Steps ---

    @When("I submit an activation request for ICCID {string}")
    public void i_submit_an_activation_request_for_iccid(String iccid) {
        // 요청 본문 (Payload) 구성: {"iccid": "..."}
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("iccid", iccid);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        String activationEndpoint = "/api/activate"; // 실제 엔드포인트에 맞게 수정 필요

        activationResponse = restTemplate.exchange(
            baseUrl + activationEndpoint,
            HttpMethod.POST,
            entity,
            String.class
        );
    }

    // --- Then Steps ---

    @Then("the activation request should be successful with HTTP status {int}")
    public void the_activation_request_should_be_successful_with_http_status(int expectedStatus) {
        assertNotNull(activationResponse, "Activation response was null.");
        assertEquals(
            expectedStatus,
            activationResponse.getStatusCode().value(),
            "Activation request HTTP status mismatch."
        );
    }

    @Then("the activation record with ID {int} should have status {string}")
    public void the_activation_record_should_have_status(int recordId, String expectedStatus) {
        // 쿼리 엔드포인트 호출: /api/query/{id}
        String queryEndpoint = "/api/query/" + recordId; // 실제 엔드포인트에 맞게 수정 필요
        queryResponse = restTemplate.getForEntity(
            baseUrl + queryEndpoint,
            String.class
        );

        // HTTP 상태 확인
        assertEquals(HttpStatus.OK, queryResponse.getStatusCode(), "Query request should return HTTP 200 OK.");
        assertNotNull(queryResponse.getBody(), "Query response body should not be null.");

        // 응답 본문에서 상태 필드 검증 (JSON 파싱이 필요하지만, 여기서는 문자열 포함으로 단순화)
        String responseBody = queryResponse.getBody();
        String expectedJsonSegment = "\"status\":\"" + expectedStatus + "\"";

        if (!responseBody.contains(expectedJsonSegment)) {
            throw new AssertionError(
                "Record status mismatch for ID " + recordId +
                    ". Expected status: " + expectedStatus +
                    ", Actual response (JSON segment check failed): " + responseBody
            );
        }
        System.out.println("Record ID " + recordId + " successfully checked for status: " + expectedStatus);
    }
}