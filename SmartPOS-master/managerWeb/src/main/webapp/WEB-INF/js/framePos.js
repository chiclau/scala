function selectChang(selectCh) {
    var $selectBox = $(selectCh).parent(".selectBox");
    var $seachSelect = $selectBox.children(".seachSelect");
    $seachSelect.text($(selectCh).find("option:selected").text());
}

$(function () {
    $(".tableDetail tbody tr:even").css("background-color", "#fff");
    var placeholders = $(".placeholder");
    for (var i = 0; i < placeholders.length; i++) {
        var val = placeholders.eq(i).parent(".Pos_rela").find(".seachIptText").val();
        if (val == null || val == "") {
            placeholders.eq(i).css("display", "block");
        } else {
            placeholders.eq(i).css("display", "none");
        }
    }
    var inputText = $("input[type='text']");
    if (inputText.val() != null || inputText.val() != "") {
        $(this).next(".placeholder").show();
    } else {
        $(this).next(".placeholder").hide();
    }

    $(".seachIptText").attr("autocomplete", "off");

});


