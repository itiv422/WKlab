var resultPage = 0;
var readPage = 0;
var readBook = '';

var saved_creator_filters;
var saved_date_filters;

function POST(data, scs, url) {
    $.ajax({
        url: url,
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: scs
    });
}

function addResult(result) {
    let elem = $("<li class='collection-item'><h3>" + result.title +
        "</h3><h4>" + result.creator + "</h4><h5>" + result.date + "</h5></li>");
    let downloadLink = $("<a>Download</a>");
    downloadLink.attr("href", 'books/' + result.file.replace('#','%23'));
    elem.append(downloadLink);
    elem.append("<span> </span>");
    let readLink = $("<a href='#'>Read</a>");
    readBook = result.file;
    readLink.attr("onclick", 'getBookPage("' + readBook + '",0);return false;');
    elem.append(readLink);
    $("#results").append(elem);
}

function addFilter(filter, key) {
    let elem = $("<li class='collection-item'></li>");
    elem.append("<h4 class='center'>" + key + "</h4>");
    for(let i = 0; i < filter.length; i += 2){
        var ch = (saved_creator_filters.includes(filter[i]) || saved_date_filters.includes(filter[i])) ? 'checked' : ''
        elem.append("<p><label><input class =" + key + " " + ch + " value = '" + filter[i] +
            "' type='checkbox'/><span>" + filter[i] + " (" + filter[i + 1] + ")</span></label></p>");
    }
    $("#facets").append(elem);
}

function loadResultPage(page){
    resultPage = page;
    var dates = [];
    var creators = [];
    $(".date:checkbox:checked").each(function() {
        dates.push($(this).val());
    });
    $(".creator:checkbox:checked").each(function() {
        creators.push($(this).val());
    });
    POST({
            search: $("#search").val(),
            dates: dates,
            creators: creators,
            page: resultPage
        }, renderResult, "search");
}

function addPaging(numOfResults) {
    $("ul.pagination").empty();
    const pages = Math.ceil(numOfResults / 10);
    for(var i = 0; i < pages; i++){
        var cl = 'waves-effect'
        if(i == resultPage){
            cl = 'active';
        } 
        $("#resultPages").append("<li class=" + cl +"><a href='#' onclick='loadResultPage(" + i + ");return false;'>" + (i + 1) + "</a></li>");
    }
}

const renderResult = function(results) {
    console.log(results);
    $("ul.collection").empty();
    if(results.response.numFound != undefined){
        numberOfResults = results.response.numFound;
        $("#numberOfResults").text("Number of results: " + numberOfResults);
        $("#numberOfResults").removeClass("hide");
        addPaging(numberOfResults)
    } else {
        $("#numberOfResults").addClass("hide");
    }
    const resultSet = results.response.docs;
    if(resultSet.length > 0){
        $(".hideSection").removeClass("hide");
        for(let result of resultSet) {
            addResult(result);
        }
        for(let key of Object.keys(results.facet_counts.facet_fields)) {
            const filterElements = results.facet_counts.facet_fields[key];
            if(filterElements.length > 0 ) {
                addFilter(filterElements, key);
            }
        }
        } else {
            $(".hideSection").addClass("hide");
        } 
}

function renderSearch() {
    resultPage = 0;
    POST({search: $("#search").val(), page: resultPage}, renderResult, "search");
}

function renderSuperSearch() {
    resultPage = 0;
    POST({search: $("#search").val(), page: resultPage}, renderResult, "superSearch");
}

$("#facetBtn").on("click",function() {
    resultPage = 0;
    var dates = [];
    var creators = [];
    $(".date:checkbox:checked").each(function() {
        dates.push($(this).val());
    });
    $(".creator:checkbox:checked").each(function() {
        creators.push($(this).val());
    });
    saved_creator_filters = creators;
    saved_date_filters = dates;
    POST({
            search: $("#search").val(),
            dates: dates,
            creators: creators,
            page: resultPage
        }, renderResult, "search");
});

renderBookPage = function(page) {
    $("#pageContent").text(page.text);
    readPage = page.page;
    if(readPage > 0) {
        let prevPage = $("<a href='#'>Previous</a>");
        prevPage.attr("onclick", 'getBookPage("' + readBook + '",' + (readPage - 1) + ');return false;');
        $("#pageContentFooter").append(prevPage);
    }
    $("#pageContentFooter").append("<span> Current page: " + (page.page + 1) + "(" + page.pages + ") </span>");
    if(readPage != page.pages) {
        let nextPage = $("<a href='#'>Next</a>");
        nextPage.attr("onclick", 'getBookPage("' + readBook + '",' + (readPage + 1) + ');return false;');
        $("#pageContentFooter").append(nextPage);
    }
    $('#modalBook').modal('open');
}

function getBookPage(file, page){
    $("#pageContent").empty();
    $("#pageContentFooter").empty();
    POST({
            file: file,
            page: page
        },
        renderBookPage, "page");
}

$("#searchBtn").on("click",function() {
    saved_creator_filters = [];
    saved_date_filters = [];
    renderSearch();
});

// TODO Method
$("#superSearchBtn").on("click",function() {
    saved_creator_filters = [];
    saved_date_filters = [];
    renderSearch();
});