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
var DeleteField = function (id) {
    $.ajax({
        type: 'DELETE',
        url: '/fields/' + id,
        async: true,
        success: function (result) {
            if (result == 'success') {
                //success();
                var row = $('#' + id);
                row.hide();
                $('#datatable').dataTable().fnDeleteRow(row, null, true);
            }
            else
                deleteFailed();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            //alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
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