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
$(document).ajaxComplete(function() {
    spinner.stop();
    spinner.stopped = true;
});
$(document).ajaxError(function(event, jqxhr, settings, thrownError) {
    console.error('Ajax error: ' + thrownError);
});


/*
---------------------------------------------------------------

DATA ACCESS.

---------------------------------------------------------------
*/

function getAllBuilds(callback) { $.get(url('builds'), callback); }

function getBuildNumbers(build, callback) { $.get(url('builds' + build), callback); }

function getEnvironments(callback) { $.get(url('environments'), callback); }


/*
---------------------------------------------------------------

DOM BINDINGS.

---------------------------------------------------------------
*/

getAllBuilds(function(builds) {
    $('#builds-wrapper tbody').append(builds);
    $('select').hide();
 });

$('body').on('change', '.build-cb', function(val) {

    var numbersSelector = '#' + $(this).attr('id') + '-numbers';
    var environmentsSelector = '#' + $(this).attr('id') + '-environments';
    var allSelector = numbersSelector + ',' + environmentsSelector;

    $(allSelector).prop('disabled', !this.checked);
    $(allSelector).show();

    if (this.checked) {

        // Loading build  numbers.
        getBuildNumbers(this.value, function(buildNumbers) {
            $(numbersSelector).append($('<option>', {
                value: '',
                text : 'Latest'
            }));
            $.each(buildNumbers, function (i, buildNumber) {
                $(numbersSelector).append($('<option>', {
                    value: buildNumber.number,
                    text : buildNumber.number
                }));
            });
        });

        // Loading available environments.
        getEnvironments(function(environments) {
            $.each(environments, function (i, env) {
                $(environmentsSelector).append($('<option>', {
                    value: env,
                    text : env
                }));
            });
        });

    } else {
        // Build deselection.
        $(allSelector).html('');
        $(allSelector).hide();
    }
});

$('body').on('click', '.deploy-button', function() {
    console.log('Deploying...')
});