import java.io.*;
import java.util.Arrays;
import java.util.List;

public class EncryptionWordInFile {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStreamWord = new FileInputStream("src/main/resources/Word.txt");

        FileReader fileReader = new FileReader("src/main/resources/Text.txt");
        BufferedReader readerText = new BufferedReader(fileReader);

        FileWriter fileWriter = new FileWriter("src/main/resources/TextWithWord.txt");
        BufferedWriter writerText = new BufferedWriter(fileWriter);

        StringBuilder text = new StringBuilder();
        String line = readerText.readLine();
        text.append(line);
        while (line != null) {
            line = readerText.readLine();
            if (line != null) {
                text.append("\n" + line);
            }
        }
        readerText.close();

        byte[] bytes = inputStreamWord.readAllBytes();
        List<String> splittedText = Arrays.asList(text.toString().split(" "));
        text = text.delete(0, text.length());
        StringBuilder bits = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            bits.append(String.format("%8s", Integer.toBinaryString(bytes[i]))
                    .replace(" ", "0"));
        }

        char[] charsBits = bits.toString().toCharArray();
        text.append(splittedText.get(0));
        for (int i = 0; i < charsBits.length; i++) {
            if (charsBits[i] == '1') {
                text.append("  " + splittedText.get(i + 1));
            }
            else {
                text.append(" " + splittedText.get(i + 1));
            }
        }
        for (int i = charsBits.length + 1; i < splittedText.size(); i++) {
            text.append(" " + splittedText.get(i));
        }

        writerText.write(text.toString());
        writerText.close();
    }
}