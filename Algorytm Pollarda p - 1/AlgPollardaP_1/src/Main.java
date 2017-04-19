import java.util.Random;


public class Main {
	
	public static long NWD(long a, long b)
	{
		long pom;

		while (b != 0)
		{
			pom = b;
			b = a%b;
			a = pom;
		}
		return a;
	}
	
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
	
	public static long AlgPollardaP(long n){
		if(n % 2 == 0) return 2;
		if(n < 4) return -1;
		long a = ( new Random()).nextInt((int) n  - 3) + 2;
		long r = 2;
		long silnia_r = 2;
		long gcd;
		do{
			long x = potega_modulo(a, silnia_r, n);
			silnia_r *= r++ + 1;
			gcd = NWD(x - 1, n); 
		}while(gcd ==  1);
		return gcd == n? -1 : gcd;

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		for(int i = 0; i < 100; i++)
		System.out.println("  " +  AlgPollardaP(2094967291));
		
	}

}
