
function doCR(){
    var str = "{\"type\":\"CR\"," + 
         "\"company_id\":\"" + document.getElementsByClassName("input")[0].value + "\"," +
         "\"company_name\":\"" + document.getElementsByClassName("input")[1].value + "\"}";

    console.log(str);
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:7777/requests", true);
    xhttp.send(str);
}

