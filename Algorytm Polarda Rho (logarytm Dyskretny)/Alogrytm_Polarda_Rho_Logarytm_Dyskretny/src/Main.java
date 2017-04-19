import java.math.BigInteger;


public class Main {

	
	public static BigInteger x = BigInteger.ONE;
	public static BigInteger y = BigInteger.ZERO;
	
	
	public static BigInteger NWD(BigInteger a, BigInteger b)
	{
		BigInteger pom;
		if(a.compareTo(BigInteger.ZERO) == -1) a = BigInteger.ZERO.subtract(a);
		while (b.compareTo(BigInteger.ZERO) != 0){
			pom = b;
			b = a.mod(b);
			a = pom;
		}
		return a;
	}
	
	public static void euklides(BigInteger a, BigInteger b)
	{
	  if(b.compareTo(BigInteger.ZERO) != 0)
	  {
	    euklides(b, a.mod(b));
	    BigInteger pom = y;
	    y = x.subtract(a.divide(b).multiply(y));//  /b * y;
	    x = pom;
	  }
	}
	
	public static void element_odwrotn_mod(BigInteger a, BigInteger p){
		
		euklides(a,p);
		x = x.mod(p);
		y = y.mod(p);
	}
	
	public static BigInteger Logarytm_Dyskretny(BigInteger p, BigInteger g, BigInteger n, BigInteger y){
		

		Row a = new Row();
		Row b = new Row();
		
		do{
			a.f(p, g, n, y);
			b.f(p, g, n, y);
			b.f(p, g, n, y);
		}while(a.x.compareTo(b.x) != 0);
		
		
		if(a.alfa.compareTo(b.alfa) == 0 
				|| 
		   a.beta.compareTo(b.beta) == 0)
				return new BigInteger("-1");
		

			
		BigInteger P = a.alfa.subtract(b.alfa).mod(n);
		BigInteger L = b.beta.subtract(a.beta).mod(n);
	
		if(NWD(L,P).compareTo(BigInteger.ONE) != 0){
			BigInteger c = P.divide(L);
			BigInteger log = g.modPow(c, p);
			BigInteger d = n.divide(L);
			BigInteger log_temp = log;
			while(log.compareTo(y) != 0 ){
				c = c.add(d);
				log = g.modPow(c, p);
				if(log.compareTo(log_temp) == 0) return new BigInteger("-1");
			}
			return c;
		}else{
			element_odwrotn_mod(L,n);
			L = x;
			return P.multiply(L).mod(n);
		}
			
	}
	
	public static void main(String[] args) {
		
		System.out.println(Logarytm_Dyskretny(
				new BigInteger(args[0]), // p 
				new BigInteger(args[1]), // g
				new BigInteger(args[2]), // n
				new BigInteger(args[3])  // y
						));
	
	}

}
