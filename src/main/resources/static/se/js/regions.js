$(function() {
	$('.regionsImage').mapster( {
    fillColor: 'cad102',
    fillOpacity: 0.2,
    stroke: false,
    clickNavigate: true,
    showToolTip: true,
    mapKey: 'data-group',
    areas: [{
            key: 'northern-region',
            toolTip: 'Northern region',
            stroke: false
		},
        {
            key: 'central-region',
            toolTip: 'Central region',
            stroke: false
        },
        {
            key: 'southern-region1',
            includeKeys: 'southern-region2,southern-region3',
            toolTip: 'Southern region',
            stroke: false
        },
        {
            key: 'southern-region2',
            includeKeys: 'southern-region1,southern-region3',
            toolTip: 'Southern region',
            stroke: false
        },
        {
            key: 'southern-region3',
            includeKeys: 'southern-region1,southern-region2',
            toolTip: 'Southern region',
            stroke: false
		}]
    });
});