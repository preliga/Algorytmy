import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	
	public static String dec_to_bin_rev(int x){
		String s ="";
		
		while(x != 0){
			if(x % 2 == 1) s += "1" ;
			else s += "0" ;
			x /= 2;
		}
		
		return s;
	}
	
	public static int potega_modulo(int b, int n, int m){
		String bin = dec_to_bin_rev(n);
		int potega = 1;
		int[] tab = new int[bin.length()];
		
		tab[0] = b % m;
		if(bin.substring(0,1).compareTo("1") == 0) potega = (potega * tab[0]) % m;;
		
		for(int i = 1; i < bin.length();i++){

			tab[i] = (int) (Math.pow(tab[i-1], 2) % m); 

			if( bin.substring(i,i+1).compareTo("1") == 0){
				potega = (potega * tab[i]) % m;
			}
		
		}
		return potega;
	}
	
	public static boolean test_fermata(int n, int k){
		int b = 0;
	    Random rand = new Random();
	    for(int i = 0; i < k; i++){
	    	b = rand.nextInt(n-2) + 1;
	    	if(potega_modulo(b, n - 1, n) != 1){
	    		System.out.println("Liczba N: "+ n + " Zlozona");
	    		return false;//"Z³o¿ona";
	    	}
	    }
	    System.out.println("Liczba N: "+ n + " Pierwsza");
	    return true;//"Prawdopodonie pierwsza";
	}
	
	
	
	
public static ArrayList faktoryzcja(int a){
	ArrayList lista = new ArrayList();
	
	for(int i = 2; i * i < a; i++ ){
		if(a % i == 0){
			lista.add(i);
			a /= i;
			i = 2;
		}
	}
	return lista;
}
	
	
public static boolean Kongruencja(int a, int b, int m){
	while(a < 0) a += m;
	while(b < 0) b += m;
	
	return a % m == b % m;
}

public static int Symbol_Lagrangea(int a, int p){
		
		if(a >= p || a < 0) return Symbol_Lagrangea(a % p, p);
		else if(a == 0 || a == 1) return a;
		else if(a == 2){
			if(Kongruencja(p, 1, 8) || Kongruencja(p, -1, 8))
				return 1;
			else
				return -1;
		}
		else if (a == p - 1){
			if(Kongruencja(p, 1, 4))
				return 1;
			else 
				return -1;
		}
		else if (!test_fermata(a,1500)){
			ArrayList lista = faktoryzcja(a); //rozk³ad a na czynniki pierwsze
			
			int x = 1;
			
			for(Object i : lista){
				x *= Symbol_Lagrangea((int)i, p);
			}
			
			return x;// iloczyn wszystkich Symbol_lagrangea(pi,p); dla rozk³adu a
		}
		else{
			if(Kongruencja((p-1)/2, 0, 2) || Kongruencja((a-1)/2, 0, 2))
				return Symbol_Lagrangea(p, a);
			else
				return -Symbol_Lagrangea(p, a); 
		}
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
				
		int a = Integer.parseInt(input.nextLine());
			    
		int p = Integer.parseInt(input.nextLine());
			
		System.out.println("Symbol Lagrangea: " + Symbol_Lagrangea(a,p));

	}

}
