public final class Res {
	
	public static final String ASK_QUESTION = "Start procedure?";
	
	public static final String MENU_TITLE = "Select Procedure";
	
	public static final String[] MENU_ITEMS = 
		{
			"Mix tank procedure",
			"Procedure 2",
			"Procedure 3"
		};
	
	public static final String[] CLOSE_HEADERS = { "COMPONENT ID", "POSITION", "CLOSED" };
	public static final String[] OPEN_HEADERS = { "COMPONENT ID", "POSITION", "OPEN" };
	
	public static Object[][] CLOSE_DATA = { 
			{"MIXXV100UI", "Closed", Boolean.FALSE},
			{"MIXXV101UI", "Closed", Boolean.FALSE},
			{"MIXXV102UI", "Closed", Boolean.FALSE},
			{"MIXXV105UI", "Closed", Boolean.FALSE},
			{"MIXXV106UI", "Closed", Boolean.FALSE},
			{"MIXXV108UI", "Closed", Boolean.FALSE},
	};
	
	public static Object[][] OPEN_DATA = { 
			{"MIXXV103UI", "Open", Boolean.FALSE},
			{"MIXXV104UI", "Open", Boolean.FALSE},
			{"MIXXV107UI", "Open", Boolean.FALSE},			
	};		
	
	public static final int MIX_STRING = 0;
	public static final int MIX_NOTE = 1;
	public static final int MIX_TABLE = 2;
	public static final int MIX_CHECKBOX = 3;
	public static final int MIX_TEXTINPUT = 4;
	public static final int MIX_RADIO = 5;
	public static final int MIX_EMPTY = 6;
	public static final int MIX_DATA = 7;

	
	public static final Object[] GOOD_DATA = {
			new MixData("9", "<html>NOTE:<br>"
					+ "These steps will prepare and UNLOAD the Mix Tank to a truck", MIX_STRING, 0),
			new MixData("0", "empty", MIX_EMPTY, 0),
		new MixData("1", "<html> 5.2.7.1 INSPECT all truck hoses for leaks and wet spots", MIX_CHECKBOX, 410),
		new MixData("2", "<html> 5.2.7.2 SECURE the tank vehicle with wheel chocks and interlocks", MIX_CHECKBOX, 411),
		new MixData("3", "<html> 5.2.7.3 VERIFY the vehicle's parking brakes are set", MIX_CHECKBOX, 412),
		new MixData("4", "<html> 5.2.7.4 ESTABLISH adequate bonding/grounding prior to connecting to the<br/>"
				+ "fuel transfer point", MIX_CHECKBOX, 413),
		new MixData("5", "<html> 5.2.7.5 ENSURE cell phones are turned OFF", MIX_CHECKBOX, 414),
		
		new MixData("0", "empty", MIX_EMPTY, 0),
		new MixData("0", "<html>CAUTION:<br>"
				+ "Driver must stay with the vehicle at all times during<br>"
				+ "loading/unloading activities.", MIX_STRING, 0),
		new MixData("0", "empty", MIX_EMPTY, 0),
		new MixData("0", "<html>CAUTION:<br>"
				+ "Facility Manager or designee should observe the delivery driver<b>"
				+ "during loading/unloading.", MIX_STRING, 0),
		new MixData("0", "empty", MIX_EMPTY, 0),
		new MixData("0", "<html>CAUTION:<br>"
				+ "Periodically inspect all systems, hoses, and connections.", MIX_STRING, 0),
		new MixData("0", "empty", MIX_EMPTY, 0),
		
		new MixData("6", "<html> 5.2.7.6 ENSURE agitator MIXM100U1 is STOPPED", MIX_CHECKBOX, 415),
		new MixData("7", "<html> 5.2.7.7 ENSURE MIXXV106U1 is OPEN", MIX_CHECKBOX, 416),
		new MixData("8", "<html> 5.2.7.8 ENSURE MIXXV107U1 is CLOSED", MIX_CHECKBOX, 417),
	};
	
