var AjaxDelete = function (id, url) {
    $.ajax({
        type: 'DELETE',
        url: '/' + url + '/' + id,
        async: true,
        success: function (result) {
            if (result == 'success')
                message('success', 'Operation finished successfully');
            else
                message('success', result);
            var row = $('#' + id);
            row.hide();
            $('#datatable').dataTable().fnDeleteRow(row, null, true);
        },
        error: function (xhr) {
            message('danger', xhr.responseText);
        }
    });
};
var ajaxSubmit = function (ev, successMessage) {
    var frm = $('#form-ajax-submit');
    if (frm[0].checkValidity()) {
        $.ajax({
            type: frm.attr('method'),
            url: frm.attr('action'),
            data: frm.serialize(),
            success: function (result) {
                if (result == 'success') {
                    frm[0].reset();
                    if (successMessage == undefined)
                        message('success', '@Messages("success.message")');
                    else
                        message('success', successMessage);
                } else {
                    if (result.indexOf("redirect:") == 0)
                        window.location.href = result.substring(9, result.length);
                    else
                        message('warning', result);
                }
            },
            error: function (xhr) {
                message('danger', xhr.responseText);
            }
        });
        ev.preventDefault();
    }
    else {
        message('warning', 'Please, enter all required fields')
    }
};
var message = function (type, message) {
    if (type == '') type = 'info';
    $("#message-container").html('<div class="alert alert-' + type + '"><button type="button" class="close" data-dismiss="alert">&times;</button>' + message + '</div>')
};