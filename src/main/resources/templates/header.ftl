<!DOCTYPE html>
<html lang="en">
<head>
    <title>${titulo}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <link rel="stylesheet" type="text/css" href="/publico/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/ublico/css/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="/publico/css/miEstilo.css">
    <link rel="stylesheet" type="text/css" href="/publico/css/style.css">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">

    <script src="/publico/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <script src="/publico/js/jquery-ui.min.js" type="text/javascript"></script>
    <script src="/publico/js/bootstrap.js" type="text/javascript"></script>


</head>
<body >
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Copy & Paste</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/">Home <span class="sr-only">(current)</span></a></li>
                <li class="dropdown">
                    <a href="" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Paste <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/add/newPaste">Add new Paste</a></li>
                    </ul>
                </li>
            </ul>
            <form class="navbar-form navbar-left">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">Search</button>
            </form>
    
           
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/login">Login</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">User <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/signIn">Sign up</a></li>
                        <li><a href="/signUp">Sign in </a></li>
                    </ul>
                </li>
            </ul>
        <ul class="nav navbar-nav navbar-right">
            <li> <a href="/list/myPaste"><img src="/publico/images/list.png" title="profile" alt=""></a>
            </li>
            <li><a href="/update/myPaste"><img src="/publico/images/settings.png" title="my paste" alt=""></a>
            </li>

        </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


</body>
</html>

