var taSourceRow;
var taCounter;

Event.observe(window, 'load', function() {
    taCounter=1
    taSourceRow1 = $('taSourceRow');
    try{
        taSourceRow = taSourceRow1.cloneNode(true);
        $('taSourceRow').remove();
        var newRow = taSourceRow.cloneNode(true);
        newRow.id = "taDataRow" + taCounter.toString()
        $('taTable').appendChild(newRow);
    }catch (e){
        alert(e);
    }
});

function addNewRow(){
    newRow = taSourceRow.cloneNode(true);
    taCounter++;
    newRow.id = "taDataRow" + taCounter.toString();
    $('taTable').appendChild(newRow);
}

function respondToSelect(event)
{
    try{
        new Ajax.Updater("laborCategorySelect",
            appName + "/lookup/laborCategory",
            {
                method:'get',
                parameters: {
                    selectedValue : $F("task.id")
                    }
                }
        );
    new Ajax.Updater("chargeCodeSelect",
        appName + "/lookup/chargeCode",
        {
            method:'get',
            parameters: {
                selectedValue : $F("task.id")
                }
            }
    );
} catch (e){
    alert(e)
    }
}

function addRow(){
    addNewRow()
}

