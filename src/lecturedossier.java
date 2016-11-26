import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;


/* Created by dania on 17/11/16.
*/
public class lecturedossier {

    public static void main(String args[]){
        ArrayList<String> listSpam = new ArrayList<>();
        ArrayList<String> listOk = new ArrayList<>();

        Dico unDico =  new Dico();
        try{
            File dir = new File("/home/dania/Documents/EML_a_trier");

            File[] children = dir.listFiles();
            if (children == null) {
                System.out.println("Directory does not exist or is not a Directory");
            } else {
                Map<String,Integer> reference = unDico.vecteur;
                Map<String, Map<String,Integer>> vecteurs = new HashMap<>();

                for (int i=0; i < dir.listFiles().length;i++) {
                    //Parametrage du mail
                    Properties props = System.getProperties();
                    props.put("mail.host", "smtp.dummydomain.com");
                    props.put("mail.transport.protocol", "smtp");
                    Session mailSession = Session.getDefaultInstance(props, null);
                    InputStream source = new FileInputStream(children[i]);
                    MimeMessage message = new MimeMessage(mailSession, source);
                    //Récuperation du contenu
                    String contenu = getTextFromMessage(message).toLowerCase();

                    //Traitement
                    Map<String,Integer> vecteurcourant = new HashMap<>();

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
                    vecteurs.put(children[i].toString(),vecteurcourant);

                }
                int k =0;
                for(Map.Entry<String, Map<String, Integer>> vecteur : vecteurs.entrySet()) {
                    double scalarproduct = Calcul.scalarProduct(reference, vecteur.getValue());
                    double cos =0;
                    if (scalarproduct>0.0){
                        cos = scalarproduct / (Calcul.norme(reference) * Calcul.norme(vecteur.getValue()));
                    }
                    System.out.println(cos);
                    System.out.println(vecteur.getKey());
                    if (cos < 0.52) {
                        listSpam.add(vecteur.getKey());
                        System.out.println("Spam");
                    }else{
                        listOk.add(vecteur.getKey());
                        System.out.println("Ok");
                    }
                    System.out.println(k);
                    k++;

                }

            }
        } catch (Exception ioe){
            ioe.printStackTrace();
        }

        insertCsv(listOk,true);
        insertCsv(listSpam,false);
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
    private static String getTextFromMessage(Message message) throws Exception {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws Exception{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

    public static void insertCsv (ArrayList<String> list, boolean ok){
        FileWriter fileWriter = null;
        String wording = "listOk";
        if(!ok){
            wording = "listSpam";
        }

        try {
            fileWriter = new FileWriter("/home/dania/Documents/"+ wording +".csv");

            for (String nomFichier : list) {
                fileWriter.append(nomFichier);
                fileWriter.append("\n");
            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }

    }
}
