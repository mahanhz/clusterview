$(document).ready(function() {
	$(".fancyLightbox").fancybox({
		maxWidth	: 800,
		maxHeight	: 600,
		fitToView	: false,
		width		: '70%',
		height		: '70%',
		autoSize	: false,
		closeClick	: false,
		openEffect	: 'elastic',
		closeEffect	: 'elastic',
        beforeClose : function() {
            // only reload parent page when a confirmation has been displayed (i.e. after an action has been performed)
            if ($('.fancybox-iframe').contents().find("#confirmationMessage").length) {
                location.reload();
                return;
            }
        }
	});
});