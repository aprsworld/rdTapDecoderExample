package rdTapDecoderExample;

public class RecordDCSWCModuleVoltageCurrentCounter {
	public double voltageBusA;
	public double voltageShuntA;
	public double voltageBusB;
	public double voltageShuntB;
	public int counterLastSecondA;
	public int counterLastSecondB;
	public int counterA;
	public int counterB;
	public int counterSeconds;
	protected boolean valid;
	


	public RecordDCSWCModuleVoltageCurrentCounter() {
			valid=false;
	}

	public boolean isValid() {
		return valid;
	}

	public void parseRecord(int[] buff) {
//		for ( int i=0 ; i<buff.length ; i++ ) {
//			System.out.printf("# buff[%d]=0x%02x\n", i,buff[i]);
//		}
		
		if ( buff.length < 20 )
			return;
		
		voltageBusA = (( (buff[0]<<24) + (buff[1]<<16) + (buff[2]<<8) + buff[3] )>>4)  * 0.0001953125;

		int shuntA = (( (buff[4]<<24) + (buff[5]<<16) + (buff[6]<<8) + buff[7] )>>4);
		/* get two's complement from device into right sign on this computer */
		if ( shuntA >= 1048576 ) {
			shuntA = 0 - shuntA;
		}
		voltageShuntA = shuntA * 312.5 * 0.000000001;
		
		
		
		voltageBusB = (( (buff[8]<<24) + (buff[9]<<16) + (buff[10]<<8) + buff[11] )>>4)  * 0.0001953125;
		
		int shuntB = (( (buff[12]<<24) + (buff[13]<<16) + (buff[14]<<8) + buff[15] )>>4);
		/* get two's complement from device into right sign on this computer */
		if ( shuntB >= 1048576 ) {
			shuntB = 0 - shuntB;
		}
		voltageShuntA = shuntB * 312.5 * 0.000000001;
		
		
		counterLastSecondA = ( (buff[16]<<8) + buff[17] );

		counterLastSecondB = ( (buff[18]<<8) + buff[19] );
		
		if ( 20 == buff.length ) {
			valid=true;
			return;
		}
		
		counterA = ( (buff[20]<<24) + (buff[21]<<16) + (buff[22]<<8) + buff[23] );

		counterB = ( (buff[24]<<24) + (buff[25]<<16) + (buff[26]<<8) + buff[27] );
		
		counterSeconds = ( (buff[28]<<24) + (buff[29]<<16) + (buff[30]<<8) + buff[31] );
		
		
		valid=true;
	}	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("# DCSWC Module Voltage Current Counter:\n");
		sb.append(String.format("\t          Bus A Voltage: %.3f volts\n", voltageBusA));
		sb.append(String.format("\t        Shunt A Voltage: %.8f volts\n", voltageShuntA));

		sb.append(String.format("\t          Bus B Voltage: %.3f volts\n", voltageBusB));
		sb.append(String.format("\t        Shunt B Voltage: %.8f volts\n", voltageShuntB));
		
		sb.append(String.format("\tCounter A (last second): %d counts\n",counterLastSecondA));
		sb.append(String.format("\tCounter B (last second): %d counts\n",counterLastSecondB));
		
		sb.append(String.format("\t              Counter A: %d counts\n",counterA));
		sb.append(String.format("\t              Counter B: %d counts\n",counterB));
		
		sb.append(String.format("\t  Counter counting time: %d seconds\n",counterSeconds));
		
		return sb.toString();
	}
	
	public String toCSV() {
		return String.format("%.3f, %.8f, %.3f, %.8f, %d, %d, %d, %d, %d,",
				voltageBusA,
				voltageShuntA,
				voltageBusB,
				voltageShuntB,
				counterLastSecondA,
				counterLastSecondB,
				counterA,
				counterB,
				counterSeconds
				);
	}
}