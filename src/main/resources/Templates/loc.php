?>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='utf-8' />
    <title>Live Map</title>
    <meta name='viewport' content='width=device-width, initial-scale=1' />
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <script src='https://api.tiles.mapbox.com/mapbox-gl-js/v2.9.2/mapbox-gl.js'></script>
    <link href='https://api.tiles.mapbox.com/mapbox-gl-js/v2.9.2/mapbox-gl.css' rel='stylesheet' />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style>
        body {
            margin: 0;
            padding: 0;
        }

        #map {
            position: absolute;
            top: 0;
            bottom: 0;
            width: 100%;
        }
        .marker {

            background-size: cover;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            cursor: pointer;
        }
        .mapboxgl-popup {
            max-width: 200px;
        }

        .mapboxgl-popup-content {
            text-align: center;
            font-family: 'Open Sans', sans-serif;
        }
    </style>
</head>
<body>

<div id='map'></div>

<script>

    mapboxgl.accessToken = 'pk.eyJ1IjoicmloYWJuYWJsaTIyIiwiYSI6ImNsZm9mYWRzYjBybGQzcm1oNDlxbHoycDgifQ.3mAZiCdrDOmBXNhGOxkxtg';

    const map = new mapboxgl.Map({
        container: 'map',
        //style: 'mapbox://styles/mapbox/streets-v11',
        style: 'mapbox://styles/ethan-php/cl5v2t7fe000m14me1ihgd3z2',
        center: [-0.2792139521586705, 51.94498751258732],
        zoom: 9
    });
    // Add geolocate control to the map.
    map.addControl(
        new mapboxgl.GeolocateControl({
            positionOptions: {
                enableHighAccuracy: true
            },
// When active the map will receive updates to the device's location as it changes.
            trackUserLocation: true,
// Draw an arrow next to the location dot to indicate which direction the device is heading.

            showUserHeading: true
        })
    );

    // Adding markers below >>>
</script>
<?php

$dbhost = 'localhost';
         $dbuser = 'root';
         $dbpass = '';
         $dbname = '';
         $mysqli = new mysqli($dbhost, $dbuser, $dbpass, $dbname);

         if($mysqli->connect_errno ) {
printf("Connect failed: %s<br />", $mysqli->connect_error);
exit();
}
printf('Connected successfully.<br />');
$sql = "SELECT * FROM PharamcyLocation";

$result = $mysqli->query($sql);

if ($result->num_rows > 0) {
} else {
printf('Unexpected Error. DB did not return enough values for a successful export.<br />');
}

?>
<script>

    const geojson = {
        type: 'FeatureCollection',
        features: [
            <?php
    while ($row = mysqli_fetch_assoc($result)) {
        $displayName = $row['name'];
            ?>
        {
            type: "Feature",
                geometry: {
            type: "Point",
                coordinates: [<?php echo $row['amplitude']; ?>, <?php echo $row['altitude']; ?>]
        },
            properties: {
                title: "<?php echo $displayName; ?>",
                    description: "<?php echo $name; ?>"
            }
        },
        <?php
    }

    mysqli_free_result($result);
    $mysqli->close();
        ?>
    ]
    };

    // add markers to map
    // Popups and Display Details
    for (const feature of geojson.features) {
// create a HTML element for each feature
        const el = document.createElement('div');
        el.className = 'marker';

// make a marker for each feature and add it to the map
        new mapboxgl.Marker(el)
            .setLngLat(feature.geometry.coordinates)
            .setPopup(
                new mapboxgl.Popup({ offset: 25 }) // add popups
                    .setHTML(
                        `<h3>${feature.properties.title}</h3><p>${feature.properties.description}</p>`
                    )
            )
            .addTo(map);
    }
</script>

</body>
</html>