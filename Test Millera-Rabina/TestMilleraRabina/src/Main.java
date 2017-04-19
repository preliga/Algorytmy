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
		if(n == 0) return 1;
		if(n == 1) return b;
		
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
	
	public static boolean Kongruencja(long a, long b, long m){
		while(a < 0) a += m;
		while(b < 0) b += m;
		
		return a % m == b % m;
	}
	
	public static long[] licz_d(long n){
		long []tab = new long[2];
		long i = 0;
		while((n - 1) % 2 == 0){
			n = (n - 1) / 2;
			i++;
		}
		tab[0] = n;
		tab[1] = i;
			
		return tab;
	}
	
	public static long szybkie_potegowanie(long x, long n){
		if(n == 0) return 1;
		else if(n % 2 != 0){
			return x * szybkie_potegowanie(x, n - 1);
		}else{
			long a = szybkie_potegowanie(x, n/2);
			return a * a;
		}
	}
	
	public static boolean TestMilleraRabina(long n){
		if(n % 2 == 0) return false;
		
		Random rand = new Random();
		
		long d = n - 1;
		long s = 0;
		while(d % 2 == 0){
			d /= 2;
			s++;
		}
		
		boolean f = true;
		long a = rand.nextInt((int)n - 2)  + 1;
		f = true;
		if(	!Kongruencja( potega_modulo(a, d, n) , 1, n) ){
			f = true;
			for(long r = 1; r < s && f; r++ )
				f = !Kongruencja(potega_modulo (a, szybkie_potegowanie(2,r)*d, n), -1, n);
			if(f){
				// zlozona
//				System.out.println("Zlo¿ona");
				return false;
			}else{
				// pierwsza
//				System.out.println("Pierwsza");
				return true;
			}
		}else{
			//pierwsza
//			System.out.println("Pierwsza");
			return true;
		}
		// true - pierwsza 
		// false - zlozona
	}
	
	public static void main(String[] args) {

		for(int i = 2; i < 20; i++){			
			System.out.println(TestMilleraRabina(25) + "  " + 25);
		}
		
		
	}

}
