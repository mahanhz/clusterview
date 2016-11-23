$(document).ready(function() {
    var i = parseInt($("#numMembers").val());

    $("#add_row").click(function() {
        $('#member'+i).html('<td>' + (i+1) + '</td><td><input type="text" name="members[' + i + '].name.firstName"  placeholder="First name" class="form-control"/></td><td><input type="text" name="members[' + i + '].name.middleName" placeholder="Middle name" class="form-control"/></td><td><input type="text" name="members[' + i + '].name.lastName" placeholder="Last name" class="form-control"/></td><td><input type="text" name="members[' + i + '].name.suffix" placeholder="Suffix" class="form-control"/></td><td><select id="capability' + i + '-select" multiple="multiple"><option value="sc">Study Circle</option><option value="dm">Devotional Meeting</option><option value="cc">Childrens class</option><option value="jyg">Junior Youth Group</option><option value="hv">Home Visit</option></select></td><td><select id="commitment' + i + '-select" multiple="multiple"><option value="sc">Study Circle</option><option value="dm">Devotional Meeting</option><option value="cc">Childrens class</option><option value="jyg">Junior Youth Group</option><option value="hv">Home Visit</option></select></td>');

        $('#capability' + i + '-select').multiselect({
            numberDisplayed: 1,
            checkboxName: function(option) {
                return 'members[' + i + '].capability.activities';
            }
        });

        $('#commitment' + i + '-select').multiselect({
            numberDisplayed: 1,
            checkboxName: function(option) {
                return 'members[' + i + '].commitment.activities';
        }});

        $('#membersTable').append('<tr id="member'+(i+1)+'"></tr>');
        i++;
    });

    $("#delete_row").click(function() {
        if(i>1) {
		    $("#member"+(i-1)).html('');
		    i--;
		}
	});

	$('#capability0-select').multiselect({
	    numberDisplayed: 1,
	    checkboxName: function(option) {
            return 'members[0].capability.activities';
        }
	});

	$('#commitment0-select').multiselect({
	    numberDisplayed: 1,
        checkboxName: function(option) {
            return 'members[0].commitment.activities';
    }});
});

$('.map').click(function(e) {
    var offset = $(this).offset();
    var relativeX = (e.pageX - offset.left);
    var relativeY = (e.pageY - offset.top);

    $(".groupPos").remove()
    $("#groupLocationModal").append('<i class="fa fa-users clusterGroup groupPos" aria-hidden="true">')
    $(".groupPos").css("left", e.pageX - 10)
    $(".groupPos").css("top", e.pageY - 10)

    $("#locationX").val(relativeX);
    $("#locationY").val(relativeY);
});

