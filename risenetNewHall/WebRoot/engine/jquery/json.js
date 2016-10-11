function toJSON(object){
	if(typeof object=='array') return arrayToJSON();
	var temp = [];
	temp.push("{");
	for(var mem in object){
		switch(typeof(object[mem])){
			case "function":
				temp.push("\""+mem+"\":"+object[mem].toString().replace(/ {0,}[\t\r\v\f\n] {0,}/g,"").replace(/\s{2,}/g, " "));
				break;
			case "object" :
				temp.push(object[mem]===null? "\"" + mem + "\":" + object[mem]: "\"" + mem + "\":" + object[mem].toJSON());
				break;
			case "undefined" :
			case "number"    :
			case "boolean"   : temp.push("\""+mem+"\":"+object[mem]); break;
			case "string"    :
			default          : temp.push("\""+mem+"\":\""+object[mem].replace(/"/g,'\\"')+"\""); break;
			
		}
		temp.push(", ");
	}
	temp.push("}");
	var strTemp = temp.join("").replace(/, {0,}$/,"");
	return strTemp;
}

function arrayToJSON(array){
	var temp = ["["];
	for(var i=0; i < array.length; i++){
		switch(typeof(array[i])){
			case "function":
			temp.push
				(
					array[i].toString().replace(/ {0,}[\t\r\v\f\n] {0,}/g,"").replace(/\s{2,}/g, " ")
				);
				break;
			case "undefined" :
			case "number"    :
			case "boolean"   : temp.push(""+array[i]); break;
			case "object"    : temp.push(array[i]===null ? array[i] : array[i].toJSON()); break;
			case "string"    :
			default          : temp.push("\""+array[i].replace(/"/g,'\\"')+"\""); break;
		}
		temp.push(i==(array.length-1) ? "" : ", ");
	}
	temp.push("]");
	return temp.join("");
}

