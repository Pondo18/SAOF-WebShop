function openArtwork(generatedArtworkName) {
    const url = `http://localhost:8080/artworks/${generatedArtworkName}`;
    window.location.replace(url);
}