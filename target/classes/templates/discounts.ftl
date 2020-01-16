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
             <select type="text" name="category" class="form-control">
                 <option type="text" value = "1">Category 1</option>
                 <option type="text" value = "2">Category 2</option>
                 <option type="text" value = "3">Category 3</option>
                 <option type="text" value = "4">Category 4</option>
             </select>
        </div>
    </div>
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit" class="btn btn-primary">Make discount</button>
</form>
</@c.page>