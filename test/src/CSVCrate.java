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
 * 创建CSV文件
 */
public class CSVCrate {
	
	public static void main(String[] args) {
		CSVCrate test = new CSVCrate();
		test.createCSV();
		test.readdata();
		
	}

     /**
     * 创建CSV文件
     */
    public void createCSV() {
        
        // 表格头
        Object[] head = { "firstTestField", "secondTestField", "thirdTestField"};
        List<Object> headList = Arrays.asList(head);

        //数据
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        List<Object> rowList = null;
        for (int i = 0; i < 5; i++) {
            rowList = new ArrayList<Object>();
            rowList.add("1");
            rowList.add("one");
            rowList.add("\"one, one\"");
            dataList.add(rowList);
        }
       
        String fileName = "testCSV2.csv";//文件名称
        String filePath = "c:/test/"; //文件路径
        
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
        	csvFile = new File(filePath + fileName);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
    
            int num = headList.size() / 2;
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < num; i++) {
                buffer.append(" ,");
            }
            csvWtriter.write("Test" + buffer.toString() +  buffer.toString());
            csvWtriter.newLine();

            // 写入文件头部
            writeRow(headList, csvWtriter);
 
            // 写入文件内容
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
     * 写一行数据
     * @param row 数据列表
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
    		BufferedReader reader = new BufferedReader(new FileReader("c:/test/testCSV.csv"));//换成你的文件名           
    		reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉       
    		String line = null;            
    		while((line=reader.readLine())!=null){          
    			String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分        
    			//int value = Integer.parseInt(last);//如果是数值，可以转化为数值
    			for(String last: item) {
    				//System.out.println(last.substring(1, last.length() - 1));         
    			}
    		}        
    	} catch (Exception e) { 
    		e.printStackTrace();         
    	} 
    }

}