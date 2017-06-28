public class Passenger2 implements DataObject<Boolean>{

  private Integer passengerId;
  private Integer pClass;
  private Boolean isMale;
  private Integer age;
  private Integer sibSpNb;
  private Integer parChNb;
  private String ticket;
  private Double fare;
  private String cabin;
  private String embarked;
  private Boolean survived;

  public Passenger2(int passengerId, int pClass, boolean isMale, int age,
      int sibSpNb, int parChNb, String ticket, double fare, String cabin, String embarked) {
    super();
    this.passengerId = passengerId;
    this.pClass = pClass;
    this.isMale = isMale;
    this.age = age;
    this.sibSpNb = sibSpNb;
    this.parChNb = parChNb;
    this.ticket = ticket;
    this.fare = fare;
    this.cabin = cabin;
    this.embarked = embarked;
  }

  public int getPassengerId() {
    return passengerId;
  }

  public int getpClass() {
    return pClass;
  }

  public boolean isMale() {
    return isMale;
  }

  public int getAge() {
    return age;
  }

  public int getSibSpNb() {
    return sibSpNb;
  }

  public int getParChNb() {
    return parChNb;
  }

  public String getTicket() {
    return ticket;
  }

  public double getFare() {
    return fare;
  }

  public String getCabin() {
    return cabin;
  }

  public String getEmbarked() {
    return embarked;
  }

  @Override
  public Object getValueForFeature(String feature) {
    switch(feature){
    case "passengerId":
      return this.passengerId;
    case "class":
      return pClass;
    case "isMale":
      return isMale;
    case "age":
      return age;
    case "sibSpNb":
      return sibSpNb;
    case "parChNb":
      return parChNb;
    case "ticket":
      return ticket;
    case "fare":
      return fare;
    case "cabin":
      return cabin;
    case "embarked":
      return embarked;
    default:
      System.err.println("No value for feature " + feature);
      return null;
    }
  }

  @Override
  public Boolean getAnswerValue() {
    return survived;
  }

  @Override
  public void setAnswerValue(Boolean value) {
    this.survived = true;
  }
}
