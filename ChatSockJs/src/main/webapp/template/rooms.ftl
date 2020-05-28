<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Chat</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <script>
        function getRooms() {
            $.ajax({
                url: "/api/rooms",
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    $.each(response, function () {
                        $('#roomsList').first().append('<a href="/chat?room='+this.id+'">' + this.name + '</a>');
                    });
                }
            });
        }
    </script>
</head>
<body onload="getRooms()">
<div>
    <h3>Rooms</h3>
    <ul id="roomsList"></ul>
</div>
</body>
</html>
