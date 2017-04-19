import java.util.Scanner;

public class Main {

	
	public static int Symbol_Jacobbiego(int a, int n){
		
		if(n % 2 == 0)
			return -2;
		
		if( a == 0) return 0;
		if( a == 1) return 1;
		
		int x = 0, y = a;
		
		while(y % 2 == 0){
			y /= 2;
			x++;
		}
		
		int wynik = 1;
		
		if(x % 2 == 1 && (n % 8 == 3 || n % 8 == 5)) 
			wynik = -1;
		else
			wynik = 1;
		
		if(n % 4 == 3 && y % 4 == 3)
			wynik *=(-1);
		
		if(y == 1)
			return wynik;
		else
			return wynik * Symbol_Jacobbiego(n % y, y);
	}
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
	    int a = Integer.parseInt(input.nextLine());
	    
	    int n = Integer.parseInt(input.nextLine());
	    
	    System.out.println("Symbol Jacobiego: " + Symbol_Jacobbiego(a, n));
	}

}