	public static final Object[] FALSE_DATA = {
			
			new MixData("0", "<html>NOTE:<br>"
			+ "These steps will DRAIN the Mix Tank due to a bad batch.", MIX_STRING, 0),
			new MixData("0", "empty", MIX_EMPTY, 0),
			
		new MixData("9", "<html> 5.2.7.9 ENSURE agitator MIXM100U1 is STOPPED", MIX_CHECKBOX, 418),
		new MixData("10", "<html> 5.2.7.10 ENSURE MIXXV103U1 is CLOSED", MIX_CHECKBOX, 419),
		new MixData("11", "<html> 5.2.7.11 ENSURE Mix Tank Drain valve MIXXV105U1 is OPEN", MIX_CHECKBOX, 420),
	};
	
	public static final Object[] TREUCK_TRUE = {
		new MixData("1", "<html>5.3.1.1 ENSURE MIXXV106U1 is CLOSED", MIX_CHECKBOX, 421),
		new MixData("2", "<html>5.3.1.2 ENSURE MIXXV107U1 is OPEN", MIX_CHECKBOX, 422),
			new MixData("0", "empty", MIX_EMPTY, 0),
		new MixData("0", "<html>NOTE:<br>"
				+ "These steps will secure the truck for transportation.", MIX_STRING, 0),
				new MixData("0", "empty", MIX_EMPTY, 0),
				
		new MixData("3", "<html>5.3.1.3 ENSURE all grounding/bonding wires are DISCONNECTED", MIX_CHECKBOX, 423),
			new MixData("0", "empty", MIX_EMPTY, 0),
		new MixData("0", "<html>NOTE:<br>"
				+ "Drip pans can be used to drain product from hoses.", MIX_STRING, 0),
				new MixData("0", "empty", MIX_EMPTY, 0),
				
		new MixData("4", "<html>5.3.1.4 ENSURE hoses are drained to remove the remaining product before<br>"
				+ "moving them away from the connection", MIX_CHECKBOX, 424),
		new MixData("5", "<html>5.3.1.5 ENSURE caps are placed at the end of the hose and other<br>"
				+ "connecting devices", MIX_CHECKBOX,425),
		new MixData("6", "<html>5.3.1.6 REMOVE wheel chocks and interlocks", MIX_CHECKBOX, 426),
		new MixData("6", "<html>5.3.1.7 INSPECT the lowermost drain and all outlets on tank truck prior to departure", MIX_CHECKBOX, 430),
	};
	
	public static final Object[] TREUCK_FALSE = {
		new MixData("8", "<html>5.3.1.8 ENSURE MIXXV105U1 is CLOSED", MIX_CHECKBOX, 427),
	};
	
	public static final Object[] OUTPUT_TRUE = {
		new MixData("1", "<html>11.1.1 REQUEST Maintenance to investigate Mix Tank Steam Flow.", MIX_CHECKBOX, 428),
	};
	
	public static final Object[] OUTPUT_FALSE = {
		new MixData("2", "<html>11.1.2 INCREASE Mix Tank Steam Flow MIXFC101U1 OUTPUT by 10% but do not exceed 90%.", MIX_CHECKBOX, 429),
	};
	
