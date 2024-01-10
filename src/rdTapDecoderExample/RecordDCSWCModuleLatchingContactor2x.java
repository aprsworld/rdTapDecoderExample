package rdTapDecoderExample;

public class RecordDCSWCModuleLatchingContactor2x {
	public double voltageInputAverage;
	public int stateContactorA, stateContactorB;
	public double temperatureBoard;
	protected boolean valid;
	
	public final int CH_STATE_BIT_OVERRIDE = 0;
	public final int CH_STATE_BIT_CMD_ON   = 1;
	public final int CH_STATE_BIT_CMD_OFF  = 2;
	public final int CH_STATE_BIT_LVD      = 3;
	public final int CH_STATE_BIT_HVD      = 4;
	public final int CH_STATE_BIT_LTD      = 5;
	public final int CH_STATE_BIT_HTD      = 6;
	public final int CH_STATE_BIT_FUTURE   = 7;
	

	public boolean isState(int channel, int bitToTest) {
		if ( 0 == channel ) {
			return ( stateContactorA & bitToTest ) > 0;
		} else {
			return ( stateContactorB & bitToTest ) > 0;
		}
	}

	public RecordDCSWCModuleLatchingContactor2x() {
			valid=false;
	}

	public boolean isValid() {
		return valid;
	}

	public void parseRecord(int[] buff) {
//		for ( int i=0 ; i<buff.length ; i++ ) {
//			System.out.printf("# buff[%d]=0x%02x\n", i,buff[i]);
//		}
		
		if ( buff.length < 6 )
			return;
		
		
		voltageInputAverage = ( (buff[0]<<8) + buff[1] ) * (40.0/1024.0);
		stateContactorA=buff[2];
		stateContactorB=buff[3];
		temperatureBoard = Thermistor.ntcThermistor( ((buff[4]<<8) + buff[5]) * (5.0 / 1024.0) , 3900, 10000, 10000, 5.0);

		
		valid=true;
	}	
	
	public String toString() {
		return String.format("# DCSWC Module Latching Contactor 2x:\n\tInput Voltage: %.1f\n\tContactor A: 0x%02x\n\tContactor B: 0x%02x\n\tTemperature: %.1f deg C\n",
				voltageInputAverage,
				stateContactorA,
				stateContactorB,
				temperatureBoard
				);
	}
	
	public String toCSV() {
		return String.format("%.1f, %d, %d, %.1f,", 
				voltageInputAverage,
				stateContactorA,
				stateContactorB,
				temperatureBoard
				);		
	
	}
}