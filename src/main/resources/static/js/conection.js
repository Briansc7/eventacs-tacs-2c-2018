var conection = (function() {

    var keyWordSearch;
    var startDateSearch;
    var endDateSearch;
    var categoriesSearch;

    function init(keyWordValue, startDateValue, endDateValue, categoriesValue) {
        keyWordSearch = keyWordValue;
        startDateSearch = startDateValue;
        endDateSearch = endDateValue;
        categoriesSearch = categoriesValue;
    }


    function searchEvents() {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/eventacs/events",
            data: {
                keyWord: keyWordSearch.val(),
                startDate: startDateSearch.val(),
                endDate: endDateSearch.val(),
                categories: categoriesSearch.val(),
            },
            success: function(result) {
                $("#cluster-result").html(result);
            }

        });
    }

    return {
        init: init(),
        searchEvents: searchEvents()
    }

})()