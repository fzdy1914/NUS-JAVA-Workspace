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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class q2 {
    public static void main(String[] args) {
        List<List<String>> contents = q2.getDataContentFromFile(Path.of("crypto.csv"));
        Map<String, List<Double>> ans = new HashMap<>();
        contents.forEach(s -> {
                    if (ans.containsKey(s.get(3))) {
                        List<Double> temp = ans.get(s.get(3));
                        if (Double.parseDouble(s.get(8)) > temp.get(0)) {
                            temp.set(0, Double.parseDouble(s.get(8)));
                        }
                        temp.set(1, Double.parseDouble(s.get(9)) + temp.get(1));
                        temp.set(2, 1 + temp.get(2));

                        double middle = (Double.parseDouble(s.get(4)) + Double.parseDouble(s.get(7))) / 2;
                        if (middle < temp.get(3)) {
                            temp.set(3, middle);
                        }
                        ans.put(s.get(3), temp);
                    } else {
                        List<Double> temp = new LinkedList<>();
                        temp.add(Double.parseDouble(s.get(8))); //highest Volume
                        temp.add(Double.parseDouble(s.get(9))); //total Market cap
                        temp.add(Double.parseDouble("1")); //total Number of transactions
                        temp.add((Double.parseDouble(s.get(4)) + Double.parseDouble(s.get(7))) / 2); //lowest middle value
                        ans.put(s.get(3), temp);
                    }
                });
        List<List<String>> finalAns = new LinkedList<>();
        ans.forEach((s1, s2) -> {
            List<String> temp = new LinkedList<>();
            temp.add(s1);
            temp.add(Long.toString(s2.get(0).longValue()));

            NumberFormat nf = NumberFormat.getInstance();
            nf.setRoundingMode(RoundingMode.HALF_UP);
            nf.setMinimumFractionDigits(0);
            nf.setMaximumFractionDigits(0);
            nf.setGroupingUsed(false);

            temp.add(nf.format((s2.get(1) / s2.get(2))));
            temp.add(Long.toString(s2.get(2).longValue()));
            temp.add(nf.format(s2.get(3)));
            finalAns.add(temp);
        });
        List<List<String>> trueFinalAns
                = finalAns.stream().sorted((s1, s2) -> -s1.get(0).compareTo(s2.get(0))).collect(Collectors.toList());
        q2.saveDataToFile(Path.of("q2.csv"), trueFinalAns);
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
