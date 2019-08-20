import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class q4 {
    public static void main(String[] args) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(0);
        nf.setGroupingUsed(false);

        List<List<String>> contents = q4.getDataContentFromFile(Path.of("crypto.csv"));
        List<List<String>> ans = contents.stream()
                .map(s -> {
                    switch (s.get(0)) {
                        case "2013":
                            s.set(8, nf.format(Double.parseDouble(s.get(8))* 1.3));
                            s.set(9, nf.format(Double.parseDouble(s.get(9))* 1.3));
                            return s;
                        case "2014":
                            s.set(8, nf.format(Double.parseDouble(s.get(8))* 1.25));
                            s.set(9, nf.format(Double.parseDouble(s.get(9))* 1.25));
                            return s;
                        case "2015":
                            s.set(8, nf.format(Double.parseDouble(s.get(8))* 1.2));
                            s.set(9, nf.format(Double.parseDouble(s.get(9))* 1.2));
                            return s;
                        case "2016":
                            s.set(8, nf.format(Double.parseDouble(s.get(8))* 1.15));
                            s.set(9, nf.format(Double.parseDouble(s.get(9))* 1.15));
                            return s;
                        case "2017":
                            s.set(8, nf.format(Double.parseDouble(s.get(8))* 1.1));
                            s.set(9, nf.format(Double.parseDouble(s.get(9))* 1.1));
                            return s;
                    }
                    return s;
                })
                .collect(Collectors.toList());
        q4.saveDataToFile(Path.of("q4.csv"), ans);
    }

    public static List<List<String>> getDataContentFromFile(Path file) {
        BufferedReader reader;
        List<List<String>> contents = new LinkedList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile())));
            String contentLine;
            while ((contentLine = reader.readLine()) != null) {
                List<String> content = getContentFromLine(contentLine);
                contents.add(content);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error happens");
        }
        return contents;
    }

    public static List<String> getContentFromLine(String contentLine) {
        boolean hasQuotesBefore = false;
        boolean hasCommaBefore = true;
        LinkedList<String> content = new LinkedList<>();
        String stringStack = "";
        for (int i = 0; i < contentLine.length(); i++) {
            if (contentLine.charAt(i) == ',') {
                if (!hasQuotesBefore) {
                    if ("".equals(stringStack)) {
                        if (hasCommaBefore) {
                            content.add(stringStack);
                        } else {
                            hasCommaBefore = true;
                        }
                    } else {
                        content.add(stringStack);
                        stringStack = "";
                        hasCommaBefore = true;
                    }
                } else {
                    stringStack += ",";
                }
            } else if (contentLine.charAt(i) == '\"') {
                if (!hasQuotesBefore) {
                    hasQuotesBefore = true;
                } else {
                    content.add(stringStack);
                    stringStack = "";
                    hasQuotesBefore = false;
                }
                hasCommaBefore = false;
            } else {
                stringStack += contentLine.charAt(i);
                hasCommaBefore = false;
            }
        }
        if ("".equals(stringStack)) {
            if (hasCommaBefore) {
                content.add(stringStack);
            }
        } else {
            content.add(stringStack);
        }
        return content;
    }

    public static void saveDataToFile(Path file, List<List<String>> contents) {
        BufferedWriter writer = null;
        try {
            file.toFile().delete();
            file.toFile().createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.toFile())));
            String separator = ",";

            int fieldsLength = contents.get(0).size();

            for (List<String> content : contents) {
                List<String> csvStandardContent = getCsvStandardContent(content);
                for (int i = 0; i < fieldsLength - 1; i++) {
                    writer.write(csvStandardContent.get(i) + separator);
                }
                writer.write(csvStandardContent.get(fieldsLength - 1) + "\n");
            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.err.println("Error happens");
        }
    }

    public static List<String> getCsvStandardContent(List<String> content) {
        return content.stream()
                .map(field -> field.contains(",") ? "\"" + field + "\"" : field)
                .collect(Collectors.toList());
    }
}
