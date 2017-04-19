import java.math.BigInteger;


public class Row {

		public BigInteger a;
		public BigInteger kwadrat_modulo;
		public int []v;
		
		public Row(BigInteger a,BigInteger kwadrat_modulo, int size){
			this.a = a;
			this.kwadrat_modulo = kwadrat_modulo;
			this.v = new int[size];
		}
		
		
		public void wypiszV(){
			String s ="";
			for(int j = 0; j < this.v.length; j++){
				s += this.v[j] + " ";
			}
			System.out.println( "V: " +  s);
		}
		
		public boolean czyZerowy(){
			boolean f = true;
			
			for(int i=0;i<this.v.length && f;i++)
				f = v[i] == 0;
			
			return f;
		}
	}