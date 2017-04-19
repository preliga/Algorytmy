package TestFermata;

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
	
	public static String test_fermata(int n, int k){
		int b = 0;
	    Random rand = new Random();
	    for(int i = 0; i < k; i++){
	    	b = rand.nextInt(n-2) + 1;
	    	if(potega_modulo(b, n - 1, n) != 1){
	    		return "Z³o¿ona ";
	    	}
	    }
	    return "Prawdopodonie pierwsza";
	}
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
	    int n = Integer.parseInt(input.nextLine());
	    
//	    text = input.nextLine();		
//	    int k = Integer.parseInt(text);
	    
	    System.out.println(test_fermata(n, 1500) );
	    
//	    for(int i = 2; i <15;i++){
//	    	String s = test_fermata(i,1);
//	    	//if(   s.compareTo("Prawdopodonie pierwsza") == 0)
//	    		System.out.println(s + i);
//	    }
	}
}
