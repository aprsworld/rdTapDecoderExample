package rdTapDecoderExample;

public class Thermistor {

	   public static double ntcThermistor(double voltage, double beta, double beta25, double rSource, double vSource) {
	      double rt=(voltage*rSource)/(vSource-voltage);

	      double tKelvin=1.0/( (1.0/beta) * Math.log(rt/beta25) + 1.0/298.15);

	      return tKelvin-273.15;
	   }

}
