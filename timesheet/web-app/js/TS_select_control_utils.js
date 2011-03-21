// TS_select_control_utils.js
function selectAllListBoxesByIds(selectItemListIds) {
    for (var i=0; i<selectItemListIds.length; i++) {
        selectItemsListBox(selectItemListIds[i]);
    }
}
        
function selectItemsListBox(selectedListBoxName) {
            
    selectedList = document.getElementById(selectedListBoxName);
    for (var i=(selectedList.options.length-1); i>=0; i--) {
        var o = selectedList.options[i];
        if (!o.selected) {
            o.selected = true;
        }
    }
}
        
function moveSelectedOptions(from,to) {
    fromThis = document.getElementById(from)
    toThat = document.getElementById(to)
    // Unselect matching options, if required
    if (arguments.length>3) {
        var regex = arguments[3];
        if (regex != "") {
            unSelectMatchingOptions(fromThis,regex);
        }
    }
    // Move them over
    for (var i=0; i<fromThis.options.length; i++) {
        var o = fromThis.options[i];
        if (o.selected) {
            toThat.options[toThat.options.length] = new Option( o.text, o.value, false, false);
        }
    }
    // Delete them fromThis original
    for (var i=(fromThis.options.length-1); i>=0; i--) {
        var o = fromThis.options[i];
        if (o.selected) {
            fromThis.options[i] = null;
        }
    }
    //        	if ((arguments.length<3) || (arguments[2]==true)) {
    //	        	sortSelect(fromThis);
    //	        	sortSelect(toThat);
    //        	}
    fromThis.selectedIndex = -1;
    toThat.selectedIndex = -1;
}
        
function moveToRight( leftListName, rightListName ) {
    var leftList = document.getElementById(leftListName).options
    var rightList = document.getElementById(rightListName).options
    for (var i = leftList.length; i >=0; i--) {
        if (leftList[i].selected) {
					
            rightList.add(leftList[i])
        }
    }
}

function moveTextToList(fromId, fromText, toId) {
    fromIdThis = document.getElementById(fromId)
    fromTextThis = document.getElementById(fromText)
    toThat = document.getElementById(toId)
    toThat.options.add(new Option( fromTextThis.value, fromIdThis.value, false, false));
}

function removeTextFromList(listBoxId) {
    var listBox = document.getElementById(listBoxId)
    // Delete from list
    for (var i=(listBox.options.length-1); i>=0; i--) {
        var o = listBox.options[i];
        if (o.selected) {
            listBox.options[i] = null;
        }
    }
}
