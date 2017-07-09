package machinelearning.example.titanic;

import machinelearning.decisiontree.features.Frame;
import machinelearning.general.DataObject;

public class Passenger implements DataObject<Boolean>{

  private Integer passengerId;
  private Integer pClass;
  private Boolean isMale;
  private Frame age;
  private Integer sibSpNb;
  private Integer parChNb;
  private Frame fare;
  // Not useful
  //private String cabin;
  private String embarked;
  private Boolean survived;

  public Passenger(int passengerId, int pClass, boolean isMale, Frame age,
      int sibSpNb, int parChNb, Frame fare, String embarked) {
    super();
    this.passengerId = passengerId;
    this.pClass = pClass;
    this.isMale = isMale;
    this.age = age;
    this.sibSpNb = sibSpNb;
    this.parChNb = parChNb;
    this.fare = fare;
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

  public Frame getAge() {
    return age;
  }

  public int getSibSpNb() {
    return sibSpNb;
  }

  public int getParChNb() {
    return parChNb;
  }

  public Frame getFare() {
    return fare;
  }

  public String getEmbarked() {
    return embarked;
  }

  @Override
  public Object getValueForFeature(String feature) {
    switch(feature){
    case "pClass":
      return pClass;
    case "isMale":
      return isMale;
    case "age":
      return age;
    case "sibSpNb":
      return sibSpNb;
    case "parChNb":
      return parChNb;
    case "fare":
      return fare;
    case "embarked":
      return embarked;
    default:
      System.err.println("[ERROR:Passenger2] No value for feature " + feature);
      return null;
    }
  }

  @Override
  public Boolean getAnswerValue() {
    return survived;
  }

  @Override
  public void setAnswerValue(Boolean value) {
    this.survived = value;
  }
}
