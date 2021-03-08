/*
* Calls fetch() and adds response to the webpage
*/

async function fetchString(){
    const responseFromServer = await fetch('/funny');
    const jsonFromServ = await responseFromServer.json();

    console.log(jsonFromServ);

    const linuxParagraph = document.getElementById("linux");
    linuxParagraph.innerText = jsonFromServ[Math.floor(Math.random() * jsonFromServ.length)];
}
