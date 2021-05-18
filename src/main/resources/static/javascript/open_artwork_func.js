function openArtwork(generatedArtworkName) {
    console.log(generatedArtworkName);
    const url = `http://localhost:8080/artworks/${generatedArtworkName}`;
    console.log(url);
    window.location.replace(url);
}