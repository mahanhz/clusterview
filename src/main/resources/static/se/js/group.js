$(document).ready(function() {
    var i = parseInt($("#numMembers").val());

    $("#add_row").click(function() {
        $('#member'+i).html('<td>' + (i+1) + '</td><td><input type="text" name="members[' + i + '].name.firstName"  placeholder="First name" class="form-control"/></td><td><input type="text" name="members[' + i + '].name.middleName" placeholder="Middle name" class="form-control"/></td><td><input type="text" name="members[' + i + '].name.lastName" placeholder="Last name" class="form-control"/></td><td><input type="text" name="members[' + i + '].name.suffix" placeholder="Suffix" class="form-control"/></td><td><select class="activitiesMultiSelect" name="members[' + i + '].capability.activities" multiple="multiple"><option value="sc">Study Circle</option><option value="dm">Devotional Meeting</option><option value="cc">Childrens class</option><option value="jyg">Junior Youth Group</option><option value="hv">Home Visit</option></select></td><td><select class="activitiesMultiSelect" name="members[' + i + '].commitment.activities" multiple="multiple"><option value="sc">Study Circle</option><option value="dm">Devotional Meeting</option><option value="cc">Childrens class</option><option value="jyg">Junior Youth Group</option><option value="hv">Home Visit</option></select></td>');

        $('.activitiesMultiSelect').multiselect({
            numberDisplayed: 1
        });

        $('#membersTable').append('<tr id="member'+(i+1)+'"></tr>');
        i++;
    });

    $("#delete_row").click(function() {
        if(i>1) {
		    $("#member"+(i-1)).html('');
		    i--;
		}
	});

    $('.activitiesMultiSelect').multiselect({
        numberDisplayed: 1
    });
});

$('.map').click(function(e) {
    var offset = $(this).offset();
    var relativeX = (e.pageX - offset.left);
    var relativeY = (e.pageY - offset.top);

    $("#groupLocationModal .cluster-group").css('visibility', 'visible');
    $("#groupLocationModal .cluster-group").css('left', relativeX - 10);
    $("#groupLocationModal .cluster-group").css('top', relativeY - 10);

    $("#location\\.coordX").val(relativeX);
    $("#location\\.coordY").val(relativeY);
});

