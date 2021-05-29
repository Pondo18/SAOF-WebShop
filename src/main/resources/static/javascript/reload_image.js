function reload_image(image_id, input) {
        const reader = new FileReader();

        reader.onload = function (e) {
            $('#' + image_id)
                .attr('src', e.target.result)
                .width(150)
                .height(200);
        };
        reader.readAsDataURL(input.files[0]);
}