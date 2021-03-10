/**
 * Fetches a string from the server
 */
async function fetchString(){
    const responseFromServer = await fetch('/funny');
    const jsonFromServ = await responseFromServer.json();

    console.log(jsonFromServ);

    const jokeParagraph = document.getElementById("joke");
    jokeParagraph.innerText = jsonFromServ[Math.floor(Math.random() * jsonFromServ.length)];
}
