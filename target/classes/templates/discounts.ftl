<#import "parts/common.ftl" as c>

<@c.page>
<form  method="post" action="/discounts">
    <div class="form-group row">
       <label class="col-sm-2 col-form-label">Discount %</label>
       <div class="col-sm-4">
       <input type="text" name="discount" class="form-control">
      </div>
    </div>
    <div class="form-group row">
         <label class="col-sm-2 col-form-label">Category</label>
         <div class="col-sm-4">
        <input type="text" name="category" class="form-control">
        </div>
    </div>
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit" class="btn btn-primary">Make discount</button>
</form>
</@c.page>