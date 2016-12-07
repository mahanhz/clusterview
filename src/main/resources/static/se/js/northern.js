$(function() {
	$('.northernRegionImage').mapster( {
    fillColor: 'cad102',
    fillOpacity: 0.2,
    stroke: false,
    clickNavigate: true,
    showToolTip: true,
    mapKey: 'data-group',
    areas: [
        {
            key: 'norrbotten-cluster',
            toolTip: 'Norrbotten',
            stroke: false
        },
        {
            key: 'central-region',
            toolTip: 'Central region',
            stroke: false,
            staticState: true,
            fillColor: 'ffffff',
            fillOpacity: 0.8,
            render_highlight: {
                  fillOpacity: 0
            }
		},
        {
            key: 'central-region2',
            toolTip: 'Central region',
            stroke: false,
            staticState: true,
            fillColor: 'ffffff',
            fillOpacity: 0.8,
            render_highlight: {
                  fillOpacity: 0
            }
		},
        {
            key: 'southern-region',
            toolTip: 'Southern region',
            stroke: false,
            staticState: true,
            fillColor: 'ffffff',
            fillOpacity: 0.8,
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
            fillOpacity: 0.8,
            render_highlight: {
                   fillOpacity: 0
            }
        }]
    });
});