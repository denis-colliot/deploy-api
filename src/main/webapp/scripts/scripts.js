/*
---------------------------------------------------------------

CONTROLLERS ACCESS.

---------------------------------------------------------------
*/

function getAllBuilds() {
    $.get(url('builds'), function(data) {
        $('#builds-wrapper').html(data);
    });
}

function getBuildVersions(build) {
    $.get(url('builds'), function(data) {
        $('#builds-wrapper').html(data);
    });
}

/*
---------------------------------------------------------------

DOM BINDINGS.

---------------------------------------------------------------
*/

getAllBuilds();

$('body').on('change', '.build-cb', function(val) {
    if (this.checked) {
        // Build selection.


    } else {
        // Build deselection.

    }
});