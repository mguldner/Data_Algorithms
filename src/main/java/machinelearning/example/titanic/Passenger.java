package machinelearning.example.titanic;

import machinelearning.decisiontree.features.Frame;
import machinelearning.general.DataObject;

public class Passenger implements DataObject<Boolean>{

    private Integer passengerId;
    private Integer pClass;
    private Boolean isMale;
    private Double age;
    private Frame ageFrame;
    private Integer sibSpNb;
    private Integer parChNb;
    private Double fare;
    private Frame fareFrame;
    private String embarked;
    private Boolean survived;

    public Passenger(int passengerId, int pClass, boolean isMale, Double age,
            int sibSpNb, int parChNb, Double fare, String embarked) {
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

    public Double getAge() {
        return age;
    }

    public int getSibSpNb() {
        return sibSpNb;
    }

    public int getParChNb() {
        return parChNb;
    }

    public Double getFare() {
        return fare;
    }

    public String getEmbarked() {
        return embarked;
    }
    
    public void setAgeFrame(Frame ageFrame) {
        this.ageFrame = ageFrame;
    }


    public void setFareFrame(Frame fareFrame) {
        this.fareFrame = fareFrame;
    }
    
    public boolean isAgeFrameSet(){
        return this.ageFrame != null;
    }
    
    @Override
    public Object getValueForFeature(String feature) {
        switch(feature){
        case "pClass":
            return pClass;
        case "isMale":
            return isMale;
        case "ageFrame":
            return ageFrame;
        case "sibSpNb":
            return sibSpNb;
        case "parChNb":
            return parChNb;
        case "fareFrame":
            return fareFrame;
        case "embarked":
            return embarked;
        default:
            System.err.println("[ERROR:Passenger] No value for feature " + feature);
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
