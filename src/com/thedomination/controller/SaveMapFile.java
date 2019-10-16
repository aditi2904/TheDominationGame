package com.thedomination.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.thedomination.controller.MapOperations;
import com.thedomination.model.ContinentModel;
import com.thedomination.model.CountryModel;

/**
 * 
 *
 * @author Pritam Kumar
 * @version 1.0.0
 */

public class SaveMapFile {

	public String getMapOperationConcateString(MapOperations MapOperations, String fileName) {
		String concateString = "name "+fileName +" Map"+ System.lineSeparator();
		concateString = concateString.concat(System.lineSeparator());
		
		if (MapOperations.getContinentsList() != null ) {
			concateString = concateString.concat("[continents]" + System.lineSeparator());
			for (ContinentModel tempContinentModel : MapOperations.getInstance().getContinentsList()) {
				String continentConcateString = tempContinentModel.getContinentName() + " "
						+ tempContinentModel.getControlValue();
				concateString = concateString.concat(continentConcateString + System.lineSeparator());
			}
			concateString = concateString.concat(System.lineSeparator());
			concateString = concateString.concat("[countries]" + System.lineSeparator());
			
			for (CountryModel countryModel : MapOperations.getCountryList()) {
				String countryConcateString = countryModel.getCountryPosition()+" "+countryModel.getCountryName()+ " "+
				(MapOperations.getInstance().getContinentsList().indexOf(countryModel.getBelongsTo())+1)+" 0 0";
				countryConcateString = countryConcateString.concat(System.lineSeparator());
				concateString = concateString.concat(countryConcateString);
			}
			
			concateString = concateString.concat(System.lineSeparator());
			concateString = concateString.concat("[borders]" + System.lineSeparator());
			
			for(CountryModel countryModel : MapOperations.getCountryList()) {
				String countryNeighbourList = ""+countryModel.getCountryPosition();
				for(Integer neighbourPosition : countryModel.getListOfNewNeighbours()) {
					countryNeighbourList = countryNeighbourList+" "+neighbourPosition;
				}
				countryNeighbourList = countryNeighbourList.concat(System.lineSeparator());
				concateString = concateString.concat(countryNeighbourList);	
			}	
		}
		return concateString;
	}

	

	public boolean saveMapFile(MapOperations MapOperations, String fileName) {
		boolean isFileSaved = false;
		String concateString = getMapOperationConcateString(MapOperations, fileName);

		if (!(concateString == null || concateString.isEmpty() || concateString.trim().equalsIgnoreCase(""))) {
			PrintWriter out = null;
			try {
				out = new PrintWriter(fileName + ".map");
				out.println(concateString);
				isFileSaved = true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				isFileSaved = false;
			} finally {
				out.close();
			}
		}
		return isFileSaved;

	}
}