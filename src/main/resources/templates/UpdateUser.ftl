<#include "header.ftl">
<#include  "nav.ftl">
<div class="container">
    <h1 class="well">Actualizar Usuario</h1>

    <div class="panel panel-primary">

        <div class="panel-body">
            <form method="post" action="">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="nombre">Name:</label>
                            <input type="text" class="form-control" id="nombre" name="name" value="${usuario.getName()}"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" class="form-control" value="${usuario.getUsername()}">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" class="form-control" name="email">
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="occupation">Occupation</label>
                                <input type="text" class="form-control" id="occupation" class="form-control" name="occupation">
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Choose Perfil Icon</label>
                            <div style="border:1px solid lightgrey;border-radius: 3px;padding: 20px ">
                                <div class="prof ">
                                    <div class="radio-inline">
                                    <input type="radio" value="/images/png/boy.png" name="optradio" id="optradio"/>
                                    <label for="optradio"><img  class="profile-image img-rounded" src="/images/png/boy.png" alt=""></label>
                                    </div>

                                    <div class="radio-inline">
                                        <input  type="radio" value="/images/png/boy-1.png" name="optradio" id="optradio"/>
                                        <label for="optradio"><img  class="profile-image img-rounded" src="/images/png/boy-1.png" alt=""></label>
                                    </div>

                                    <div class="radio-inline">
                                        <input  type="radio" value="/images/png/girl.png" name="optradio" id="optradio"/>
                                        <label for="optradio"><img  class="profile-image img-rounded" src="/images/png/girl.png" alt=""></label>
                                    </div>
                                </div>
                                <div class="prof ">

                                    <div class="radio-inline">
                                        <input type="radio" value="/images/png/girl-1.png" name="optradio" id="optradio"/>
                                        <label for="optradio"><img  class="profile-image img-rounded" src="/images/png/girl-1.png" alt=""></label>
                                    </div>

                                    <div class="radio-inline">
                                        <input type="radio" value="/images/png/man.png" name="optradio" id="optradio"/>
                                        <label for="optradio"><img  class="profile-image img-rounded" src="/images/png/man.png" alt=""></label>
                                    </div>

                                    <div class="radio-inline">
                                        <input type="radio" value="/images/png/man-1.png" name="optradio" id="optradio"/>
                                        <label for="optradio"><img  class="profile-image img-rounded" src="/images/png/man-1.png" alt=""></label>
                                    </div>

                                </div>

                                <div class="prof">

                                    <div class="radio-inline">
                                        <input type="radio" value="/images/png/man-2.png" name="optradio" id="optradio"/>
                                        <label for="optradio"><img  class="profile-image img-rounded" src="/images/png/man-2.png" alt=""></label>
                                    </div>

                                    <div class="radio-inline">
                                        <input type="radio" value="/images/png/man-3.png" name="optradio" id="optradio"/>
                                        <label for="optradio"><img  class="profile-image img-rounded" src="/images/png/man-3.png" alt=""></label>
                                    </div>

                                    <div class="radio-inline">
                                        <input type="radio" value="/images/png/man-4.png" name="optradio" id="optradio"/>
                                        <label for="optradio"><img  class="profile-image img-rounded" src="/images/png/man-4.png" alt=""></label>
                                    </div>
                                    </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="phoneNumber">Phone Number</label>
                            <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="dateOfBirth"> Date of Birth</label>
                            <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <input type="reset" class="btn btn-primary form-control" value="Reset">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <input type="submit" class="btn btn-primary form-control" value="Update">
                        </div>
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>
</div>

<#include "footer.ftl">

