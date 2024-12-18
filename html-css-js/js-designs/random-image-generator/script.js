// script.js

// Function to generate the image URL
function getRandomImage() {
    const imageUrl = "https://picsum.photos/1900/800";  // This fetches a random image with 1900x800 resolution
    document.getElementById("random-image").src = imageUrl;
}

// Call the function to set the random image on page load
window.onload = getRandomImage;
