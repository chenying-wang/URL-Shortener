const main = function () {
    $("#shorten .submit").click(function () {
        var url = $("#url-to-shorten").val();
        console.log("shorten..." + url);
        $.ajax({
            cache: false,
            type: "GET",
            url: "url_shorten.action",
            data: {
                url: url
            },
            dataType: "json",
            error: function(data) {
                console.log("fail" + data)
            },
            success: function(response) {
                $("#generate-url").html(
                    "Your Short URL:  " +
                    "<a href='" + response.url + "'>" + response.url + "</a>"
                );
            }
        });
    });
}