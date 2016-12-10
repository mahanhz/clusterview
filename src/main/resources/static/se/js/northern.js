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
            key: 'vasterbotten-cluster',
            toolTip: 'Vasterbotten',
            selected: true,
            render_select: {
                fillColor: 'f78f27',
                fillOpacity: 0.3,
                stroke: false
            }
        },
        {
            key: 'jamtland-cluster',
            toolTip: 'Jamtland',
            stroke: false
        },
        {
            key: 'vasternorrland-cluster',
            toolTip: 'Vasternorrland',
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