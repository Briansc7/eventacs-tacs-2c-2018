$(document).ready(function () {

    var keyWord = $('#keyWord');
    var startDate = $('#startDate');
    var endDate = $('#endDate');
    var categories = $('#categories');
    var searchButton = $('#searchButton');

    validaciones.init(keyWord, startDate, endDate, categories);
    conection.init(keyWord, startDate, endDate, categories);

    searchButton.on('click', validaciones.validateAll)
});