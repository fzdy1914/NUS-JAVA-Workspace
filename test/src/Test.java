import java.util.*;

public class Test {

	public static void main(String[] args) {
		int A = 12345;
		int result = 0;
	
		//System.out.println("\n ab");
		
		", a,b,".codePoints().map(c -> c == ',' ? '\\' : c).forEach(a->System.out.print((char)a));
		System.out.println();
		
		String contentLine = "steven,\"2,638,341,940\",123";
		
		//System.out.print(last);
		//System.out.print(last.substring(1, last.length() - 1));
		String.valueOf(", ab".codePoints().map(c -> c == ',' ? '\\' : c));
		
		
		boolean hasQuotesBefore = false;
		boolean hasCommaBefore = true;
        LinkedList<String> content = new LinkedList<>();
        String stringStack = "";
        for (int i = 0; i < contentLine.length(); i++) {
            if (contentLine.charAt(i) == ',') {
                if (!hasQuotesBefore) {
                	if("".equals(stringStack)) {
	                    if (hasCommaBefore) {
	                    	content.add(stringStack);
	                    } else {
	                    	hasCommaBefore = true;
	                    }
                	} else {
                		content.add(stringStack);
	                    stringStack = "";
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
		
		for(String s: content) {
			System.out.println(s);
		}
		/*
	    /**
	     * Saves the data in the file in xml format.
	     *
	     * @param file Points to a valid xml file containing data that match the {@code classToConvert}.
	     *             Cannot be null.
	     * @throws FileNotFoundException Thrown if the file is missing.
	     /
	    public static <T> void saveDataToFile(Path file, T data) throws FileNotFoundException {
	        requireNonNull(file);
	        requireNonNull(data);

	        if (!Files.exists(file)) {
	            throw new FileNotFoundException("File not found : " + file.toAbsolutePath());
	        }

	    }
	    */
	}
}
