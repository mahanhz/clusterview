$(function() {
	$('.southernRegionImage').mapster( {
    fillColor: 'cad102',
    fillOpacity: 0.2,
    stroke: false,
    clickNavigate: true,
    showToolTip: true,
    mapKey: 'data-group',
    areas: [
        {
            key: 'vastragotaland-cluster',
            toolTip: 'Vastra gotaland',
            stroke: false
        },
        {
            key: 'ostergotland-cluster',
            toolTip: 'Ostergotland',
            stroke: false
        },
        {
            key: 'oland',
            includeKeys: 'kalmar',
            toolTip: 'Kalmar lan',
            stroke: false
        },
        {
            key: 'kalmar',
            includeKeys: 'oland',
            toolTip: 'Kalmar lan',
            stroke: false
        },
        {
            key: 'jonkoping-cluster',
            toolTip: 'Jonkoping',
            stroke: false
        },
        {
            key: 'halland-cluster',
            toolTip: 'Halland',
            stroke: false
        },
        {
            key: 'kronoberg-cluster',
            toolTip: 'Kronoberg',
            stroke: false
        },
        {
            key: 'blekinge-cluster',
            toolTip: 'Blekinge',
            stroke: false
        },
        {
            key: 'skane-cluster',
            toolTip: 'Skane',
            selected: true,
            render_select: {
                fillColor: '7ac452',
                fillOpacity: 0.3,
                stroke: false
            }
        },
        {
            key: 'northern-region',
            toolTip: 'Northern region',
            stroke: false,
            staticState: true,
            fillColor: 'ffffff',
            fillOpacity: 0.8,
            render_highlight: {
                  fillOpacity: 0
            }
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
		}]
    });
});