import java.io.BufferedReader;
import java.io.FileReader;

public class DecodeWordFromFile {
    public static void main(String[] args) throws Exception {
        FileReader fileReader = new FileReader("src/main/resources/TextWithWord.txt");
        BufferedReader readerTextWithWord = new BufferedReader(fileReader);

        StringBuilder textWithWord = new StringBuilder();
        String line = readerTextWithWord.readLine();
        textWithWord.append(line);
        while (line != null) {
            line = readerTextWithWord.readLine();
            if (line != null) {
                textWithWord.append("\n" + line);
            }
        }
        readerTextWithWord.close();

        StringBuilder stringBits = new StringBuilder();
        char[] textWithWordChars = textWithWord.toString().toCharArray();
        for (int i = 0, j = 0; i < textWithWordChars.length - 1 && (j < 8 || stringBits.length() % 8 != 0); i++) {
            if (textWithWordChars[i] == ' ' && textWithWordChars[i + 1] == ' ') {
                j = 0;
                i++;
                stringBits.append("1");
            }
            else if (textWithWordChars[i] == ' ') {
                j++;
                stringBits.append("0");
            }
        }

        char[] bits = stringBits.delete(stringBits.length() - 8, stringBits.length())
                .toString()
                .toCharArray();

        byte[] bytes = new byte[bits.length / 8];
        int byteFromBits = 0;
        for (int i = 0, degree = 7; i < bits.length; i++) {
            byteFromBits += Math.pow(2, degree) * Character.getNumericValue(bits[i]);

            if (degree == 0) {
                degree = 7;
                bytes[i / 8] = (byte)byteFromBits;
                byteFromBits = 0;
            }
            else {
                degree--;
            }
        }

        String word = new String(bytes);
        System.out.println(word);
    }
}