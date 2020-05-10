function sendMessage(owner, content) {
    let body;
    body = {
        owner: owner,
        content: content
    };

    $.ajax({
        url: "/messages",
        method: "POST",
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        complete: function () {
            receiveMessage(owner)
        }
    });
}

function receiveMessage(owner) {
    $.ajax({
        url: "/messages?owner=" + owner,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            var msg = document.getElementById('box');
            var own = response[0]['owner'];
            var cnt = response[0]['content'];
            var date = response[0]['date'];
            if (owner === own) {
                msg.innerHTML += '<div class="bubbleWrapper">' +
                    '<div class="inlineContainer own">' +
                    '<div class="ownBubble own">' +
                    '<strong>' + own + '</strong>' +
                    '<br>' +
                    cnt +
                    '</div>' +
                    '</div>' +
                    '<span class="own">' + date + '</span>' +
                    '</div>';
            } else {
                msg.innerHTML += '<div class="bubbleWrapper">' +
                    '<div class="inlineContainer">' +
                    '<div class="otherBubble other">' +
                    '<strong>' + own + '</strong>' +
                    '<br>' +
                    cnt +
                    '</div>' +
                    '</div>' +
                    '<span class="other">' + date + '</span>' +
                    '</div>';
            }
            receiveMessage(owner);
        }
    })
}