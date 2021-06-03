function reload_image(image_id, input) {
        const reader = new FileReader();

        reader.onload = function (e) {
            $('#' + image_id)
                .attr('src', e.target.result);
                // .width(300)
                // .height(300);
        };
        reader.readAsDataURL(input.files[0]);
        document.getElementById(image_id).style.visibility="visible";
}