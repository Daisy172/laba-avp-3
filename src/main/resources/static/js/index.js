$(document).ready(function () {
    M.AutoInit();

    loadPage();
});

function loadPage () {
    const loadCircle = $('#fileLoading');
    const filesTable = $('#filesDiv');
    const uploadFileErrorContainer = $('#upload-error');

    filesTable.css('display', 'none');
    loadCircle.css('display', '');

    $.get({
        url: '/getFilesList',
        contentType: 'application/json',
        success: function (response) {
            $('#filesList').html('');

            if (response.items.length <= 0) {
                $('#no-files').css('display', '');
                uploadFileErrorContainer.css('display', 'none');
                filesTable.css('display', 'none');
                loadCircle.css('display', 'none');
                return;
            }

            response.items.forEach(function (item) {
                const fileElement = $(`
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.fileName}</td>
                        <td>${item.uploadDate}</td>
                        <td>${item.ownerId}</td>
                        <td>
                            <a href="/getFileById?file_id=${item.id}" download="${item.fileName}">Download</a>
                            <br>
                            <a style="cursor: pointer" onclick="deleteFile(${item.id})">Delete</a>
                        </td>
                    </tr>
                `);

                $('#filesList').append(fileElement);
            });

            loadCircle.css('display', 'none');
            filesTable.css('display', '');
            uploadFileErrorContainer.css('display', 'none');
        },
        error: function () {
            filesTable.css('display', 'none');
            loadCircle.css('display', 'none');
            uploadFileErrorContainer.css('display', 'unset');

            return M.toast({html: 'Load error'});
        }
    });
}

function addFile () {
    M.Modal.getInstance(document.getElementById('upload-modal')).open();
}

function deleteFile (fileId) {
    if (confirm('Are you sure want to delete file?')) {
        $.ajax({
            url: '/deleteFile?file_id=' + fileId,
            method: 'DELETE',
            success: function () {
                return window.location.reload();
            },
            error: function () {
                return M.toast({html: 'File deletion failed.'});
            }
        });
    }
}

function startUploading (modal, button, files) {
    if (files.length <= 0) {
        return M.toast({html: 'File not specified'});
    }

    button.setAttribute('disabled', 'true');
    button.innerText = 'Uploading...';

    const form = new FormData();
    form.append('file', files[0]);

    $.post({
        url: '/addFile',
        data: form,
        processData: false,
        contentType: false,
        success: function (response) {
            button.innerText = 'Upload';

            if (response.fileId) {
                M.toast({html: 'File uploaded.'});

                return setTimeout(function () {
                    return window.location.reload();
                }, 2000);
            } else {
                button.removeAttribute('disabled');

                return M.toast({html: 'File not uploaded.'})
            }
        },
        error: function () {
            button.removeAttribute('disabled');
            button.innerText = 'Upload';

            return M.toast({html: 'Connection failed.'});
        }
    });
}