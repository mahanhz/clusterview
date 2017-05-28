$(document).ready(function() {

    $('.activitiesMultiSelect').multiselect({
        numberDisplayed: 1,
        buttonWidth: '250px'
    });
});

$("#add_row").click(function() {
    var i = parseInt($('input[type="checkbox"][name="memberIds"]').length);
    var options = $("#activityOptions").html();
    $('#member' + i).html('<td><input type="checkbox" name="memberIds" /></td><td><input type="text" name="members[' + i + '].name.firstName"  placeholder="First name" class="form-control"/></td><td><input type="text" name="members[' + i + '].name.lastName" placeholder="Last name" class="form-control"/></td><td><select class="activitiesMultiSelect" name="members[' + i + '].capability.activities" multiple="multiple">' + options + '</select></td><td><select class="activitiesMultiSelect" name="members[' + i + '].commitment.activities" multiple="multiple">' + options + '</select></td>');

    $('.activitiesMultiSelect').multiselect({
        numberDisplayed: 1,
        buttonWidth: '250px'
    });

    $('#membersTable').append('<tr id="member'+(i+1)+'"></tr>');
});

$("#delete_row").click(function() {
    var selectedMembers = $('input[type="checkbox"][name="memberIds"]:checked');
    var numSelected = selectedMembers.length;

    if (numSelected > 0) {
        selectedMembers.each(function() {
            $(this).closest('tr').remove();
        });

        //reset the index of each row
        var index = 0;
        $('#membersTable tbody tr').each(function (rowIndex, element) {
            $(this).attr('id', $(this).attr('id').replace(/member\d+/g, "member" + index));

            $(this).find(':input').each(function (rowIndex, element) {
                var nameValue = $(this).attr("name");

                if (nameValue !== undefined) {
                    $(this).attr('name', $(this).attr('name').replace(/members\[\d+\]/g, "members[" + index + "]"));
                }
            });

            index++;
        });
    }
});

$('.map').click(function(e) {
    var offset = $(this).offset();
    var relativeX = (e.pageX - offset.left);
    var relativeY = (e.pageY - offset.top);

    $("#groupLocation .cluster-group").css('display','block');
    $("#groupLocation .cluster-group").css('left', relativeX - 10);
    $("#groupLocation .cluster-group").css('top', relativeY - 10);

    $("#location\\.coordX").val(relativeX);
    $("#location\\.coordY").val(relativeY);
});

