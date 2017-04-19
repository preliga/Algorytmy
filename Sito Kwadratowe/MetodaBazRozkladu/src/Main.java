import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;


public class Main {
	
	// Test pierwszoœci metod¹ Solovaya-Strassena // 
	
	public static int Symbol_Jacobbiego(BigInteger a, BigInteger n){
		BigInteger B2 = new BigInteger("2");
		if(n.mod(B2).compareTo(BigInteger.ZERO) == 0)
			return -2;
		
		if( a.intValue() == 0) return 0;
		if( a.intValue() == 1) return 1;
		
		BigInteger x = BigInteger.ZERO, y = a;
		while(y.mod(B2).intValue() == 0){
			y = y.divide(B2);// /= 2;
			x = x.add(BigInteger.ONE);
		}
		
		int wynik = 1;
		int InMOD8 = n.mod(new BigInteger("8")).intValue();
		if(x.mod(B2).intValue() == 1 && (InMOD8 == 3 || InMOD8 == 5)) 
			wynik = -1;
		else
			wynik = 1;
		
		BigInteger B4 = new BigInteger("4");
		if(n.mod(B4).intValue() == 3 && y.mod(B4).intValue() == 3)
			wynik *=(-1);
		
		if(y.intValue() == 1)
			return wynik;
		else
			return wynik * Symbol_Jacobbiego(n.mod(y), y);
	}
	
	public static boolean Kongruencja(BigInteger a, BigInteger b, BigInteger m){
		while(a.compareTo(BigInteger.ZERO) == -1) a = a.add(m);// += m;
		while(b.compareTo(BigInteger.ZERO) == -1) b = b.add(m);// += m;
		
		return a.mod(m).compareTo(b.mod(m)) == 0;
	}


	public static String TestSolovayaStrassena(BigInteger n){
		
		
		Random rand = new Random();
		BigInteger a;
		BigInteger B2 = new BigInteger("2");
		do{
			a = new BigInteger( String.valueOf(rand.nextInt(1000) + 2)) ;
		}while( a.mod(B2).intValue() == 0);
		
		
		
		BigInteger potega_a_mod_n =  a.modPow( n.subtract(BigInteger.ONE).divide(B2) , n);//     potega_modulo(a, (n-1)/2 , n);
		
		if(potega_a_mod_n.compareTo(BigInteger.ZERO) == -1) return "-1";
		
		int symbol_jacobbiego = Symbol_Jacobbiego(a,n);
		
		// true pierwsza || false zlo¿ona
		return Kongruencja(potega_a_mod_n, new BigInteger(String.valueOf(symbol_jacobbiego) ),n )? "0" : "1" ;
	}
	
	
	// Faktoryzacja Metod¹ baz rozk³adu // 
	
	public static BigInteger sqrt(BigInteger n) {
		  BigInteger a = BigInteger.ONE;
		  BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
		  while(b.compareTo(a) >= 0) {
		    BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
		    if(mid.multiply(mid).compareTo(n) > 0) b = mid.subtract(BigInteger.ONE);
		    else a = mid.add(BigInteger.ONE);
		  }
		  return a.subtract(BigInteger.ONE);
		}
	
	public static BigInteger bez_potega_modulo(BigInteger b, BigInteger m){
		
		
		
		BigInteger potega = b.modPow(new BigInteger("2"), m);//  (b*b)% m ; 
		
		BigInteger minus_potega = potega.subtract(m);
		return minus_potega.abs().compareTo(potega) == -1 ? minus_potega : potega;
	}
	
