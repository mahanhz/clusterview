$(document).ready(function() {
    var i=1;
    $("#add_row").click(function() {
        $('#member'+i).html('<td>' + (i+1) + '</td><td><input type="text" name="firstName' + i + '"  placeholder="First name" class="form-control"/></td><td><input type="text" name="middleName' + i + '" placeholder="Middle name" class="form-control"/></td><td><input type="text" name="lastName' + i + '" placeholder="Last name" class="form-control"/></td><td><input type="text" name="suffix' + i + '" placeholder="Suffix" class="form-control"/></td><td><input type="text" name="capability' + i + '" placeholder="Capability" class="form-control"/></td><td><input type="text" name="commitment' + i + '" placeholder="Commitment" class="form-control"/></td><td><input type="text" name="location' + i + '" placeholder="Location" class="form-control"/></td>');
        $('#tab_logic').append('<tr id="member'+(i+1)+'"></tr>');
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

$('#activitiesModal').on('show.bs.modal', function (event) {
  var button = $(event.relatedTarget); // Button that triggered the modal
  var prefix = button.data('activity-prefix'); // Extract info from data-* attributes
  var title = button.data('activity-type');

  var modal = $(this);
  modal.find('.modal-title').text(title);
  modal.find('.modal-body input').each(function() {
      $(this).attr("name", prefix + $(this).attr("name"));
  });
})

