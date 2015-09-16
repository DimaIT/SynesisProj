// todo refactor
var AjaxDelete = function (tableName, id, urlSuffix, inactivate) {
    var deleteLink = 'delete';
    if (!!inactivate) deleteLink = 'inactivate';
    $.ajax({
        type: 'DELETE',
        url: '/table/' + tableName + '/' + deleteLink + '/' + id + '/' + urlSuffix,
        dataType: 'json',
        async: true,
        success: function (result) {
            if (result) {
                success();
                var row = $('#' + id);
                row.hide();
                $('#datatable').dataTable().fnDeleteRow(row, null, true);
            }
            else
                deleteFailed();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
};
var AjaxUpdateWorkDate = function (workDate) {
    $.ajax({
        type: 'POST',
        url: '/updateWorkDate/' + workDate,
        dataType: 'json',
        async: true
    });
    setTimeout(function () {
        location.reload()
    }, 200);
};
var message = function (type, message) {
    $("#message-container").html('<div class="alert alert-' + type + '"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>' + message + '</div>')
};
var success = function () {
    message('success', 'Операция завершена успешно')
};
var error = function () {
    message('danger', 'Произошла ошибка')
};
var deleteFailed = function () {
    message('danger', 'Произошла ошибка, возможно запись используется в другой таблице')
};

var MenuTransition = function () {
    var flag = 1;
    $('.slide').on('click', function () {
        $(this).find(".glyphicon").toggleClass("glyphicon-chevron-right");
        $(this).find(".glyphicon").toggleClass("glyphicon-chevron-left");
        $(this).toggleClass("active");
        if (flag == 0) {
            flag = 1;
            $(this).parent().parent().find("#menu").animate({"width": "+=265px"}, "slow");
            $(this).parent().parent().find(".left").animate({"min-width": "+=295px"}, "slow");
            return false;
        }
        if (flag == 1) {
            flag = 0;
            $(this).parent().parent().find("#menu").animate({"width": "-=265px"}, "slow");
            $(this).parent().parent().find(".left").animate({"min-width": "-=295px"}, "slow");
            $(this).toggleClass("active");
            return false;
        }
    });
};