	public static boolean rozklad_z_bazy(ArrayList<BigInteger> B, Row v, ArrayList<Row> V){
		BigInteger temp_kwadrat_modulo = v.kwadrat_modulo;
		if(temp_kwadrat_modulo.compareTo(BigInteger.ZERO) ==  -1){ 
			temp_kwadrat_modulo = BigInteger.ZERO.subtract(temp_kwadrat_modulo);
			v.v[0] = 1;
		}
		
		for(int i = 1; i < B.size() && temp_kwadrat_modulo.compareTo(BigInteger.ONE) != 0; i++){
			if(temp_kwadrat_modulo.mod(B.get(i)).compareTo(BigInteger.ZERO) == 0){
				v.v[i] ^=1;
				temp_kwadrat_modulo = temp_kwadrat_modulo.divide(B.get(i));
				i--;
			}
		}
		
		if(temp_kwadrat_modulo.compareTo(BigInteger.ONE) == 0){ 
			//v.wypiszV();
			V.add(v);
			return true;
		}
		return false;
	}
	
	public static boolean sprawdzRownania(int [][]A, int []wspolczynniki){
		
		for(int i=0;i<A.length;i++){
			int row = 0;
			for(int j=0;j<wspolczynniki.length;j++){
				row ^= A[i][j]*wspolczynniki[j];
			}
			if(row != 0) return false;
		}
		return true;
	}
	
	public static BigInteger NWD(BigInteger a, BigInteger b)
	{
		
		return a.gcd(b);
//		
//		if(a.compareTo(BigInteger.ZERO) == -1) a = BigInteger.ZERO.subtract(a);
//		while (b.compareTo(BigInteger.ZERO) != 0){
//			
//			BigInteger pom = a.mod(b);
//			a = b;
//			b = pom;
//			/*
//			BigInteger pom = b;
//			b = a.mod(b);
//			a = pom;
//			*/
//		}
//		return a;
	}
	
	public static BigInteger liczAiB (int []wspolczynniki, ArrayList<Row> V, BigInteger n ){
		BigInteger a = BigInteger.ONE;
		BigInteger b = BigInteger.ONE;
//		String s="Wsp: ";
		for(int i=0;i < wspolczynniki.length;i++){
//			s += wspolczynniki[i] + " ";
			if(wspolczynniki[i] == 1){
//				System.out.println("A " + a + "  a: " + V.get(i).a);
//				System.out.println("B " + b + "  b: " + V.get(i).kwadrat_modulo);
				a = a.multiply(V.get(i).a).mod(n);
				b = b.multiply(V.get(i).kwadrat_modulo).mod(n);
			}
		}
//		System.out.println(s);
//		System.out.println("A: " + a + " B: " + b);
		return sprawdzKongruencje(a, b, V , n);
	}
	
	public static BigInteger sprawdzKongruencje(BigInteger a, BigInteger b, ArrayList<Row> V, BigInteger n ){
		BigInteger b_1 = sqrt(b);
//		System.out.println( (a.multiply(a).mod(n).compareTo(b.mod(n))==0)  + "    " + (a.compareTo(b_1.mod(n)) != 0) );
		
		if(
			 a.multiply(a).mod(n).compareTo(b.mod(n))==0 
				&&
			a.compareTo(b_1.mod(n)) != 0 
		){
			BigInteger nwd = NWD(a.add(b_1), n);
			
//			System.out.println("NWD: " + nwd + " A: " + a.add(b_1) + " B: " + n);
			
			if(nwd == n || nwd.compareTo(BigInteger.ONE) == 0) 
				nwd = NWD(a.subtract(b_1), n);
			
			
//			System.out.println("NWD: " + nwd + " A: " + a.subtract(b_1) + " B: " + n);
//			System.out.println("\n\n\n");
			if(nwd == n || nwd.compareTo(BigInteger.ONE) == 0) 
				return BigInteger.ZERO;

			return nwd;
		}
		else	
			return BigInteger.ZERO;
		
	}
	
