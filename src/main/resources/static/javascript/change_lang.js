function change_lang() {
    const input_switch = document.getElementById("lang");
    let url;
    let referer = document.referrer;
    if (referer === window.location.origin + "/login" || referer === window.location.origin + "/registration/user") {
        referer = window.location.origin;
    }
    try {
        referer = referer.replace('?lang=de', '');
        referer = referer.replace('?lang=en', '');
    } catch(ex) {
    }
    if (input_switch.checked) {
        url = referer +`?lang=en`;
    } else {
        url = referer +`?lang=de`;
    }
    window.location.replace(url);
}