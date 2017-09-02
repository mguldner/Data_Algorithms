package machinelearning.example.iris;

import machinelearning.kmeans.DoubleTool;
import machinelearning.kmeans.GenericTool;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mathieu on 9/2/17.
 */
public class IrisTool implements GenericTool<Iris> {

    DoubleTool dTool = new DoubleTool();

    @Override
    public Iris calculateMean(List<Iris> points) {
        if(points.isEmpty()){
            return null;
        }
        else {
            double globalSepalLength = 0.0;
            double globalSepalWidth = 0.0;
            double globalPetalLength = 0.0;
            double globalPetalWidth = 0.0;
            Map<String, Integer> globalSpecies = new HashMap<>();
            for (Iris iris : points) {
                globalSepalLength += (Double) iris.getValueForFeature("sepalLength");
                globalSepalWidth += (Double) iris.getValueForFeature("sepalWidth");
                globalPetalLength += (Double) iris.getValueForFeature("petalLength");
                globalPetalWidth += (Double) iris.getValueForFeature("petalWidth");
                String species = (String) iris.getValueForFeature("species");
                globalSpecies.put(species, globalSpecies.containsKey(species) ? globalSpecies.get(species) + 1 : 1);
            }
            return new Iris(globalSepalLength,
                    globalSepalWidth,
                    globalPetalLength,
                    globalPetalWidth,
                    Collections.max(globalSpecies.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey());
        }
    }

    @Override
    public boolean areTheSame(Iris point1, Iris point2) {
        return dTool.areTheSame((Double)point1.getValueForFeature("sepalLength"), (Double)point2.getValueForFeature("sepalLength")) &&
        dTool.areTheSame((Double)point1.getValueForFeature("sepalWidth"), (Double)point2.getValueForFeature("sepalWidth")) &&
        dTool.areTheSame((Double)point1.getValueForFeature("petalLength"), (Double)point2.getValueForFeature("petalLength")) &&
        dTool.areTheSame((Double)point1.getValueForFeature("petalWidth"), (Double)point2.getValueForFeature("petalWidth"));
    }

    @Override
    public double distanceBetween(Iris point1, Iris point2) {
        if(point1 == null)
            System.out.println("point1 null");
        if(point2 == null)
            System.out.println("point2 null");
        double sum = 0.0;
        sum += Math.pow((Double)point1.getValueForFeature("sepalLength") - (Double)point2.getValueForFeature("sepalLength"), 2);
        sum += Math.pow((Double)point1.getValueForFeature("sepalWidth") - (Double)point2.getValueForFeature("sepalWidth"), 2);
        sum += Math.pow((Double)point1.getValueForFeature("petalLength") - (Double)point2.getValueForFeature("petalLength"), 2);
        sum += Math.pow((Double)point1.getValueForFeature("petalWidth") - (Double)point2.getValueForFeature("petalWidth"), 2);
        return Math.sqrt(sum);
    }
}
