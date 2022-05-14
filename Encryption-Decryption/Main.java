package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String operation = "enc";
        String thePhrase = "";
        String outPath = "";
        String inPath = "";
        int theKey = 0;
        String theResult="";
        String alg = "shift";
        //System.out.println(args.length);
        for (int i = 0; i < args.length; i += 2) {
            if ("-mode".equals(args[i]) && i+1 < args.length) {
                operation = args[i + 1];
            }
            if ("-key".equals(args[i]) && i+1 < args.length) {
                theKey = Integer.parseInt(args[i + 1]);
            }
            if ("-data".equals(args[i]) && i+1 < args.length) {
                thePhrase = args[i + 1];
            }
            if ("-out".equals(args[i]) && i+1 < args.length) {
                outPath = args[i + 1];
            }
            if ("-in".equals(args[i]) && i+1 < args.length) {
                inPath = args[i + 1];
            }
            if ("-alg".equals(args[i]) && i+1 < args.length) {
                alg = args[i + 1];
            }
        }
        if (!"".equals(inPath)) {
            thePhrase = getThePhraseFromFile(inPath);
        }

        Converter converter = new Converter();

        if (operation.equals("enc")) {
            if (alg.equals("shift")) {
                converter.setMethod(new encryptionShift());
            } else {
                converter.setMethod(new encryptionUnicode());
            }
            theResult = converter.convert(thePhrase, theKey);
        }

        if (operation.equals("dec")) {
            if (alg.equals("shift")) {
                converter.setMethod(new decryptionShift());
            } else {
                converter.setMethod(new decryptionUnicode());
            }
            theResult = converter.convert(thePhrase, theKey);
        }

        if ("".equals(outPath)) {
            System.out.println(theResult);
        } else {
            writeTheResult(outPath, theResult);
        }
    }

    private static String getThePhraseFromFile (String pathToFile) {
        File file = new File(pathToFile);
        String thePhrase = null;
        try (Scanner scanner = new Scanner(file)) {
            thePhrase = scanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Error: no file found: " + pathToFile);
        }
        return thePhrase;
    }
    private static void writeTheResult(String pathToFile, String data) {
        File file = new File(pathToFile);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("Error: can't manage with this path: " + pathToFile);
            System.exit(1);
        }

    }
}

class Converter {

    private ConverterMethod method;

    public void setMethod(ConverterMethod method) {
        this.method = method;
    }

    public String convert(String thePhrase, int theKey) {
        return this.method.convert(thePhrase, theKey);
    }
}

interface ConverterMethod {

    String convert(String thePhrase, int theKey);

}

class encryptionUnicode implements ConverterMethod {

    @Override
    public String convert(String thePhrase, int theKey) {

        char[] thePhraseAsCharArray = thePhrase.toCharArray();

        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < thePhrase.length(); i++) { //for each letter in the phrase
            if ((thePhraseAsCharArray[i] + theKey) >126) {
                encryptedMessage.append((char) ((thePhraseAsCharArray[i] + theKey) % 126 + 31));
            } else {
                encryptedMessage.append((char) (thePhraseAsCharArray[i] + theKey));
            }
        }
        return encryptedMessage.toString();
    }
}

class decryptionUnicode implements ConverterMethod {

    @Override
    public  String convert(String thePhrase, int theKey) {
        char[] thePhraseAsCharArray = thePhrase.toCharArray();

        StringBuilder decryptedMessage = new StringBuilder();
        for (int i = 0; i < thePhrase.length(); i++) { //for each letter in the phrase
            if ((thePhraseAsCharArray[i] - theKey) < 32) {
                decryptedMessage.append((char) ((thePhraseAsCharArray[i] - theKey) + 126 - 31));
            } else {
                decryptedMessage.append((char) (thePhraseAsCharArray[i] - theKey));
            }
        }
        return decryptedMessage.toString();
    }
}

