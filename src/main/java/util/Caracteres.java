/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.security.SecureRandom;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import org.apache.commons.lang.WordUtils;

/**
 *
 * @author eduardo
 */
public class Caracteres {
    
    public static String cpfMask = "###.###.###-##";
    public static String cnpjMask = "##.###.###/####-##";
    public static String cepMask = "##.###-###";
    public static String phoneMask = "(##) ####-####";
    public static String inscricaoCadastralMask = "##.##.###.####.##";

    
    
    public static String removecaracter(String s) {
        if (s != null) {
            s = s.replaceAll("\\.", "")
                    .replace("-", "")
                    .replace("/", "")
                    .replace("-", "")
                    .replace("[", "")
                    .replace("]", "")
                    .replace("{", "")
                    .replace("}", "")
                    .replace("_", "")
                    .replace("(", "")
                    .replace(")", "")
                    .replace(" ", "");
        }
        return s;
    }

    /**
     * Remove caracteres não alphanuméricos, exceto o ponto (.)
     *
     * @param s
     * @return string com os caracteres removidos
     */
    public static String removeCaracterIgnoraPonto(String s) {
        if (s != null) {
            s = s.replace("-", "").replace("/", "").replace("[", "").replace("]", "").replace("{", "").replace("}", "");
        }
        return s;
    }

    public static String removerAcentos(String s) {
        if (s != null) {
            s = s.replaceAll("[ãâàáä]", "a")
                    .replaceAll("[êèéë]", "e")
                    .replaceAll("[îìíï]", "i")
                    .replaceAll("[õôòóö]", "o")
                    .replaceAll("[ûúùü]", "u")
                    .replaceAll("[ÃÂÀÁÄ]", "A")
                    .replaceAll("[ÊÈÉË]", "E")
                    .replaceAll("[ÎÌÍÏ]", "I")
                    .replaceAll("[ÕÔÒÓÖ]", "O")
                    .replaceAll("[ÛÙÚÜ]", "U")
                    .replace('ç', 'c')
                    .replace('Ç', 'C')
                    .replace("[ñǹń]", "n")
                    .replace("[ÑŃǸ]", "N");
        }

        return s;
    } 

      public static String removeCaracterAnexo(String s) {
        if (s != null) {
        s = s.replace("¦", "").replace(":", "").replace("-", "").replaceAll("[ãâàáä]", "a").replaceAll("[êèéëẽ]", "e").replaceAll("[îìíïĩ]", "i").replaceAll("[õôòóö]", "o").replaceAll("[ûúùüũ]", "u").replaceAll("[ÃÂÀÁÄ]", "A").replaceAll("[ÊÈÉËẼ]", "E").replaceAll("[ÎÌÍÏĨ]", "I").replaceAll("[ÕÔÒÓÖ]", "O").replaceAll("[ÛÙÚÜŨ]", "U").replace('ç', 'c').replace('Ç', 'C').replace('ñ', 'n').replace('Ñ', 'N').replaceAll("!", "").replace("/", "").replace("-", "").replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("(", "").replace(")", "").replace("§", "").replace((char) 160, (char) 32);        
        }
        return s;
         
    }
    
       /**
     * Remove caracteres especiais e substitui letras acentuadas 
     *
     * @param text
     * @return
     */
    public static String removeCaracteresEspeciais(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("¦", "").replaceAll("-", "").replaceAll("[ãâàáä]", "a").replaceAll("[êèéë]", "e").replaceAll("[îìíï]", "i").replaceAll("[õôòóö]", "o").replaceAll("[ûúùü]", "u").replaceAll("[ÃÂÀÁÄ]", "A").replaceAll("[ÊÈÉË]", "E").replaceAll("[ÎÌÍÏ]", "I").replaceAll("[ÕÔÒÓÖ]", "O").replaceAll("[ÛÙÚÜ]", "U").replace('ç', 'c').replace('Ç', 'C').replace('ñ', 'n').replace('Ñ', 'N').replaceAll("!", "").replaceAll("\\[\\´\\`\\?!\\@\\#\\$\\%\\¨\\*", " ").replaceAll("\\(\\)\\=\\{\\}\\[\\]\\~\\^\\]", " ").replaceAll("[\\.\\;\\-\\_\\+\\'\\ª\\º\\:\\;\\/\\°]", " ").replace((char) 160, (char) 32).replace("\"", "");
    }
    
