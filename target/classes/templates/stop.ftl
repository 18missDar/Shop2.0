<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>

<h1>Warning</h1>
<form action="/stop" method="post">
    <div class="form-group row">
       <div class="col-sm-4">
       <p class="lead"> You can't delete this good, because it in user's card. May be do you want to deactive this good?
       </p>
      </div>
    </div>
    <input type="hidden" value="${goodi}" name="goodi">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit" class="btn btn-primary">Deactive</button>
</form>

</@c.page>