	public static final Object[] MIX_SAMPLE_DATA = {
			new MixData("1", "<html>5.2.1 SCAN Mix Tank QR Code to verify proper location", MIX_CHECKBOX, 404,0),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("2", "<html>5.2.2 RECORD Mix Tank level (gal)", MIX_TEXTINPUT, 501,0),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("3", "<html>5.2.3 REVIEW Mix Tank temperature and concentration", MIX_CHECKBOX, 405,0),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("4", "<html>5.2.4 RECORD 30-minute trend of the Mix Tank temperature and concentration", MIX_TEXTINPUT, 502,0),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("10", "<html>WARNING:<br> If at any time during Unloading the Mix Tank Temperature <br>"
					+ "MIXTC100UI is <195 DEG F, GO TO the Mix Tank Low <br>"
					+ "Temperature's section and RETURN to step.", MIX_STRING, 0),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("5", "<html>5.2.5 START monitoring for Mix Tank Low Temperature", MIX_CHECKBOX, 406,0),
			new MixData("6", "<html>5.2.6 SAMPLE Mix Tank and send to lab for analysis", MIX_CHECKBOX, 407,0),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("7", "<html>5.2.7 Is the Mix Tank sample GOOD?", GOOD_DATA, FALSE_DATA, MIX_RADIO, 7,1),
			
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("8", "<html>5.2.8 WAIT until Mix Tank < 5% or until 5 seconds has elapsed", MIX_CHECKBOX, 408,2),
			new MixData("9", "<html>5.2.9 STOP monitoring for Mix Tank Low Temperature", MIX_CHECKBOX, 409,2),

	};

	public static final Object[] MIX_TABLE_DATA = {
			
			new MixData("0", "<html>NOTE:<br>"
					+ "The steps below will ensure the Mix Tank is safe and ready to<br>sample.", MIX_STRING, 0),
					new MixData("0", "empty", MIX_EMPTY, 0),
					
			new MixData("1","5.1.1 ENSURE - CLOSED position",MIX_STRING,0),
			new MixData("2", "Close", MIX_TABLE, 0),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("3", "5.1.2 ENSURE - OPEN position", MIX_STRING, 0),
			new MixData("4", "Open", MIX_TABLE, 0),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("5", "5.1.3 ENSURE pump MIXP101U1 is RUNNING.", MIX_STRING, 0)

	};

	public static final Object[] MIX_UPLOADING_DATA = {
			new MixData("1", "<html>5.3.1 IF Mix Tank UNLOADED TO TRUCK?", TREUCK_TRUE, TREUCK_FALSE, MIX_RADIO, 1,0),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("2", "<html>5.3.2 RECORD Mix Tank level (gal)", MIX_TEXTINPUT, 503,1),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("3", "<html>5.3.3 CALCULATE amount of gallons transferred", MIX_TEXTINPUT, 504,1),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("4", "<html>5.3.4 RECORD 30 minute trend of the Mix Tank level", MIX_TEXTINPUT, 505,1),
	};
	
	public static final Object[] MIX_LOW_DATA = {
			new MixData("1", "<html>NOTE:<br>"
					+ "The next steps will attempt to increase the Mix Tank temperature<br>"
					+ "by adjusting the manual steam flow control.", MIX_STRING, 0),
			new MixData("2", "<html>If Mix Tank Steam Flow MIXFC101 OUTPUT >= 90%?", OUTPUT_TRUE, OUTPUT_FALSE, MIX_RADIO, 40,0),
			new MixData("0", "empty", MIX_EMPTY, 0),
			new MixData("1", "<html>GO TO step 5.2.3", MIX_STRING, 0),
	
	};
	
