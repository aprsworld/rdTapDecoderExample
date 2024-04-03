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
	public int[] bRaw;
	


	public RecordDCSWCModuleVoltageCurrentCounter() {
			valid=false;
			bRaw = new int[32];
	}

	public boolean isValid() {
		return valid;
	}

	public void parseRecord(int[] buff) {
		for ( int i=0 ; i<buff.length && i<bRaw.length ; i++ ) {
		//	System.out.printf("# RecordDCSWCModuleVoltageCurrentCounter buff[%d]=0x%02x\n", i,buff[i]);
			bRaw[i]=buff[i];
		}
		
		if ( buff.length < 20 )
			return;
		
		voltageBusA = (( (buff[0]<<24) + (buff[1]<<16) + (buff[2]<<8) + buff[3] )>>4)  * 0.0001953125;

		int shuntA = (( (buff[4]<<24) + (buff[5]<<16) + (buff[6]<<8) + buff[7] )>>4);
		
		// shuntA = 566251; /* test value of -0.15072625 volts */ 

		
		/* get two's complement from device into right sign on this computer */
		if ( shuntA >= 524287 ) {
			shuntA = 0 - (~shuntA & 0x7ffff);
		}
		voltageShuntA = shuntA * 312.5 * 0.000000001;
		
		
		
		voltageBusB = (( (buff[8]<<24) + (buff[9]<<16) + (buff[10]<<8) + buff[11] )>>4)  * 0.0001953125;
		
		int shuntB = (( (buff[12]<<24) + (buff[13]<<16) + (buff[14]<<8) + buff[15] )>>4);

		// shuntB = 482557; /* test value of 0.15079906 volts */
		
		/* get two's complement from device into right sign on this computer */
		if ( shuntB >= 524287 ) {
			shuntB = 0 - (~shuntB & 0x7ffff);
		}
		voltageShuntB = shuntB * 312.5 * 0.000000001;
		
		
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
		sb.append(String.format("\t          Bus A Voltage: %.3f volts (0x %02x %02x %02x %02x)\n", voltageBusA,
				bRaw[0],bRaw[1],bRaw[2],bRaw[3]));
		sb.append(String.format("\t        Shunt A Voltage: %.8f volts (0x %02x %02x %02x %02x)\n", voltageShuntA,
				bRaw[4],bRaw[5],bRaw[6],bRaw[7]));				
		
		sb.append(String.format("\t          Bus B Voltage: %.3f volts (0x %02x %02x %02x %02x)\n", voltageBusB,
				bRaw[8],bRaw[9],bRaw[10],bRaw[11]));	
		sb.append(String.format("\t        Shunt B Voltage: %.8f volts (0x %02x %02x %02x %02x)\n", voltageShuntB,
				bRaw[12],bRaw[13],bRaw[14],bRaw[15]));	
		
		sb.append(String.format("\tCounter A (last second): %d counts (0x %02x %02x)\n",counterLastSecondA,
				bRaw[16],bRaw[17]));	
		sb.append(String.format("\tCounter B (last second): %d counts (0x %02x %02x)\n",counterLastSecondB,
				bRaw[18],bRaw[19]));
		
		sb.append(String.format("\t              Counter A: %d counts (0x %02x %02x %02x %02x)\n",counterA,
				bRaw[20],bRaw[21],bRaw[22],bRaw[23]));	
		sb.append(String.format("\t              Counter B: %d counts (0x %02x %02x %02x %02x)\n",counterB,
				bRaw[24],bRaw[25],bRaw[26],bRaw[27]));	;
		
		sb.append(String.format("\t  Counter counting time: %d seconds (0x %02x %02x %02x %02x)\n",counterSeconds,
				bRaw[28],bRaw[29],bRaw[30],bRaw[31]));	
		
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