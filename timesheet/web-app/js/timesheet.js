function updateTotals(dayId, chargeCodeId){
	var totalForRow = 0.0;
	var totalForDay = 0.0;
	var currentField;
	var currentValue;
	for (var i = 1; i <= 7; i++){
		currentField = 'day' + i + "_" + chargeCodeId;
		currentValue = parseFloat($(currentField).value);
		if(isNaN(currentValue)) {
			currentValue = 0.0;
		}
		totalForRow += currentValue;
	}
	$('row_' + chargeCodeId).innerHTML = "<b>" + totalForRow + "</b>"
	
	
}