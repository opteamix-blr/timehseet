document.observe('dom:loaded', function() {
    $("task.id").observe("change", respondToSelect);

});


function respondToSelect(event)
{
    try{
       new Ajax.Updater("laborCategorySelect",
          "/Timesheet/lookup/laborCategory",
          {method:'get', parameters: {selectedValue : $F("task.id")} }
         );
       new Ajax.Updater("chargeCodeSelect",
          "/Timesheet/lookup/chargeCode",
          {method:'get', parameters: {selectedValue : $F("task.id")} }
         );
    } catch (e){alert(e)}
}

