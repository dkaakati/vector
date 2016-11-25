import java.util.*;
/**
 * Created by dania on 23/11/16.
 */
public class Dico {

    /**
     * Created by clementg on 17/11/16.
     */
    public Map<String, Integer> vecteur = new HashMap<>();

    public Map<String, Map<String,Integer>> vecteurs = new HashMap<>();


    public Dico() {
        vecteur.put("annonce de stage", 1);
        vecteur.put("offre de stage", 1);
        vecteur.put("stage", 1);
        vecteur.put("offres d'emploi_stage", 1);
        vecteur.put("offres d'emploi", 1);
        vecteur.put("recrutement", 1);
        vecteur.put("expertise", 1);
        vecteur.put("logiciels", 1);
        vecteur.put("CRM", 1);
        vecteur.put("java", 1);
        vecteur.put("cobol", 1);
        vecteur.put("emploi", 1);
        vecteur.put("stage", 1);
        vecteur.put("Développement", 1);
        vecteur.put("informatique", 1);
        vecteur.put("GPU", 1);
        vecteur.put("CUDA", 1);
        vecteur.put("SPARK", 1);
        vecteur.put("Big data", 1);
        vecteur.put("traitement parallèle", 1);
        vecteur.put("CV", 1);
        vecteur.put("lettre de motivation", 1);

        // Mot NOK
        vecteur.put("Cartouche d'encre", 0);
        vecteur.put("moitié prix", 0);
        vecteur.put("acheté", 0);
        vecteur.put("démarque", 0);
        vecteur.put("cotisation", 0);
        vecteur.put("contrat habitation", 0);
        vecteur.put("soin offerts", 0);
        vecteur.put("50%", 0);
        vecteur.put("promo", 0);
        vecteur.put("remise ", 0);
        vecteur.put("cadeaux", 0);
        vecteur.put("vente flash", 0);
        vecteur.put("ventes flash", 0);
        vecteur.put("vente", 0);
        vecteur.put("prix cassé", 0);
        vecteur.put("prix cassés", 0);
        vecteur.put("carton", 0);
        vecteur.put("gratuit", 0);
        vecteur.put("prix ", 0);
        vecteur.put("soldes", 0);
        vecteur.put("porte-jaretelle", 0);
        vecteur.put("vente flash", 0);
        vecteur.put("ski", 0);
        vecteur.put("séjour", 0);
        vecteur.put("cambriloage", 0);
        vecteur.put("profitez", 0);
        vecteur.put("offert", 0);
        vecteur.put("vacances", 0);
        vecteur.put("assurances", 0);
        vecteur.put("assurance", 0);
        vecteur.put("dentaires", 0);
        vecteur.put("livraisons", 0);
        vecteur.put("livraison", 0);
        vecteur.put("coquines", 0);
        vecteur.put("coquine", 0);
        vecteur.put("buzz", 0);
        vecteur.put("promos", 0);
        vecteur.put("newsletter", 0);
        vecteur.put("devis", 0);
    }
}