    public static String removeEspacos(String text) {
        return text.replace(" ", "");
    }
    
    public static String substituirEspacos(String s) {
        if (s != null) {
            s = s.replace(' ', '_');
        }

        return s;
    }

    public static String addMask(String s, String mask) {

        if (s == null || s.isEmpty()) {
            return "";
        }

        MaskFormatter maskFormatter;

        try {
            maskFormatter = new MaskFormatter(mask);
            maskFormatter.setValueContainsLiteralCharacters(false);
            return maskFormatter.valueToString(s);
        } catch (ParseException ex) {
            System.err.println(ex);
        }

        return "";
    }

    public static String capitalizeFully(String string) {

        if (string == null) {
            return "";
        }

        String word = WordUtils.capitalizeFully(string);

        word = word.replace(" Da ", " da ");
        word = word.replace(" De ", " de ");
        word = word.replace(" Di ", " di ");
        word = word.replace(" Do ", " do ");
        word = word.replace(" Du ", " du ");

        word = word.replace(" Das ", " das ");
        word = word.replace(" Des ", " des ");
        word = word.replace(" Dis ", " dis ");
        word = word.replace(" Dos ", " dos ");
        word = word.replace(" Dus ", " dus ");

        word = word.replace(" Na ", " na ");
        word = word.replace(" Ne ", " ne ");
        word = word.replace(" Ni ", " ni ");
        word = word.replace(" No ", " no ");
        word = word.replace(" Nu ", " nu ");

        word = word.replace(" Nas ", " nas ");
        word = word.replace(" Nes ", " nes ");
        word = word.replace(" Nis ", " nis ");
        word = word.replace(" Nos ", " nos ");
        word = word.replace(" Nus ", " nus ");

        word = word.replace(" A ", " a ");
        word = word.replace(" E ", " e ");
        word = word.replace(" I ", " i ");
        word = word.replace(" O ", " o ");
        word = word.replace(" U ", " u ");

        word = word.replace(" As ", " as ");
        word = word.replace(" Es ", " es ");
        word = word.replace(" Is ", " is ");
        word = word.replace(" Os ", " os ");
        word = word.replace(" Us ", " us ");

        word = word.replace(" Para ", " para ");
        word = word.replace(" Com ", " com ");
        word = word.replace(" M ", " m ");
        word = word.replace(" M. ", " m");
        word = word.replace(" m. ", " m");
        word = word.replace(" Cm ", " cm ");
        word = word.replace(" Mm ", " mm ");

        word = word.replace(" norte", " Norte");
        word = word.replace(" sul", " Sul");
        word = word.replace(" leste", " Leste");
        word = word.replace(" oeste", " Oeste");

        word = word.replace("Para ", "para ").replace("Medindo", "medindo").replace("Limitando-se", "limitando-se").replace("Em Linhas Quebradas", "em linhas quebradas");

        return word;
    }

    public static String formataString(String string, int tamanho, char complemento, boolean alinhaAEsquerda) {
        string = string == null || string.isEmpty() ? "" : string;
        if (string.length() < tamanho) {
            String c = String.valueOf(complemento);
            while (string.length() < tamanho) {
                if (alinhaAEsquerda) {
                    string += c;
                } else {
                    string = c + string;
                }
            }
        } else if (string.length() > tamanho) {
            int diferenca = string.length() - tamanho;
            if (alinhaAEsquerda) {
                string = string.substring(0, string.length() - diferenca);
            } else {
                string = string.substring(diferenca, string.length());
            }
        }

        return string;
    }

    private static String geradorString(int tamanho) {
        SecureRandom random = new SecureRandom();
        String[] numeros = {"0", "1", "b", "2", "4", "5", "6", "7", "8", "9"};
        String[] letras = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
            "x", "y", "z"};

        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < tamanho; i++) {
            if (random.nextBoolean()) {
                senha.append(numeros[random.nextInt(numeros.length)]);
            } else {
                if (random.nextBoolean()) {
                    senha.append(letras[random.nextInt(letras.length)]);
                } else {
                    senha.append(letras[random.nextInt(letras.length)].toUpperCase());
                }
            }
        }
        return senha.toString();
    }
}
