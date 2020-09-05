
public class Main {
	
	public static final double CONST = 250000;

	public static void main(String[] args) {
		
		for(int xCoord = 0; xCoord < 40; xCoord++) {
			for(int yCoord = 0; yCoord < 30; yCoord++) {
				
				final int prime = 31;
			    int result = 1;
			    result = prime * result + xCoord;
			    result = prime * result + yCoord;
				
			    System.out.println("x: " + xCoord +"; y: "+yCoord +" - "+ result + " - " + (int)result);
			}
		}

	}

}
