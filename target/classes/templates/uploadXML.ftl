<#import "parts/common.ftl" as c>

<@c.page>
    <h3>You can upload goods to xml file. Which categories are requires for uploading?</h3>
    <form  method="post" action="/uploadXML">
        <div class="form-group row">
            <div class="col-sm-4">
                <select type="text" name="category" class="form-control">
                    <option type="text" value = "1"></option>
                    <option type="text" value = "1">Category 1</option>
                    <option type="text" value = "2">Category 2</option>
                    <option type="text" value = "3">Category 3</option>
                    <option type="text" value = "4">Category 4</option>
                    <option type="text" value = "5">All categories</option>
                </select>
            </div>
        </div>

        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-primary">Get XML file</button>
    </form>

    <div class = "my-3">
        <textarea type = "text" style="overflow: auto" name="result" cols="100" rows="12" value="${result}"></textarea>
    </div>
</@c.page>