function jumpAjax(method, url, data) {
    var myForm = document.createElement("form");
    myForm.method = method;
    myForm.action = url;
    for (var key in data) {
        var seq = document.createElement("input");
        seq.setAttribute("name", key);
        seq.setAttribute("value", data[key]);
        myForm.appendChild(seq);
    }
    document.body.appendChild(myForm);
    myForm.submit();
    document.body.removeChild(myForm);
}