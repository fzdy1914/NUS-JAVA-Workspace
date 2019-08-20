import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
    public static void main(String[] args) {
        String pathname_linux = "E:/test/mac_whiteOnBlack.bss";
        String pathname_win = "E:/test/linux_whiteOnBlack.bss";
        try {
            FileReader reader_linux = new FileReader(pathname_linux);
            BufferedReader br_linux = new BufferedReader(reader_linux);
            FileReader reader_win = new FileReader(pathname_win);
            BufferedReader br_win = new BufferedReader(reader_win);
            String line_linux;
            int i = 0;
            while ((line_linux = br_linux.readLine()) != null) {

                String line_win = br_win.readLine();
                if(line_linux.equals(line_win)){
                    continue;
                } else {
                    System.out.println(line_linux);
                    System.out.println(line_win);
                    if(i++ != 0) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
