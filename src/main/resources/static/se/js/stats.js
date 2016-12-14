$(document).ready(function() {

    $('.clustersMultiSelect').multiselect({
        numberDisplayed: 1,
        buttonWidth: '300px',
        onChange: function(element, checked) {
            $("#errorMessages").css("display", "none");
        }
    });
});

function viewClusterHistory(baseUri) {
    var selectedOption = $('.clustersMultiSelect option:selected').val();

    if (selectedOption == "") {
        $("#errorMessages").css("display", "block");

        return false;
    }

    window.location.href = baseUri + selectedOption;
}