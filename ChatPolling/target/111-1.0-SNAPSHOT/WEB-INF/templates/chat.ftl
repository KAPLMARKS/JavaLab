<#macro head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>

    <title>Chat</title>

    <script>
        function sendMessage(pageId, text) {
            let body = {
                pageId: pageId,
                text: text,
                userId: pageId
            };

            $.ajax({
                url: "/messages",
                method: "POST",
                data: JSON.stringify(body),
                contentType: "application/json",
                dataType: "json",
                complete: function () {
                    if (text === 'New user: ${user.getUsername()}') {
                        receiveMessage(pageId)
                    }
                }
            });
        }
    </script>
    <script>
        function receiveMessage(pageId) {
            $.ajax({
                url: "/messages?pageId=" + pageId,
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    response.forEach(function (element,index) {
                        $('#messages').first().after('<li>' + 'userId:' + element['pageId'] + ' <b>'
                            + element['text'] + '</b></li>');
                    })
                    receiveMessage(pageId);
                }
            })
        }
    </script>
</#macro>
<#macro content>
<body onload="sendMessage('${pageId}', 'New user: ${user.getUsername()}')">
<h1>your identicator- ${pageId}</h1>
<div>
    <input id="message" placeholder="Write message...">
    <button onclick="sendMessage('${pageId}',
            $('#message').val())">send</button>
</div>
<div>
    <ul id="messages">

    </ul>
</div>
</#macro>
<@main/>