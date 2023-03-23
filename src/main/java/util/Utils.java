/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author eduardo
 */
public class Utils {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * <p>
     * É considerado vazio todo e qualquer array cujo valor seja
     * <b>null</b>, ou o tamanho seja <b>zero</b>, ou todos os elementos
     * contidos no array sejam <b>null</b>. </p>
     *
     * @param args
     * @return
     */
    public static boolean isEmpty(Object... args) {

        // Qualquer valor passado que seja nulo sera considerado como vazio
        if (args == null || args.length == 0) {
            return true;
        } else {
            // pecorre o array, se existe um item não nulo é porque o array não esta vazio
            for (Object object : args) {
                if (object != null) {
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean isNotEmpty(Object... args) {
        return !isEmpty(args);
    }

    public static boolean validarCPF(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean validarCNPJ(String cnpj) {
        CNPJValidator validator = new CNPJValidator();
        try {
            validator.assertValid(cnpj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int calcularDigito(String str, int peso) {
        int soma = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            soma += Integer.parseInt(str.substring(i, i + 1)) * peso--;
            if (peso < 2) {
                peso = 9;
            }
        }
        int digito = 11 - (soma % 11);
        return digito > 9 ? 0 : digito;
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String convertDateToString(Date date, String mask) {
        SimpleDateFormat format = new SimpleDateFormat(mask);
        String result = format.format(date);
        return result;
    }

    public static String colocarMascaraCep(String cep) {
        cep = cep.substring(0, 2) + "." + cep.substring(2, 5) + "-" + cep.substring(5, 8);
        return cep;
    }

}
