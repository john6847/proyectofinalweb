<form action="/agregar/${articulo.getId()}/comentario/">
    <div class="row">
        <div class="col-md-offset-5 col-md-5">
            <div class=" form-group">
                <textarea type="text" class="form-control" name="comentario" placeholder="Agrega tu comentario" id="comment" aria-describedby="sizing-addon1" rows="1"></textarea>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-offset-5 col-md-5">
            <button class="btn btn-lg btn-primary btn-block btn-signin form-control" id="IngresoLog" type="submit" >Agregar</button>
        </div>
    </div>
</form>