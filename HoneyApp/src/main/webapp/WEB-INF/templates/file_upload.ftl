<#import "spring.ftl" as spring />
<html lang="en" contentType="text/html; charset=UTF-8">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
    <title>Document</title>
    <script>
        function sendFile() {
            // данные для отправки
            let formData = new FormData();
            // забрал файл из input
            let files = ($('#file'))[0]['files'];
            // добавляю файл в formData
            [].forEach.call(files, function (file, i, files) {
                formData.append("file", file);
            });
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/files",
                data: formData,
                processData: false,
                contentType: false
            })
                .done(function () {
                    alert("file link in your email")
                })
                .fail(function () {
                    alert('alert')
                });
        }
    </script>
</head>
<style type="text/css">
    A {
        text-decoration: none;
    }
</style>
<body>
<div>
    <input type="file" id="file" name="file" placeholder="<@spring.message 'confirm.confirm'/>"/>
    <button onclick="sendFile()">
        <@spring.message 'files.upload.upload'/>
    </button>
    <input type="hidden" id="file_hidden">
    <div class="filename"></div>
</div>

<#if check??>
    ${check}
</#if>
<br>
<button><a href="http://localhost:8080/honey/" text-decoration="none" class="nubex"><@spring.message 'confirm.confirm.honey'/></a></button>
</body>
</html>