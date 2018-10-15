import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
 
/**
 * ����CSV�ļ�
 */
public class CSVCrate {
	
	public static void main(String[] args) {
		CSVCrate test = new CSVCrate();
		test.createCSV();
		test.readdata();
		
	}

     /**
     * ����CSV�ļ�
     */
    public void createCSV() {
        
        // ���ͷ
        Object[] head = { "firstTestField", "secondTestField", "thirdTestField"};
        List<Object> headList = Arrays.asList(head);

        //����
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        List<Object> rowList = null;
        for (int i = 0; i < 5; i++) {
            rowList = new ArrayList<Object>();
            rowList.add("1");
            rowList.add("one");
            rowList.add("\"one, one\"");
            dataList.add(rowList);
        }
       
        String fileName = "testCSV2.csv";//�ļ�����
        String filePath = "c:/test/"; //�ļ�·��
        
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
        	csvFile = new File(filePath + fileName);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312ʹ��ȷ��ȡ�ָ���","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
    
            int num = headList.size() / 2;
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < num; i++) {
                buffer.append(" ,");
            }
            csvWtriter.write("Test" + buffer.toString() +  buffer.toString());
            csvWtriter.newLine();

            // д���ļ�ͷ��
            writeRow(headList, csvWtriter);
 
            // д���ļ�����
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * дһ������
     * @param row �����б�
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("").append(data).append(",").toString();
            csvWriter.write(rowStr);
         }
         csvWriter.newLine();
    }
    
    public static void readdata() {
    	try {             
    		BufferedReader reader = new BufferedReader(new FileReader("c:/test/testCSV.csv"));//��������ļ���           
    		reader.readLine();//��һ����Ϣ��Ϊ������Ϣ������,�����Ҫ��ע�͵�       
    		String line = null;            
    		while((line=reader.readLine())!=null){          
    			String item[] = line.split(",");//CSV��ʽ�ļ�Ϊ���ŷָ����ļ���������ݶ����з�        
    			//int value = Integer.parseInt(last);//�������ֵ������ת��Ϊ��ֵ
    			for(String last: item) {
    				//System.out.println(last.substring(1, last.length() - 1));         
    			}
    		}        
    	} catch (Exception e) { 
    		e.printStackTrace();         
    	} 
    }

}