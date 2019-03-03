import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Writer {
    public static void main(String[] args) {
        Random random = new Random();
        File file= new File("test.in");
        try {
            FileWriter fw= new FileWriter(file);
            fw.write(100 + " " + 1 + "\n");
            for(int i = 0; i < 100; i++) {
                fw.write(random.nextInt(10000000) + " ");
            }
            fw.write("\n");
            for(int i = 0; i < 100; i++) {
                fw.write(random.nextInt(10000000) + " ");
            }
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
