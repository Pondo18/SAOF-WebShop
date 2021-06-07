function change_lang() {
    const input_switch = document.getElementById("lang");
    let url="";
    const referer = document.referrer;
    if (input_switch.checked) {
        url = referer +`?lang=en`;
    } else {
        url = referer +`?lang=de`;
    }
    window.location.replace(url);
}