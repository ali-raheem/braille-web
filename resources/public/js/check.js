function getValue(id, value) {
    if(document.getElementById(id).checked){
        return value;
    } else {
        return 0;
    }
}
function decodeBraille() {
    var total = getValue("cb1", 1);
    total += getValue("cb2", 2);
    total += getValue("cb4", 4);
    total += getValue("cb8", 8);
    total += getValue("cb16", 16);
    total += getValue("cb32", 32);
    document.getElementById("braille-data").value = total;
    document.getElementById("braille-form").submit();
}
