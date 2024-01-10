package rdTapDecoderExample;

import java.util.Arrays;

public class rdTapDecoder {
	public int sbdByte[];
	public int rdTapSequenceNumber;


	public void decode(int mode, String sbdHexData) {
//		System.err.println("# starting rdTapDecoderInstance");
//		System.err.println("# raw hex data: ");
//		System.err.println(sbdHexData);


		/* load hex string into an integer array */
		sbdByte = new int[sbdHexData.length() / 2];
		for ( int i=0 ; i<sbdHexData.length()/2; i++ ) {
			//			System.err.printf("# sbdByte[%d]=0x%s\n",i,sbdHexData.substring(i*2,i*2+2));
			sbdByte[i]=Integer.parseInt(sbdHexData.substring(i*2,i*2+2),16) & 0xff;
//			System.err.printf("# sbdByte[%d]=0x%s=%d\n",i,sbdHexData.substring(i*2,i*2+2),sbdByte[i]);
		}

		if ( 0 == mode ) {
			System.err.printf("# %d bytes loaded\n",sbdByte.length);
		} else if ( 1 == mode ) {
			System.out.printf("%d, ",sbdByte.length);
		}

		/* SBD packet has a sequence number */
		rdTapSequenceNumber = (sbdByte[0]<< 8) + sbdByte[1];
		
		if ( 0 == mode ) {
			System.out.printf("# rdTap sequence number: %d\n",rdTapSequenceNumber);
		} else if ( 1 == mode ) {
			System.out.printf("%d, ",rdTapSequenceNumber);
		}
		
		/* and then 0 or more sub packets */

		int startByte=2;
		while ( startByte < sbdByte.length ) {
			int rdTapDeviceNumber=sbdByte[startByte];
			int rdTapDeviceDataLength=sbdByte[startByte+1];

			if ( 0 == mode ) {
				System.out.printf("# rdTap device number: %d (%d bytes)\n",rdTapDeviceNumber,rdTapDeviceDataLength);
			} else if ( 1 == mode ) {
				System.out.printf("%d, ",rdTapDeviceNumber);
			}
			
			if ( 0 == rdTapDeviceNumber ) {
//				System.out.println("# decoding DCSWC Module Latching Contactor");
				RecordDCSWCModuleLatchingContactor2x rLatchingContactor = new RecordDCSWCModuleLatchingContactor2x();
				rLatchingContactor.parseRecord(Arrays.copyOfRange(sbdByte, startByte+2, startByte+2+rdTapDeviceDataLength));

				if ( 0 == mode ) {
					System.out.println(rLatchingContactor.toString());
				} else if ( 1 == mode ) {
					System.out.print(rLatchingContactor.toCSV());
				}
			} else {
				/* assume anything else is a voltage current counter */
//				System.out.println("# decoding DCSWC Module Voltage Current Counter");
				RecordDCSWCModuleVoltageCurrentCounter rVoltageCurrentCounter = new RecordDCSWCModuleVoltageCurrentCounter();
				rVoltageCurrentCounter.parseRecord(Arrays.copyOfRange(sbdByte, startByte+2, startByte+2+rdTapDeviceDataLength));

				if ( 0 == mode ) {
					System.out.println(rVoltageCurrentCounter.toString());
				} else if ( 1 == mode ) {
					System.out.print(rVoltageCurrentCounter.toCSV());
				}
			}


			startByte += sbdByte[startByte+1]+2;
//			System.out.printf("# startByte for next is %d\n",startByte);		
		}

	//	System.out.println("");

	}

}
