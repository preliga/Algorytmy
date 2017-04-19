import java.util.Random;

public class Main {

	public static String dec_to_bin_rev(long x){
		String s ="";
		
		while(x != 0){
			if(x % 2 == 1) s += "1" ;
			else s += "0" ;
			x /= 2;
		}
		
		return s;
	}
	
	public static long potega_modulo(long b, long n, long m){
		String bin = dec_to_bin_rev(n);
		long  potega = 1;
		long [] tab = new long[bin.length()];
		
		tab[0] = b % m;
		if(bin.substring(0,1).compareTo("1") == 0) potega = (potega * tab[0]) % m;;
		
		for(int i = 1; i < bin.length();i++){

			tab[i] = (long) (Math.pow(tab[i-1], 2) % m); 

			if( bin.substring(i,i+1).compareTo("1") == 0){
				potega = (potega * tab[i]) % m;
			}
		}
		return potega;
	}
	
	public static long Symbol_Jacobbiego(long a, long n){
		
		if(n % 2 == 0)
			return -2;
		
		if( a == 0) return 0;
		if( a == 1) return 1;
		
		long x = 0, y = a;
		
		while(y % 2 == 0){
			y /= 2;
			x++;
		}
		
		long wynik = 1;
		
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
	
	public static boolean Kongruencja(long a, long b, long m){
		while(a < 0) a += m;
		while(b < 0) b += m;
		
		return a % m == b % m;
	}


	public static String TestSolovayaStrassena(long n){
		
		
		Random rand = new Random();
		long a;
		do{
			a = rand.nextInt(1000) + 2 ;
		}while(a % 2 == 0);
		
		
		
		long potega_a_mod_n = potega_modulo(a, (n-1)/2 , n);
		
		if(potega_a_mod_n < 0) return "-1";
		
		long symbol_jacobbiego = Symbol_Jacobbiego(a,n);
		
		// true pierwsza || false zlo¿ona
		return Kongruencja(potega_a_mod_n,symbol_jacobbiego,n )? "0" : "1" ;
	}
	
	public static void main(String[] args) {
		
		long a = Long.parseLong(args[0]);
		long iteracje = Long.parseLong(args[1]);
		// 0 - pierwsza
		// 1 - z³o¿ona
		// -1 - blad;
		
		long ile1 = 0;
		long ile0 = 0;
		
		for(long i = 0; i<iteracje ; i++){
			
			String s = TestSolovayaStrassena(a);
			if(s == "1") ile1++;
			else if(s == "0") ile0++;
			else if (s == "-1") {
				System.out.println("-1");
				return;
			}
		}
		if(ile1 > ile0)
			System.out.println("1");
		else if(ile1 < ile0)
			System.out.println("0");
		else
			System.out.println("-1");
	}

}
