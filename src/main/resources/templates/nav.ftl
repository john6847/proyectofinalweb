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
                <li class="active"><a href="/"><i class="glyphicon glyphicon-home"></i><span class="sr-only">(current)</span></a></li>
                <li class="dropdown">
                    <a href="" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Paste<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li> <a href="/">Add new Paste</a></li>
                        <li> <a href="/paste/show/list">Show Paste</a></li>
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
                <li> <a href="/user/show/paste"><img src="/images/list.png" title="my paste" alt=""></a></li>
                <li><a href="/user/update/profile"><img src="/images/settings.png" title="update profile" alt=""></a></li>
                <#if usuario??>
                    <li id="LoggedIn"><a  href="/user/update/profile">Welcome, <strong style="color: blue;">${usuario}</strong></a></li>
                <#else >
                    <li id="LoggedIn"><a id="logged" href="/user/signIn">Login</a></li>
                </#if>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">User<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <#if usuario??>
                            <li> <a href="/user/signOut">Sign Out</a></li>
                            <#else>
                                <li> <a href="/user/signIn">Sign in</a></li>
                        </#if>

                        <li> <a href="/user/signUp">Sign up</a></li>
                        <li> <a href="/user/listar/user">Create Admin</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
