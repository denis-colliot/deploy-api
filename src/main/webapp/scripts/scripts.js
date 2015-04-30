/*
---------------------------------------------------------------

AJAX CONFIGURATION.

---------------------------------------------------------------
*/

var opts = {
  lines: 12, // The number of lines to draw
  length: 12, // The length of each line
  width: 6, // The line thickness
  radius: 16, // The radius of the inner circle
  corners: 1, // Corner roundness (0..1)
  rotate: 0, // The rotation offset
  direction: 1, // 1: clockwise, -1: counterclockwise
  color: '#fff', // #rgb or #rrggbb or array of colors
  speed: 1, // Rounds per second
  trail: 60, // Afterglow percentage
  shadow: false, // Whether to render a shadow
  hwaccel: false, // Whether to use hardware acceleration
  className: 'spinner', // The CSS class to assign to the spinner
  zIndex: 2e9, // The z-index (defaults to 2000000000)
  top: '50%', // Top position relative to parent
  left: '50%' // Left position relative to parent
};
var target = document.getElementById('ajaxLoader');
var spinner = new Spinner(opts).spin();

$(document).ajaxStart(function() {
    spinner.spin();
    target.appendChild(spinner.el);
    spinner.stopped = false;
});
$(document).ajaxStop(function() {
    spinner.stop();
    spinner.stopped = true;
});


/*
---------------------------------------------------------------

DATA ACCESS.

---------------------------------------------------------------
*/

function getAllBuilds(callback) { $.get(url('builds'), callback); }

function getBuildNumbers(build, callback) { $.get(url('builds' + build), callback); }


/*
---------------------------------------------------------------

DOM BINDINGS.

---------------------------------------------------------------
*/

getAllBuilds(function(data) {
    $('#builds-wrapper .content').html(data);
 });

$('body').on('change', '.build-cb', function(val) {
    var selector = '#' + $(this).attr('id') + '-numbers';

    $(selector).prop('disabled', !this.checked);

    if (this.checked) {
        // Build selection.
        getBuildNumbers(this.value, function(buildNumbers) {
            $(selector).append($('<option>', {
                value: '',
                text : 'Latest'
            }));
            $.each(buildNumbers, function (i, buildNumber) {
                $(selector).append($('<option>', {
                    value: buildNumber.number,
                    text : buildNumber.number
                }));
            });
        });

    } else {
        // Build deselection.
        $(selector).html('');
    }
});

$('body').on('click', '.deploy-button', function() {
    console.log('Deploying...')
});