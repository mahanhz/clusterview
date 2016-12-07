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
