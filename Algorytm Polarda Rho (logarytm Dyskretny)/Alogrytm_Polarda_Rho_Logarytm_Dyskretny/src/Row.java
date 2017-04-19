import java.math.BigInteger;

public class Row{
		public BigInteger x;
		public BigInteger alfa;
		public BigInteger beta;

		
		public Row(){
			this.x=BigInteger.ONE;
			this.alfa = this.beta = BigInteger.ZERO;
		}
		
		public void f(BigInteger p,BigInteger g, BigInteger n, BigInteger y){
			int h = this.h();
			if(h == 1){
				this.x = this.x.multiply(g).mod(p);
				this.alfa = this.alfa.add(BigInteger.ONE).mod(n);
			}else if(h == 2){
				this.x = this.x.multiply(y).mod(p);
				this.beta = this.beta.add(BigInteger.ONE).mod(n);
			}else if(h == 0){
				
				BigInteger b2 = new BigInteger("2");
				
				this.x = this.x.multiply(x).mod(p);
				this.alfa = this.alfa.multiply(b2).mod(n);
				this.beta = this.beta.multiply(b2).mod(n);
			}
		}
		
		public int h(){
			return this.x.mod(new BigInteger("3")).intValue();
		}
		
		public String toString(){
			return "[ " + this.x + " , " + this.alfa + " , " + this.beta + " ]";
		}
	}