	public static BigInteger fastGauss(int [][]A, ArrayList<Row> V, BigInteger n){
		
//		for(int i = 0;i < A.length;i++){
//			String s = "";
//			for(int j = 0; j < A[i].length; j++){
//				s += A[i][j] + " ";
//			}
//			System.out.println(s);
//		}
		
		
		boolean uzyte_wektory[] = new boolean[A.length];
		
		for(int i = 0; i < A.length; i++) uzyte_wektory[i] = false;
		for(int i = 0; i < A[0].length; i++){
			
			int j = 0;
			while(j < A.length && (uzyte_wektory[j] || A[j][i] != 1)) j++;
			
			if(j < A.length){
				uzyte_wektory[j] = true;
				
				for(int k = 0;k < A.length;k++)  	//wiersz
					for(int l = 0;l < A[0].length;l++)	//kolumna
						if(l != i && A[j][l] == 1) A[k][l] ^= A[k][i];
			}
		}
		
		
//		for(int i = 0;i < A.length;i++){
//			String s = i + ")  ";
//			if(i < 10) s +=" ";
//			for(int j = 0; j < A[i].length; j++){
//				s += A[i][j] + " ";
//			}
//			System.out.println(s);
//		}
		
		
//		System.out.println("UW: " + uzyte_wektory.length);
		
		for(int i = 0;i<uzyte_wektory.length;i++){
			if(!uzyte_wektory[i]){
				
				int wspolczynniki[] = new int[uzyte_wektory.length];
//				System.out.println("I: " + i);
				wspolczynniki[i] = 1;
		
				for(int j = 0;j< A[i].length;j++){
					if(A[i][j] == 1){
//						System.out.println( i + ") " + j);
						int k = 0;
						
						// nie braæ pod uwagê wektorów z wieloma jednynkami
						
						while(k < A.length && k != i && (A[k][j] != 1 || !uzyte_wektory[k])) k++;
						if(k < A.length) wspolczynniki[k] = 1; 
					}
				}
				
				BigInteger wynik = liczAiB(wspolczynniki,V,n);
				if(wynik.compareTo(BigInteger.ZERO) != 0) 
					return wynik;
			}
		}
		return new BigInteger("-1");
	}
	
    public static BigDecimal intPower(BigDecimal x, long exponent, int scale)
    {
        if (exponent < 0)
            return BigDecimal.valueOf(1)
                        .divide(intPower(x, -exponent, scale), scale,
                                BigDecimal.ROUND_HALF_EVEN);

        BigDecimal power = BigDecimal.valueOf(1);

        while (exponent > 0) {

            if ((exponent & 1) == 1)
                power = power.multiply(x)
                          .setScale(scale, BigDecimal.ROUND_HALF_EVEN);

            x = x.multiply(x)
                    .setScale(scale, BigDecimal.ROUND_HALF_EVEN);
            exponent >>= 1;
            Thread.yield();
        }
        return power;
    }

    public static BigDecimal intRoot(BigDecimal x, long index, int scale)
    {
        if (x.signum() < 0)
            throw new IllegalArgumentException("x < 0");

        int sp1 = scale + 1;
        BigDecimal n = x;
        BigDecimal i = BigDecimal.valueOf(index);
        BigDecimal im1 = BigDecimal.valueOf(index-1);
        BigDecimal tolerance = BigDecimal.valueOf(5)
                                            .movePointLeft(sp1);
        BigDecimal xPrev;

        x = x.divide(i, scale, BigDecimal.ROUND_HALF_EVEN);

        do {
            BigDecimal xToIm1 = intPower(x, index-1, sp1);
            BigDecimal xToI =
                    x.multiply(xToIm1)
                        .setScale(sp1, BigDecimal.ROUND_HALF_EVEN);

            BigDecimal numerator =
                    n.add(im1.multiply(xToI))
                        .setScale(sp1, BigDecimal.ROUND_HALF_EVEN);

            BigDecimal denominator =
                    i.multiply(xToIm1)
                        .setScale(sp1, BigDecimal.ROUND_HALF_EVEN);
            xPrev = x;
            x = numerator
                    .divide(denominator, sp1, BigDecimal.ROUND_DOWN);

            Thread.yield();
        } while (x.subtract(xPrev).abs().compareTo(tolerance) > 0);

        return x;
    }

