package com.dierauf.app.unitsconverter.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dierauf.app.unitsconverter.ui.model.request.UnitsconverterRequestModel;
import com.dierauf.app.unitsconverter.ui.model.response.UnitsconverterResponseModel;

@RestController
@RequestMapping("units")
public class UnitsConverterController {

	@GetMapping("si")
	public UnitsconverterResponseModel getUnits(@RequestParam final String units) {
		UnitsconverterRequestModel requestModel = new UnitsconverterRequestModel(units);
		UnitsconverterResponseModel responseModel = requestModel.getUnitsRest();
		return responseModel;
	}
}
