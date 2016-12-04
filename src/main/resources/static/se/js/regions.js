$(function() {
	$('.regionsImage').mapster( {
    fillColor: 'cad102',
    fillOpacity: 0.2,
    stroke: false,
    clickNavigate: true,
    showToolTip: true,
    //wrapClass: 'center-block',
    mapKey: 'data-group',
    areas: [{
            key: 'northern-region',
            toolTip: 'Northern region',
            stroke: false
		},
        {
            key: 'central-region',
            includeKeys: 'central-region2',
            toolTip: 'Central region',
            stroke: false
        },
        {
            key: 'central-region2',
            includeKeys: 'central-region',
            toolTip: 'Central region',
            stroke: false
        },
        {
            key: 'southern-region',
            includeKeys: 'southern-region2',
            toolTip: 'Southern region',
            stroke: false
        },
        {
            key: 'southern-region2',
            includeKeys: 'southern-region',
            toolTip: 'Southern region',
            stroke: false
		},
		{
            key: 'stockholm-cluster',
            toolTip: 'Stockholm',
            fillColor: '8e2a16',
            fillOpacity: 0.5,
            staticState: true,
            stroke: false
        }]
    });
});

$('#small').bind('click',function() {
    $('.regionsImage').mapster('resize', 245, 0, 1000);
});

$('#large').bind('click',function() {
    $('.regionsImage').mapster('resize', 345, 0, 1000);
});

$('#xlarge').bind('click',function() {
    $('.regionsImage').mapster('resize', 490, 0, 1000);
});

$('#xxlarge').bind('click',function() {
    $('.regionsImage').mapster('resize', 600, 0, 1000);
});