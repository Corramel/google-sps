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

/**
 * Gets a list of images from the server
 * Trying the JSON method of listing the files
 */
async function fetchImages(){
    const responseFromServer = await fetch('/list-images');
    const jsonFromServ = await responseFromServer.json();
    console.log(`Did the JSON return as an actual object? ${jsonFromServ != null}`);
    console.log(jsonFromServ);
    const imageContainer = document.getElementById("images");
    if (jsonFromServ.length > 0) {
        jsonFromServ.forEach(src => appendImage(src, imageContainer));
    } else {
        imageContainer.innerText = "No images yet. :(";
    }
}

/**
 * Creates an image given a source and appends it to the parent.
 * @param {URL} src Source of image
 * @param {Document Element} parent Element to append image to 
 */
function appendImage(src, parent) {
    let img = document.createElement("img");
    img.src = src;
    parent.appendChild(img);
}