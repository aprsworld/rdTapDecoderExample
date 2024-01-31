# rdTapDecoderExample

## Example ##
run:
```
cd bin
java rdTapDecoderExample/rdTapDecoderExample 000000060292000002150120001f43800089e8d0001b4380007618300000000000000000000000000000001e022000000080000000f000000080000000900000000000000000000000000000001e
```

outputs:
```

# 78 bytes loaded
# rdTap sequence number: 0
# rdTap device number: 0 (6 bytes)
# DCSWC Module Latching Contactor 2x:
	Input Voltage: 25.7
	Contactor A: 0x00
	Contactor B: 0x00
	Temperature: 23.1 deg C

# rdTap device number: 1 (32 bytes)
# DCSWC Module Voltage Current Counter:
	          Bus A Voltage: 25.011 volts
	        Shunt A Voltage: -0.15115563 volts
	          Bus B Voltage: 21.811 volts
	        Shunt B Voltage: 0.15116094 volts
	Counter A (last second): 0 counts
	Counter B (last second): 0 counts
	              Counter A: 0 counts
	              Counter B: 0 counts
	  Counter counting time: 30 seconds

# rdTap device number: 2 (32 bytes)
# DCSWC Module Voltage Current Counter:
	          Bus A Voltage: 0.002 volts
	        Shunt A Voltage: 0.00000469 volts
	          Bus B Voltage: 0.002 volts
	        Shunt B Voltage: 0.00000281 volts
	Counter A (last second): 0 counts
	Counter B (last second): 0 counts
	              Counter A: 0 counts
	              Counter B: 0 counts
	  Counter counting time: 30 seconds
```


