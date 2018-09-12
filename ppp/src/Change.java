import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class Change {
    String textHtml = "";
    String color = "#00688B";
    //��ȡ�ļ�
    public void ReadFile(String filePath) {
        BufferedReader bu = null;
        InputStreamReader in = null;
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                in = new InputStreamReader(new FileInputStream(file));
                bu = new BufferedReader(in);
                String lineText = null;
                textHtml = "<html><body>";
                while ((lineText = bu.readLine()) != null) {
                    lineText = changeToHtml(lineText);
                    lineText += "</br>";
                    textHtml += lineText;
                }
                textHtml += "</html></body>";
            } else {
                System.out.println("�ļ�������");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bu.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    //����ļ�
    public void writerFile(String writepath) {
        File file = new File(writepath);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            System.out.println(textHtml);
            output.write(textHtml);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    //�ļ�ת��
    public String changeToHtml(String text) {
        text = text.replace("&", "&");
        text = text.replace(" ", " ");
        text = text.replace("<", "<");
        text = text.replace(">", ">");
        text = text.replace("\"", "\"");
        text = text.replace(" ", "    ");
        text = text.replace("public", "<b><font color='"+color+"'>public</font></b>");
        text = text.replace("class", "<b><font color='"+color+"'>class</font></b>");
        text = text.replace("static", "<b><font color='"+color+"'>static</font></b>");
        text = text.replace("void", "<b><font color='"+color+"'>void</font></b>");
        String t = text.replace("//", "<font color=green>//");
        if (!text.equals(t)) {
            System.out.println("t:"+t);
            text = t + "</font>";
        }
        return text;
    }
 
    public static void main(String[] args) {
        System.out.println("��һ������Ϊ��ȡ�ļ�·�����ڶ�������Ϊ�����ļ�·��");
        if(args.length<1){
            System.out.println("�������ļ�</a>·��");
            return ;
        }else if(args.length<2){
            System.out.println("�����������ļ�");
            return;
        }
        Change c = new Change();
        c.ReadFile(args[0]);
        c.writerFile(args[1]);
    }
}
