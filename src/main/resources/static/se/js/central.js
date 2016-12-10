$(function() {
	$('.centralRegionImage').mapster( {
    fillColor: 'cad102',
    fillOpacity: 0.2,
    stroke: false,
    clickNavigate: true,
    showToolTip: true,
    mapKey: 'data-group',
    areas: [
        {
            key: 'stockholm-cluster',
            toolTip: 'Stockholm',
            selected: true,
            render_select: {
                fillColor: 'f72727',
                fillOpacity: 0.3,
                stroke: false
            }
        },
        {
            key: 'uppsala-cluster',
            toolTip: 'Uppsala',
            stroke: false
        },
        {
            key: 'malardalen-cluster',
            toolTip: 'Malardalen',
            selected: true,
            render_select: {
                fillColor: 'f78f27',
                fillOpacity: 0.3,
                stroke: false
            }
        },
        {
            key: 'gavleborg-cluster',
            toolTip: 'Gavleborg',
            stroke: false
        },
        {
            key: 'dalarna-cluster',
            toolTip: 'Dalarna',
            stroke: false
        },
        {
            key: 'varmland-cluster',
            toolTip: 'Varmland',
            stroke: false
        },
        {
            key: 'gotland-cluster',
            toolTip: 'Gotland',
            stroke: false
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