    public static BigDecimal exp(BigDecimal x, int scale)
    {
        if (x.signum() == 0)
            return BigDecimal.valueOf(1);

        else if (x.signum() == -1) {
            return BigDecimal.valueOf(1)
                        .divide(exp(x.negate(), scale), scale,
                                BigDecimal.ROUND_HALF_EVEN);
        }

        BigDecimal xWhole = x.setScale(0, BigDecimal.ROUND_DOWN);

        if (xWhole.signum() == 0) return expTaylor(x, scale);

        BigDecimal xFraction = x.subtract(xWhole);
        BigDecimal z = BigDecimal.valueOf(1)
                            .add(xFraction.divide(
                                    xWhole, scale,
                                    BigDecimal.ROUND_HALF_EVEN));

        BigDecimal t = expTaylor(z, scale);

        BigDecimal maxLong = BigDecimal.valueOf(Long.MAX_VALUE);
        BigDecimal result  = BigDecimal.valueOf(1);

        while (xWhole.compareTo(maxLong) >= 0) {
            result = result.multiply(
                                intPower(t, Long.MAX_VALUE, scale))
                        .setScale(scale, BigDecimal.ROUND_HALF_EVEN);
            xWhole = xWhole.subtract(maxLong);

            Thread.yield();
        }
        return result.multiply(intPower(t, xWhole.longValue(), scale))
                        .setScale(scale, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal expTaylor(BigDecimal x, int scale)
    {
        BigDecimal factorial = BigDecimal.valueOf(1);
        BigDecimal xPower    = x;
        BigDecimal sumPrev;

        BigDecimal sum  = x.add(BigDecimal.valueOf(1));

        int i = 2;
        do {
            xPower = xPower.multiply(x)
                        .setScale(scale, BigDecimal.ROUND_HALF_EVEN);

            factorial = factorial.multiply(BigDecimal.valueOf(i));

            BigDecimal term = xPower
                                .divide(factorial, scale,
                                        BigDecimal.ROUND_HALF_EVEN);
            sumPrev = sum;
            sum = sum.add(term);

            ++i;
            Thread.yield();
        } while (sum.compareTo(sumPrev) != 0);

        return sum;
    }

    public static BigDecimal ln(BigDecimal x, int scale)
    {
        if (x.signum() <= 0) 
            throw new IllegalArgumentException("x <= 0");
        
        int magnitude = x.toString().length() - x.scale() - 1;

        if (magnitude < 3)
            return lnNewton(x, scale);

        else {
            BigDecimal root = intRoot(x, magnitude, scale);
            BigDecimal lnRoot = lnNewton(root, scale);

            return BigDecimal.valueOf(magnitude).multiply(lnRoot)
                        .setScale(scale, BigDecimal.ROUND_HALF_EVEN);
        }
    }

    public static BigDecimal lnNewton(BigDecimal x, int scale)
    {
        int sp1 = scale + 1;
        BigDecimal n   = x;
        BigDecimal term;

        BigDecimal tolerance = BigDecimal.valueOf(5)
                                            .movePointLeft(sp1);
        do {
            BigDecimal eToX = exp(x, sp1);

            term = eToX.subtract(n).divide(eToX, sp1, BigDecimal.ROUND_DOWN);
            x = x.subtract(term);

            Thread.yield();
        } while (term.compareTo(tolerance) > 0);

        return x.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal sqrt(BigDecimal x, int scale)
    {
        if (x.signum() < 0) 
            throw new IllegalArgumentException("x < 0");

        BigInteger n = x.movePointRight(scale << 1).toBigInteger();

        int bits = (n.bitLength() + 1) >> 1;
        BigInteger ix = n.shiftRight(bits);
        BigInteger ixPrev;

        do {
            ixPrev = ix;
            ix = ix.add(n.divide(ix)).shiftRight(1);

            Thread.yield();
        } while (ix.compareTo(ixPrev) != 0);

        return new BigDecimal(ix, scale);
    }
    
	
	
	public static ArrayList<BigInteger> generujBaze(BigInteger n){

		int scale = 10;
		BigDecimal lnn =  ln(new BigDecimal(n), scale);
		BigDecimal lnlnn = ln(lnn, scale);
		BigDecimal pierw = sqrt(lnn.multiply(lnlnn),scale);
		BigDecimal wykladnik = pierw.multiply(  sqrt(new BigDecimal(2), scale)).divide(new BigDecimal(4));
		BigDecimal b = exp(wykladnik, scale);
		 
//		if(n.compareTo(new BigInteger("1000000")) == -1) 
			b = b.multiply(new BigDecimal(10));
		
		b = b.add(BigDecimal.ONE);
		
		ArrayList<BigInteger> temp_B = new ArrayList<BigInteger>();
		ArrayList<BigInteger> B = new ArrayList<BigInteger>();
		temp_B.add(new BigInteger("-1"));
		temp_B.add(new BigInteger("2"));

		B.add(temp_B.get(0));
		B.add(temp_B.get(1));
		
		for(BigInteger i = new BigInteger("3"); i.compareTo(b.toBigInteger()) == -1; i = i.add(BigInteger.ONE)   ){
			boolean f = true;
			
			for(int j = 1; j < temp_B.size() && f; j++){
				f = i.mod(temp_B.get(j)).compareTo(BigInteger.ZERO) != 0;
			}
			
			if(f){
				temp_B.add(i);
				if( Symbol_Jacobbiego(n, i) == 1)
					B.add(i);
			}
		}
		return B;
		
	}
	
	public static BigInteger FaktoryzacjaMetodaBazRozkladu(BigInteger n){
		
		BigInteger B2 = new BigInteger("2");

		if(n.intValue() == 2) return n;
		if(n.mod(B2).compareTo(BigInteger.ZERO) == 0 ) return B2;
		if( TestSolovayaStrassena(n)== "0" ) return n;
		
		ArrayList<BigInteger> B = generujBaze(n);
//		System.out.println("Rozmiar bazy: " + B.size());
		ArrayList<Row> V = new ArrayList<Row>();
		
		int l = 1;
		BigInteger dzielnik;
		BigInteger a1 = sqrt(n);
		BigInteger a2 = a1;
		
		boolean pierwsze_zero = false;
		do{
//			System.out.println("L: " + l);
			while( V.size() < (B.size() + 1) * l){
				boolean f = false;
				
				if(a1.compareTo(n) != 0)
					 f = rozklad_z_bazy(B,new Row(a1,bez_potega_modulo(a1, n),B.size()),V);
				else return n;
				
				if(f && V.get(V.size() - 1).czyZerowy()){
					BigInteger wynik = sprawdzKongruencje(V.get(V.size() - 1).a, V.get(V.size() - 1).kwadrat_modulo,V,n);
					if(wynik.compareTo(BigInteger.ZERO) != 0) 
						return wynik;
				}
								
				if(pierwsze_zero){
					if(a2.compareTo(BigInteger.ONE) != 0)
						f = rozklad_z_bazy(B,new Row(a2,bez_potega_modulo(a2, n),B.size()),V);
					else return n;
							
					if(f && V.get(V.size() - 1).czyZerowy()){
						BigInteger wynik = sprawdzKongruencje(V.get(V.size() - 1).a, V.get(V.size() - 1).kwadrat_modulo,V,n);
						if(wynik.compareTo(BigInteger.ZERO) != 0) 
							return wynik;
					}
				} else pierwsze_zero = true;
				
				a1 = a1.add(BigInteger.ONE);
				a2 = a2.subtract(BigInteger.ONE);
			}
			
			
			int[][] A = new int[V.size()][B.size()];
			for(int i = 0;i<V.size(); i ++)
				for(int j=0;j<B.size();j++)
					A[i][j] = V.get(i).v[j];
				
			dzielnik = fastGauss(A, V, n);
			
			l++;
			//break;
		}while( dzielnik.compareTo(new BigInteger("-1")) == 0 );
		
		return  dzielnik;

	}
	
	
	
	public static void main(String[] args) {
		
		
		if(args.length < 1){
			System.out.println("Zle parametry");
			return;
		}
		
		BigInteger a = new BigInteger(args[0]);
		
		BigInteger wynik = FaktoryzacjaMetodaBazRozkladu(a);
		
		System.out.println(wynik);
		
		
	}

}
