package com.dierauf.app.unitsconverter.ui.model;

import java.util.HashMap;
import java.util.Map;

public class UnitConversions {

	private final Map<String, UnitConversion> conversionMap_KeyedOnName = new HashMap<>();
	private final Map<String, UnitConversion> conversionMap_KeyedOnSymbol = new HashMap<>();

	private static final String[][] CONVERSIONS = new String[][] {
		{"minute", "min", "time", "60", "s"},
		{"hour", "h", "time", "3600", "s"},
		{"day", "d", "time", "86400", "s"},
		{"degree", "°", "Plane angle", "(π / 180)", "rad"},
		{"", "‘", "Plane angle", "(π / 10800)", "rad"},
		{"", "'", "Plane angle", "(π / 10800)", "rad"},
		{"second", "“", "Plane angle", "(π / 648000)", "rad"},
		{"second", "\"", "Plane angle", "(π / 648000)", "rad"},
		{"hectare", "ha", "area", "10000", "m^2"},
		{"litre", "L", "volume", "0.001", "m^3"},
		{"tonne", "t", "mass", "10^3", "kg"}
	};

	private static final UnitConversions INSTANCE = new UnitConversions();


	public static UnitConversions getInstance() {
		return INSTANCE;
	}


	private UnitConversions() {
		for (String[] conversion : CONVERSIONS) {
 			this.createConversionAndAddToMaps(conversion);
		}
	}


	private void createConversionAndAddToMaps(final String[] conversion) {
		UnitConversion unitConversion = new UnitConversion(conversion);
		if (!unitConversion.getName().equals(""))
			this.conversionMap_KeyedOnName.put(unitConversion.getName(), unitConversion);
		this.conversionMap_KeyedOnSymbol.put(unitConversion.getSymbol(), unitConversion);
	}

	public UnitConversion getUnitConversion(final String keyRaw) {
		if (keyRaw == null)
			throw new RuntimeException("Unknown units value: 'null'. ");
		String key = keyRaw.trim();
		if (this.conversionMap_KeyedOnName.containsKey(key))
			return this.conversionMap_KeyedOnName.get(key);
		else if (this.conversionMap_KeyedOnSymbol.containsKey(key))
			return this.conversionMap_KeyedOnSymbol.get(key);
		throw new RuntimeException("Unknown units value: '" + key + "'. ");
	}
}
