document.observe('dom:loaded', function() {
    $("task.id").observe("change", respondToSelect);

});


function respondToSelect(event)
{
    try{
       new Ajax.Updater("laborCategorySelect",
          appName + "/lookup/laborCategory",
          {method:'get', parameters: {selectedValue : $F("task.id")} }
         );
       new Ajax.Updater("chargeCodeSelect",
          appName + "/lookup/chargeCode",
          {method:'get', parameters: {selectedValue : $F("task.id")} }
         );
    } catch (e){alert(e)}
}

