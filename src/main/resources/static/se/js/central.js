$(function() {
	$('.centralRegionImage').mapster( {
    fillColor: 'cad102',
    fillOpacity: 0.2,
    stroke: false,
    clickNavigate: true,
    showToolTip: true,
    mapKey: 'data-group',
    areas: [{
            key: 'northern-region',
            toolTip: 'Northern region',
            stroke: false,
            staticState: true,
            fillColor: 'ffffff',
            fillOpacity: 1,
            render_highlight: {
                  fillOpacity: 0
             }
		},
        {
            key: 'central-region',
            toolTip: 'Central region',
            stroke: false
        },
        {
            key: 'southern-region1',
            toolTip: 'Southern region',
            stroke: false,
            staticState: true,
            fillColor: 'ffffff',
            fillOpacity: 1,
            render_highlight: {
                  fillOpacity: 0
             }
        },
        {
            key: 'southern-region2',
            toolTip: 'Southern region',
            stroke: false,
            staticState: true,
            fillColor: 'ffffff',
            fillOpacity: 1,
            render_highlight: {
                   fillOpacity: 0
            }
        },
        {
            key: 'southern-region3',
            toolTip: 'Southern region',
            stroke: false,
            staticState: true,
            fillColor: 'ffffff',
            fillOpacity: 1,
            render_highlight: {
                   fillOpacity: 0
            }
		}]
    });
});