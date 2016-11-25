import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/* Created by dania on 17/11/16.
*/
public class lecturedossier {

    public static void main(String args[]){
        Dico unDico =  new Dico();
        try{
            File dir = new File("/home/dania/Documents/EML_a_trier");

            File[] children = dir.listFiles();
            if (children == null) {
                System.out.println("Directory does not exist or is not a Directory");
            } else {
                Map<String,Integer> reference = unDico.vecteur;
                Map<String, Map<String,Integer>> vecteurs = new HashMap<>();

                for (int i=0; i < dir.listFiles().length && i<1;i++) {
                    //Parametrage du mail
                    Properties props = System.getProperties();
                    props.put("mail.host", "smtp.dummydomain.com");
                    props.put("mail.transport.protocol", "smtp");
                    Session mailSession = Session.getDefaultInstance(props, null);
                    InputStream source = new FileInputStream(children[i]);
                    MimeMessage message = new MimeMessage(mailSession, source);
                    //Récuperation du contenu
                    String contenu = getNoteBody(message).toLowerCase();

                    //Traitement
                    Map<String,Integer> vecteurcourant = new HashMap<>();
                    int produitscalaire = 0;
                    for(Map.Entry<String, Integer> element : reference.entrySet()){

                        String mot = element.getKey();
                        Pattern pattern = Pattern.compile(mot);

                        Matcher matcher = pattern.matcher(contenu);
                        int count = 0;
                        while (matcher.find()){
                            count++;
                        }
                        vecteurcourant.put(mot,count);
                    }
                    double res = scalarProduct(reference, vecteurcourant) / norme(reference) * norme(vecteurcourant);
                   System.out.println(res);
                    vecteurs.put(children[i].toString(),vecteurcourant);

                }
                System.out.println(vecteurs);

            }
        } catch (Exception ioe){
            ioe.printStackTrace();
        }
        //SOmme des count au carré puis la racine sur cette somme


    }

    private static String getNoteBody(Message message) throws Exception {
        if (message.getContentType().toLowerCase(Locale.ENGLISH).startsWith("text/plain") || message.getContentType().toLowerCase(Locale.ENGLISH).startsWith("text/html")) {
            return (String) message.getContent();
        } else if (message.getContentType().toLowerCase(Locale.ENGLISH).startsWith("multipart/")) {
            MimeMultipart multipart = (MimeMultipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getContentType().toLowerCase(Locale.ENGLISH).startsWith("text/plain")) {
                    return (String) bodyPart.getContent();
                } else if (bodyPart.getContentType().toLowerCase(Locale.ENGLISH).startsWith("text/html")) {
                    return (String) bodyPart.getContent().toString();
                }
            }
        }
        return "";
    }
    public static double norme(Map<String, Integer> vector) {
        int result = 0;

        for (Map.Entry<String, Integer> entry : vector.entrySet()) {
            result += entry.getValue() * entry.getValue();
        }

        return Math.sqrt(result);
    }
    public static Integer scalarProduct(Map<String, Integer> reference, Map<String, Integer> vector){
        int produitscalaire = 0;

        for(Map.Entry<String, Integer> element : reference.entrySet()){
            produitscalaire += vector.get(element.getKey())* element.getValue();
        }
        return produitscalaire;
    }


}
