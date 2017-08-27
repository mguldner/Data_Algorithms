package machinelearning.general;

/**
 * 
 * @author mathieu
 *
 * @param <T> le type de retour : Integer si mort ou vivant, String pour nom du fruit/légume
 */
public interface DataObject<T> {

    Object getValueForFeature(String feature);
    T getAnswerValue();
    void setAnswerValue(T value);
}
