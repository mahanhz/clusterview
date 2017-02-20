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
            fillColor: 'f72727',
            fillOpacity: 0.3,
            staticState: true,
            stroke: false
        },
        {
            key: 'vasterbotten-cluster',
            toolTip: 'Vasterbotten',
            fillColor: '7ac452',
            fillOpacity: 0.3,
            staticState: true,
            stroke: false
        },
        {
            key: 'malardalen-cluster',
            toolTip: 'Malardalen',
            fillColor: '7ac452',
            fillOpacity: 0.3,
            staticState: true,
            stroke: false
        },
        {
            key: 'skane-cluster',
            toolTip: 'Skane',
            fillColor: '7ac452',
            fillOpacity: 0.3,
            staticState: true,
            stroke: false
        }]
    });
});
