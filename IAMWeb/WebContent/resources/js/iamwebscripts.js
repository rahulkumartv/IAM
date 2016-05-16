/**
 * @author Rahul Thai Valappil
 * Java script file to handle the IAM System data request and manipulation 
 * 
 */
function postServlet(method,url,callback,datapair)
{
	var xhr;
	if( typeof XMLHttpRequest !== 'undefined')
	{
		xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function()
		{	
	   		if(xhr.readyState == 4 && xhr.status == 200)
	    	{
	   			if (typeof(callback) !== "undefined" && callback)
	   			{
	   				callback.call(xhr.responseText);
	   			}
	   			
	   			return;
	    	}
		}
		
	}
	else
	{
		var versions = ['MSXML2.XmlHttp.5.0','MSXML2.XmlHttp.4.0','MSXML2.XmlHttp.3.0','MSXML2.XmlHttp.2.0','Microsoft.XmlHttp'];
		for( var i=0,len=versions.length;i<len;i++)
		{
				xhr = new ActiveXObject(versions[i]);
		}
	}
	
	xhr.open(method, url, true);
	xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
	xhr.send(JSON.stringify(datapair));
}

function showError(htmlElem,message)
{
	htmlElem.innerText = message;
	htmlElem.style.display = "block";
	htmlElem.style.color = "Red";
}

function ResetMessage(htmlElem)
{
	htmlElem.innerText = "";
	htmlElem.style.display = "block";
	htmlElem.style.color = "Black";
}
function validateEmail(email) 
{
    var regExp = /\S+@\S+\.\S+/;
    return regExp.test(email);
}

function appendFormRow( htmlObj,type,id,label,value )
{
	var divObj = document.createElement("div");
	divObj.className = "form-group";
	var labelObj = document.createElement("label");
	labelObj.htmlFor = id;
	labelObj.className="col-sm-2 control-label";
	labelObj.innerHTML = label;
	divObj.appendChild(labelObj);
	var innerdivObj = document.createElement("div");
	innerdivObj.className= "col-sm-10";
	var inputObj =  document.createElement("input");
	inputObj.type = type;
	inputObj.class="form-control";
	inputObj.id=id;
	inputObj.value = value;
	inputObj.placeholder=label;
	innerdivObj.appendChild(inputObj);
	divObj.appendChild(innerdivObj);
	htmlObj.appendChild(divObj);
}
function CreateFormheader( htmlObj,label )
{
	var divObj = document.createElement("div");
	divObj.className = "form-group";
	divObj.style.border = "thick solid #FFFFFF";
	var heder = document.createElement("p");
	heder.innerHTML = label;
	divObj.appendChild(heder);
	htmlObj.appendChild(divObj);
	return divObj;
}

function CreateFormFooter( htmlObj,label )
{
	var divObj = document.createElement("div");
	divObj.className = "form-group";	
	var divHead = document.createElement("div");
	divHead.className = " col-sm-offset-2 col-sm-10 text-right";
	divObj.appendChild(divHead);
	return divObj;
}
function AppendButtontoDivGrp( divObj, type,value,clickEventHandler)
{
	var buttonObj = document.createElement("button");
	buttonObj.type= type;
	buttonObj.innerHTML = value;
	buttonObj.class="btn btn-primary";
	buttonObj.onclick= clickEventHandler;
	divObj.appendChild(buttonObj);
}