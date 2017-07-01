<#include "header.ftl">
<#include "nav.ftl">

<div class="container paste-bin">
    
    <div class="panel panel-primary" style="width:850px; margin-right: 20px">
      <div class="panel-heading">Copy and Paste</div>
      <div class="panel-body">
        
            <form action="/paste/" method="post">
               <div class="row">
                    <div class="col-md-offset col-md-7">
                           <div class="form-group">
                               <label for="title">Title</label>
                               <input type="text" class="form-control" name="title" id="title" placeholder="Enter the Paste title here">
                           </div>
                            <div class="form-group">
                                  <label for="bloqueDeTexto">New Paste</label>
                                  <textarea class="form-control" name="bloqueDeTexto" id="bloqueDeTexto" cols="50" rows="20">
                                  </textarea>
                            </div>
                     </div>
                        <div class="col-md-5">
                             <div class="form-group" style="margin-top:77px">
                                  <label for="syntax">Syntax</label>
                                   <select class="form-control" name="syntax" id="syntax">
                                       <option value=" " disabled>Select One</option>
                                       <option value="">Apache</option>
                                       <option value="">Bash</option>
                                       <option value="">C#</option>
                                       <option value="">C++</option>
                                       <option value="">CSS</option>
                                       <option value="">CoffeeScript</option>
                                       <option value="">Diff</option>
                                       <option value="">HTML</option>
                                       <option value="">XML</option>
                                       <option value="">HTTP</option>
                                       <option value="">Ini</option>
                                       <option value="">JSON</option>
                                       <option value="">JAVA</option>
                                       <option value="">JavaScript</option>
                                       <option value="">Makefile</option>
                                       <option value="">Markdown</option>
                                       <option value="">Ngnix</option>
                                       <option value="">Objective-C</option>
                                       <option value="">PHP</option>
                                       <option value="">Perl</option>
                                       <option value="">Python</option>
                                       <option value="">Ruby</option>
                                       <option value="">SQL</option>
                                       <option value="">Shell</option>
                                       <option value="">Session</option>
                                       <option value="">Arduino</option>
                                       <option value="">ARM Assembler</option>
                                       <option value="">Clojure</option>
                                       <option value="">Django</option>
                                       <option value="">Excel</option>
                                       <option value="">F#</option>
                                       <option value="">Go</option>
                                       <option value="">Haskell</option>
                                       <option value="">Groovy</option>
                                       <option value="">R</option>
                                       <option value="">SML</option>
                                       <option value="">Swift</option>
                                       <option value="">VB.NET</option>
                                       <option value="">YAML</option>

                                   </select>
                             </div>
                            <div class="form-group">
                                <label for="expirationDate">Expiration Date</label>
                                <select class="form-control" name="expirationDate" id="expirationDate">
                                    <option value=" " disabled>
                                        Select One
                                    </option>
                                    <option value="">never</option>
                                    <option value="">10 minutes</option>
                                    <option value="">15 minutes</option>
                                    <option value="">30 minutes</option>
                                    <option value="">1 hour</option>
                                    <option value="">1 day</option>
                                    <option value="">1 week</option>
                                </select>
                            <div class="form-group">
                               <label for="expositionType">Exposition Type</label>
                                <select class="form-control" name="expositionType" id="expositionType">
                                       <option value=" " disabled>
                                       Select One
                                       </option>
                                    <option value="">public</option>
                                    <option value="">private</option>
                                    <option value="">unlisted</option>
                                   </select>
                            </div>
                            <div class="row">
                                <div class="col-md-offset-6 col-md-6">
                                <div class="form-group" style="margin-top:190px">
                                <button class="btn btn-primary form-control" type="submit">Create New Paste</button>
                            </div>
                                </div>
                            </div>
                        </div>
                      
                </div>
            </form>
      </div>
    </div>
          <div class="panel panel-primary" style="width: 300px">
              <div class="panel-heading">
                  <a href="/paste/show/list">
                      <h4 class="public" style="text-decoration: none; color: white">Public</h4>
                  </a>
              </div>
              <div class="panel-body">

              </div>
    </div>
</div>
<#include "footer.ftl">