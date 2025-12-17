// src/main/java/au/com/telstra/simcardactivator/IccidRequest.java
package au.com.telstra.simcardactivator;

public class IccidRequest {
  private String iccid;

  public IccidRequest() {
  }

  public IccidRequest(String iccid) {
    this.iccid = iccid;
  }

  // Getters
  public String getIccid() {
    return iccid;
  }

  // Setters
  public void setIccid(String iccid) {
    this.iccid = iccid;
  }
}