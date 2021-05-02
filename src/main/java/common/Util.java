package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);
    public static void log(String s) {
        System.out.println(s);
    }
    public static void log(String info, Throwable e, String source) {
        if (e != null) {
            logger.error(String.format("%-11s", "Exception:") +
                String.format("%-8s", "[" + source + "]") +
                String.format("%10s", " Reason : ") +
                e.getMessage() +
                (e.getCause() != null && e.getCause().getMessage() != null ? " - " + e.getCause().getMessage() : "" ) +
                (e.getStackTrace() != null ? System.lineSeparator() + String.format("%11s", "Stack : ") + stackTraceToString(e) : "") +
                (e.getCause() != null ?  (System.lineSeparator() + String.format("%11s", "Inner : ") + stackTraceToString(e.getCause())) : "" ) + System.lineSeparator() );
        }
        if (info != "") {
            System.out.println(info);
        }
    }

    private static String stackTraceToString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static /*String*/byte[] loadSampleFingerPrint(String fileName) throws Exception {//change
        //Finger print data parameter
        byte[] fpArr;
        try {
            fpArr = Files.readAllBytes(Paths.get(Util.class.getClassLoader().getResource("SampleFinger_Feature_1.minutea").toURI()));
            /*File f = new File(Util.class.getClassLoader().getResource(nid).toURI());
            FileInputStream fis = new FileInputStream(f);
            byte[] data = new byte[(int) f.length()];
            fis.read(data);
            fis.close();

            fpStr = new String(data, "UTF-8");*/
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to load sample finger print file!", e);
        }
        return fpArr;
    }

    /// <summary>
    /// Read certificate from file
    /// [ this function is used in sample code to load certificate from file and you wont
    /// need it in your project because you will get these certificates from server ]
    /// </summary>
    /// <param name="certName">string certificate name</param>
    /// <returns>returns certificate base64 string</returns>
    public static String readCertificate(String certName) throws Exception {
        String certStr;
        try {
            certStr = Files.readAllLines(Paths.get(certName + ".txt")).get(0);
        } catch (IOException e) {
            throw new Exception("Failed to read certificate file!", e);
        }
        return certStr;
    }

    /// <summary>
    /// Converts provided Hex string to byte array
    /// </summary>
    /// <param name="hexString">Hex string</param>
    /// <returns>Returns corresponding byte array</returns>
    public static byte[] hexStringToByteArray(String hexString) {
        if (hexString == null || hexString.isEmpty())
            return null;
        byte[] byteArr = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2)
            byteArr[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));

        return byteArr;
    }

    /// <summary>
    /// Converts provided byte array to Hex string
    /// </summary>
    /// <param name="bytes"></param>
    /// <returns>Returns corresponding Hex string</returns>
    public static String byteArrayToHexString(byte[] bytes) {
        if (bytes == null)
            return "";
        String S = "";
        for (int i = 0; i < bytes.length; i++)
            S += String.format("%02X", bytes[i]);
        return S;
    }

    public static Integer tryParse(String userInput) {
        Integer parsedInt = null;
        try {
            parsedInt = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            Util.log("Bad input format!");
        }
        return parsedInt;
    }

    public static String byteArrayToBase64String(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] base64StringToByteArray(String str) {
        return Base64.getDecoder().decode(str.getBytes());
    }

    public static void clearConsole() {
        //TODO
    }

    public static void seekForEnter() {
        try {
            System.in.read();
            System.in.skip(System.in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String registrationBase64ToBrowserBase64(String registrationBase64) throws Exception {
        byte[] img = Base64.getDecoder().decode(registrationBase64);

        byte[] subImg = Arrays.copyOfRange(img, 50, img.length);

        return new String(Base64.getEncoder().encode(subImg));
    }
}
