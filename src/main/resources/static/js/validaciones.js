var validaciones = (function () {
    const dateRegex = new RegExp("^([0-9]{4})[-](0[1-9]|1[0-2])[-](0[1-9]|(1|2)[0-9]|3[0-1])$");
    const url = "http://localhost:8080/search";

    var keyWord;
    var startDate;
    var endDate;
    var categories;

    function init(inputKeyWord, inputStart, inputEnd, inputCategories) {

        keyWord = inputKeyWord;
        startDate = inputStart;
        endDate = inputEnd;
        categories = inputCategories;
    }

    function validateAll(){

        var validStart = validateStartDate(startDate);
        var validEnd =  validateEndDate(endDate);
        var validCategories = validate(categories);

        if (validStart && validEnd && validCategories) {
            retrieveEvents(url);
        } else {
            console.log("Existing errors in input fields.")
        }
    }


    function validate(e) {
        var element = $(e);

        if (regexInput.test(element.val())) {
            element.removeClass('is-invalid');
            return true;
        } else {
            element.addClass('is-invalid');
            return false;
        }
    }

    function validateStartDate(e) {
        var valid = isValidDate(e);
        if (valid) {
            e.removeClass("is-invalid");
            return true;
        } else {
            e.text("Ingrese una fecha válida y mayor al día de hoy");
            e.addClass("is-invalid")
            return false;
        }
    }

    function validateEndDate(e) {
        var valid = dateRegex.test(e.val());
        var biggerDate = isBiggerThan(e.val(), startDate.val());
        if (!dateRegex.test(startDate.val())) biggerDate = true;

        if (biggerDate) {
            if (valid) {
                e.removeClass("is-invalid");
                return true;
            } else {
                e.text("Ingrese una fecha válida.");
                e.addClass("is-invalid");
                return false;
            }
        } else {
            endDate.text("Ingrese una fecha mayor a la de partida.")
            endDate.addClass("is-invalid")
        }
    }



    function isValidDate(input) {

        var value = $(input).val();

        var isValid = dateRegex.test(value);
        var todayCompleteDate = new Date();

        var inputDate = new Date(input.val().slice(0,4), input.val().slice(5,7) - 1, input.val().slice(8,10));

        var validationBiggerDate = isBiggerThan(inputDate, todayCompleteDate);

        if (validationBiggerDate) {
        } else {
            return false
        }

        if (!isValid)
            return false;
        return (validDay(endDate.val()) && validDay(startDate.val()))
    }

    function validDay(input) {
        var anArray = input.split("-");
        if (anArray[1] === "02") {
            if (esBisiesto(anArray[0])) {
                return anArray.pop(3) <= 29
            } else {
                return anArray.pop(3) <= 28
            }
        }
        return true
    }

    function esBisiesto(year) {
        return ((year % 400) === 0) || (((year % 4) === 0) && ((year % 100) !== 0))
    }

    function isBiggerThan(aValue, anotherValue) {

        return aValue > anotherValue
    }

    function retrieveEvents(url) {
        conection.searchEvents();
    }

    return {
        init: init,
        validateAll: validateAll()
    }


})();