function updateTotals(dayId, taskAssignmentId){
	var totalForRow = 0.0;
	var totalForDay = 0.0;
	var currentField;
	var currentValue;
	for (var i = 1; i <= 7; i++){
		currentField = 'day' + i + "_" + taskAssignmentId;
		currentValue = parseFloat($(currentField).value);
		if(isNaN(currentValue)) {
			currentValue = 0.0;
		}
		totalForRow += currentValue;
		totalForRow = Math.round(totalForRow*Math.pow(10,2))/Math.pow(10,2)
	}
	$('row_' + taskAssignmentId).innerHTML = "<b>" + totalForRow + "</b>"
	
	
}