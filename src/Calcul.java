import java.util.Map;

/**
 * Created by dania on 25/11/16.
 */
public class Calcul {
    public static double norme(Map<String, Integer> vector) {
        int result = 0;

        for (Map.Entry<String, Integer> entry : vector.entrySet()) {
            result += entry.getValue() * entry.getValue();
        }

        return Math.sqrt(result);
    }
    public static double scalarProduct(Map<String, Integer> reference, Map<String, Integer> vector){
        //System.out.println(vector);
        int produitscalaire = 0;

        for(Map.Entry<String, Integer> element : reference.entrySet()){
            produitscalaire += vector.get(element.getKey())* element.getValue();
        }
        return produitscalaire;
    }

}
