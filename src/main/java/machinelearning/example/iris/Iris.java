package machinelearning.example.iris;

import machinelearning.general.DataObject;
import org.apache.log4j.Logger;

/**
 * Created by mathieu on 9/2/17.
 */
public class Iris implements DataObject<String> {

    private final static Logger logger = Logger.getLogger(Iris.class);

    private double sepalLength;
    private double sepalWidth;
    private double petalLength;
    private double petalWidth;
    private String speciesPrediction;
    private String species;

    public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String species){
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.species = species;
    }

    @Override
    public Object getValueForFeature(String feature) {
        switch (feature){
            case "petalLength":
                return petalLength;
            case "petalWidth":
                return petalWidth;
            case "sepalLength":
                return sepalLength;
            case "sepalWidth":
                return sepalWidth;
            case "species":
                return species;
            default:
                logger.error("[ERROR:Iris] No value for feature " + feature);
                return null;
        }
    }

    @Override
    public String getAnswerValue() {
        return this.speciesPrediction;
    }

    @Override
    public void setAnswerValue(String value) {
        this.speciesPrediction = value;
    }
}
