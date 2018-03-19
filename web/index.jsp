<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">

    <title>URL Shortener</title>

    <link href="css/index.css" rel="stylesheet">

    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/main.js"></script>
    <script>
        $(document).ready(function() {
            main();
        });
    </script>
</head>

<body>

    <header>
        <h2>URL Shortener</h2>
    </header>

    <section id="main">

        <p id="title">Shorten your URL</p>

        <section id="shorten">
            <div class="input-container">
                <label for="url-to-shorten"></label>
                <input id="url-to-shorten" class="url" type="text" name="url">
            </div>
            <a class="submit" href="javascript:void(0)">SHORTEN</a>
        </section>


        <div id="generate-url">
            <a href=""></a>
        </div>

    </section>

</body>

</html>
