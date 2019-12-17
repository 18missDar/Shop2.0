<#import "parts/common.ftl" as c>

<@c.page>
<form name = "good"  method="post" enctype="multipart/form-data" action="/updateGood">
    <div class="form-group row">
       <label class="col-sm-2 col-form-label">Title</label>
       <div class="col-sm-4">
       <input type="text" name="title" class="form-control" value="${good.title}">
      </div>
    </div>
    <div class="form-group row">
       <label class="col-sm-2 col-form-label">Description</label>
        <div class="col-sm-4">
       <input type="text" name="description" class="form-control" value="${good.description}">
        </div>
     </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Cost</label>
        <div class="col-sm-4">
       <input type="text" name="cost" class="form-control" value="${good.cost}">
       </div>
     </div>
    <div class="form-group row">
         <label class="col-sm-2 col-form-label">Category</label>
         <div class="col-sm-4">
        <input type="text" name="category" class="form-control" value="${good.category}">
        </div>
    </div>
     <div class="form-group">
        <label class="col-sm-2 col-form-label">Upload file</label>
        <div class="col-sm-4">
         <div class="custom-file">
         <input type="file" name="file" id="customFile">
         <label class="custom-file-label" for="customFile">Choose file</label>
        </div>
        </div>
     </div>
    <input type="hidden" name="id" value="${good.id}">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit" class="btn btn-primary">Save</button>
</form>
</@c.page>