import java.io.*;
import java.util.*;

public class IO {

    public static List<String> InpLines(String fileName) throws IOException {

        List<String> lines = new ArrayList<>();
        String line;

        try (BufferedReader inp = new BufferedReader(new FileReader(fileName))) {
            while ((line = inp.readLine()) != null) {
                line = line.trim();
                if (line.equals("")) continue;
                lines.add(line);
            }
        } catch (IOException e) {
            return null;
        }

        if (lines.isEmpty()) return null;
        return lines;
    }

    public static boolean OutLines(String fileName, List<String> lines) throws IOException {

        if ((lines == null) || lines.isEmpty()) return false;

        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            int n = lines.size();
            for (String line : lines) {
                out.println(line.trim());
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}