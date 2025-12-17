package au.com.telstra.simcardactivator;

public class SimActivationRequest {
  private String iccid;
  private String customerEmail;

  // 기본 생성자
  public SimActivationRequest() {
  }

  // 모든 필드를 포함하는 생성자
  public SimActivationRequest(String iccid, String customerEmail) {
    this.iccid = iccid;
    this.customerEmail = customerEmail;
  }

  // Getters
  public String getIccid() {
    return iccid;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  // Setters (Spring의 ObjectMapper가 필요로 함)
  public void setIccid(String iccid) {
    this.iccid = iccid;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }
}