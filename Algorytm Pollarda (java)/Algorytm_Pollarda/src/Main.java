
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

	public static long AlgPollarda_rho(long N, long n){
		
		long a = 2, d = 0;
		
		if(N % 2 == 0) return 2;
		
		for (int j = 2; j < n; j++){

			a = potega_modulo(a, j, N);
			d = NWD(a - 1, N);
			
			if (1 < d && d < N)  
				return d;
			
		}
		return -1;
	}
	public static void main(String[] args) {

		long n = 50;
		//for(int i = 3 ; i < 100; i ++ ){
		long N = 2147483638;
		
		long x = AlgPollarda_rho(N, n);
		if (x > 0) System.out.println( N + " d: " + x);
		else System.out.println(N + " nie udalo sie");
	//	}
	}

}
