
<#import "parts/common.ftl" as c>

<@c.page>
<div class="row">

  <div class="col-3">
   <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
        <form method="get" action="/main/category" class="form-inline">
         <input type="hidden" name="category" class="form-control" value="${1}">
         <button type="submit" class="btn btn-primary ml-4 mt-1">Category 1</button>
         </form>
         <form method="get" action="/main/category" class="form-inline">
         <input type="hidden" name="category" class="form-control" value="${2}">
               <button type="submit" class="btn btn-primary ml-4 mt-1">Category 2</button>
          </form>
         <form method="get" action="/main/category" class="form-inline">
         <input type="hidden" name="category" class="form-control" value="${3}">
                <button type="submit" class="btn btn-primary ml-4 mt-1">Category 3</button>
          </form>
         <form method="get" action="/main/category" class="form-inline">
          <input type="hidden" name="category" class="form-control" value="${4}">
                <button type="submit" class="btn btn-primary ml-4 mt-1">Category 4</button>
         </form>
       </div>
  </div>

  <div class="col-9">
      <div class="card-columns">
          <#list messages as message>
              <div class="card my-3 h-100">
                  <h3 class="card-title">${message.title}</h3>
                  <#if message.filename??>
                      <img src=${message.filename} class="card-img-top">
                  </#if>

                  <div class="card-footer text-muted">
                      <p> Description: ${message.description} </p>
                      <p> Cost: ${message.cost}</p>
                      <div class="form-group">
                          <#if isAdmin>
                              <a href="/delete/${message.id}" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Delete</a>
                              <a href="/update/${message.id}" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Update</a>
                          <#else>
                              <a href="/addc/${message.id}" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Add to cart</a>
                          </#if>
                      </div>
                  </div>
              </div>
          <#else>
              No message
          </#list>
      </div>

    </div>
  </div>
</div>
</@c.page>