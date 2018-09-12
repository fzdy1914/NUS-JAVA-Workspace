package diy;

public class Printout {

	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable(){
			int count = 10;
			public void run() {
				while(true) {
					if(count == -1) break;
					try {
						Thread.sleep(100);
						System.out.println(count--);
						
					} catch(Exception e) {
						
					}
					
				}
			}	
		});
		thread.start();
	}

}
