$(document).ready(function() {

    sessionTimeout();

	$(".fancyLightbox").fancybox({
		maxWidth	: 800,
		maxHeight	: 600,
		fitToView	: false,
		width		: '70%',
		height		: '70%',
		autoSize	: false,
		closeClick	: false,
		openEffect	: 'elastic',
		closeEffect	: 'elastic'
	});
});

function sessionTimeout() {
    var sessionTimeoutInSeconds = $('#sessionTimeoutInSeconds').val();
    var sessionTimeoutMilliseconds = 600000; // Default to 10 mins

    if (sessionTimeoutInSeconds) {
        sessionTimeoutMilliseconds = sessionTimeoutInSeconds * 1000;
    }

    $.sessionTimeout({
        message: 'Your session will expire in 1 minute.',
        ajaxType: 'GET',
        keepAliveUrl: '/keep-alive.html',
        logoutUrl: '/logout',
        redirUrl: '/logout',
        warnAfter: sessionTimeoutMilliseconds - 60000,
        redirAfter: sessionTimeoutMilliseconds
    });
}

function clusterNotFound() {
    $("#groupOperations").css('display', 'none');
}

$('#small').bind('click',function() {
    $('.adjustableImage').mapster('resize', 245, 0, 1000);
});

$('#large').bind('click',function() {
    $('.adjustableImage').mapster('resize', 345, 0, 1000);
});

$('#xlarge').bind('click',function() {
    $('.adjustableImage').mapster('resize', 490, 0, 1000);
});

$('#xxlarge').bind('click',function() {
    $('.adjustableImage').mapster('resize', 600, 0, 1000);
});