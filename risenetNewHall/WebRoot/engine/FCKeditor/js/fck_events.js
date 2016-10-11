/*
 * fck_events.js: Creates the events object that handles the editor
 * changes events for the toolbar.
 */

var events = new Object() ;

events.attachEvent = function(eventName, attachedFunction)
{
	if (! events["ev" + eventName]) 
		events["ev" + eventName] = new Array() ;
	events["ev" + eventName][events["ev" + eventName].length] = attachedFunction ;
}

events.fireEvent = function(eventName, params)
{
	var oEvents = events["ev" + eventName] ;
	
	if (oEvents) 
	{
		for (i in oEvents)
		{
			if (typeof(oEvents[i]) == "function")
				oEvents[i](params) ;
			else
				oEvents[i][eventName](params) ;
		}
	}
}