	public static final MixData[] MIX_INFO = {
			
		new MixData("1.0 PURPOSE", "<html>To ensure truck loading and unloading operations are completed safely and to eliminate the" +
				"<br> potential for injuries where vehicle and people interaction occurs.", MIX_STRING,1),
	
		new MixData("2.0 SCOPE", "<html>This procedure applies to all employees, truck operators and other personnel involved in the " +
				"<br>loading and unloading operations of trucks on the wharf, in the maintenance yard and in any " +
				"<br>other Port operated areas where loading and unloading of trucks occurs. Cargo types could" +
				"<br>include containers, palletised cargo, bulk bags, casing and project cargo", MIX_STRING,2),

		new MixData("2.1 Responsibilities", "<html>The employee is responsible to comply with this procedure and follow instructions from the" +
				"<br> supervisor or person in charge. The employee is also responsible to report all hazards and/or " +
				"<br>incidents to the supervisor as soon as possible. All truck operators are responsible to secure loads " +
				"<br>correctly.", MIX_STRING,3,2),

		new MixData("2.2 Background", "<html>Trucks may be loaded and unloaded either manually, by cranes and/or forklifts. The use of crane " +
				"<br>or forklift is determined by the Supervisor/person in charge. Loading/unloading cargo by forklift " +
				"<br>is to be done by the delegated competent forklift operator utilizing safe zones and work areas as " +
				"<br>per Forklift SOP.", MIX_STRING,4,2),
	
		new MixData("3.0 PRECAUTIONS & LIMITATIONS", "<html>Employees must not stand on truck trailers during placement or removal of loads by cranes " +
				"<br>or forklifts. Access to truck trailers should be avoided where possible and only occur with the"
				+ "<br>authorization of the supervisor/person in charge, once the load is landed and settled on the truck"
				+ "<br>and where an adjacent platform, for example a trailer, work platform or stairs, is in place. Stairs"
				+ "<br>need to be controlled by another person while in use.", MIX_STRING,5),
	
		new MixData("4.0 PREREQUISITE ACTIONS", "", MIX_STRING,6),
		new MixData("4.1 Radiological Concerns", "None", MIX_STRING,7,6),
		new MixData("4.2 Employee Safety", "<html>All Employees and Port Users are required to wear the minimum PPE requirements. For " +
				"<br>information on what requirements are to be met, employees can refer to the PPE SOP for more"
				+ "<br>detailed information.", MIX_STRING,8,6),
		new MixData("4.3 Special Tools and Equipment, Parts, and Supplies", "None", MIX_STRING,9,6),
		new MixData("4.4 Approvals & Notifications", "<html> 4.4.1 OBTAIN   Shift Manager approval prior to the initiation of this SOP", MIX_CHECKBOX,401,6),
		new MixData("4.5 Preliminary Actions", "<html>4.5.1 REVIEW   safety video prior to initiation of this SOP", MIX_CHECKBOX,402,6),

		new MixData("4.6 Field Preparations", "<html>4.6.1 REQUEST Shift Manager to determine if Mix Tank manual valve lineup is required to be performed", MIX_CHECKBOX,403,6),
	
		new MixData("5.0 PERFORMANCE", "Mix Tank Unloading Operation", MIX_STRING,16),

		new MixData("5.1 Preparation of Mix Tank for Unloading",
				"",
				MIX_TABLE_DATA, MIX_DATA,17,16),

		new MixData("5.2 Mix Tank Unloading", "",
				MIX_SAMPLE_DATA,MIX_DATA, 20, 16),

		new MixData("5.3 Completion of Mix Tank Unloading", "",
				MIX_UPLOADING_DATA, MIX_DATA,30,16),
		
		new MixData("6.0 REFERENCES", "", MIX_STRING,35),
		new MixData("6.1 Performance References", "<html>1. SOP-0011, Forklift Operations<br/>"
				+ "2. SOP-0022, Lifting Equipment Maintenance and Storage<br/>"
				+ "3. SOP-0033, Personal Protective Equipment (PPE)<br/>"
				+ "4. SOP-0044, Traffic Management SOP<br/>"
				+ "5. SOP-0055, Hazard and Incident Reporting Procedure</html>", MIX_STRING, 36,35),
		new MixData("6.2 Source Requirements", "<html>1. OSHA 1910.178, Powered industrial trucks<br/>"
				+ "2. OSHA 1910.132, General Requirements (PPE)<br/>"
				+ "3. OSHA 1910.147, Portable fire extinguishers</html>", MIX_STRING, 37,35),
	
		new MixData("7.0 RECORDS", "<html>This completed procedure is to be retained and dispositioned as directed by company policy.", MIX_STRING,38),
	
		new MixData("8.0 ATTACHMENTS", "None", MIX_STRING,39),				
		
		new MixData("11.0 MIX TANK LOW TEMPERATURE", "", MIX_LOW_DATA, MIX_DATA, 40,0)
	};	
}