class encryptionShift implements ConverterMethod {
    static final String allLetters = "abcdefghijklmnopqrstuvwxyz";
    static final String allLettersUp = allLetters.toUpperCase();
    @Override
    public String convert(String thePhrase, int theKey) {

        char[] thePhraseAsCharArray = thePhrase.toCharArray();
        char[] lettersAsCharArray = allLetters.toCharArray();
        char[] lettersUpAsCharArray = allLettersUp.toCharArray();

        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < thePhrase.length(); i++) {
            if(Arrays.binarySearch(lettersAsCharArray, thePhraseAsCharArray[i]) >= 0) {
                encryptedMessage.append(lettersAsCharArray[
                        (Arrays.binarySearch(lettersAsCharArray, thePhraseAsCharArray[i]) + theKey) % allLetters.length()]);
            } else if (Arrays.binarySearch(lettersUpAsCharArray, thePhraseAsCharArray[i]) >= 0) {
                encryptedMessage.append(lettersUpAsCharArray[
                        (Arrays.binarySearch(lettersUpAsCharArray, thePhraseAsCharArray[i]) + theKey) % allLettersUp.length()]);
            } else {
                encryptedMessage.append(thePhraseAsCharArray[i]);
            }
        }
        return encryptedMessage.toString();
    }
}

class decryptionShift implements ConverterMethod {
    static final String allLetters = "abcdefghijklmnopqrstuvwxyz";
    static final String allLettersUp = allLetters.toUpperCase();
    @Override
    public String convert(String thePhrase, int theKey) {
        //System.out.println("Decriptios shift start");

        char[] thePhraseAsCharArray = thePhrase.toCharArray();
        char[] lettersAsCharArray = allLetters.toCharArray();
        char[] lettersUpAsCharArray = allLettersUp.toCharArray();

        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < thePhrase.length(); i++) {
            if (Arrays.binarySearch(lettersAsCharArray, thePhraseAsCharArray[i]) < 0 &&
                    Arrays.binarySearch(lettersUpAsCharArray, thePhraseAsCharArray[i]) < 0) {
                //System.out.println("It's not a letter");
                encryptedMessage.append(thePhraseAsCharArray[i]);
                continue;
            }

            if(Arrays.binarySearch(lettersAsCharArray, thePhraseAsCharArray[i]) >= 0) {
                //System.out.println("It's lowCase letter");
                if ((Arrays.binarySearch(lettersAsCharArray, thePhraseAsCharArray[i]) - theKey) >= 0) {
                    //System.out.println("it's more then 0");
                    encryptedMessage.append(lettersAsCharArray[
                            (Arrays.binarySearch(lettersAsCharArray, thePhraseAsCharArray[i]) - theKey) % allLetters.length()]);
                } else {
                    //System.out.println("it's less then 0");
                    encryptedMessage.append(lettersAsCharArray[
                            (Arrays.binarySearch(lettersAsCharArray, thePhraseAsCharArray[i]) - theKey) % allLetters.length() + allLetters.length()]);
                }
            }

            if(Arrays.binarySearch(lettersUpAsCharArray, thePhraseAsCharArray[i]) >= 0) {
                //System.out.println("It's upCase letter");
                if ((Arrays.binarySearch(lettersUpAsCharArray, thePhraseAsCharArray[i]) - theKey) >= 0) {
                    //System.out.println("it's more then 0");
                    encryptedMessage.append(lettersUpAsCharArray[
                            (Arrays.binarySearch(lettersUpAsCharArray, thePhraseAsCharArray[i]) - theKey) % allLetters.length()]);
                } else {
                    //System.out.println("it's less then 0");
                    encryptedMessage.append(lettersUpAsCharArray[
                            (Arrays.binarySearch(lettersUpAsCharArray, thePhraseAsCharArray[i]) - theKey) % allLetters.length() + allLetters.length()]);
                }
            }
        }

        return encryptedMessage.toString();
    }
}
