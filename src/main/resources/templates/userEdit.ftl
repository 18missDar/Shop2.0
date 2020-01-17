<#import "parts/common.ftl" as c>

<@c.page>
    User editor

    <form action="/user" method="post">
        <div class="form-group row">
            <div class="col-sm-4">
                <input type="text" name="username" value="${user.username}">
            </div>
        </div>
        <#list roles as role>
            <div class="form-group row">
                <div class="col-sm-4">
                    <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
                </div>
            </div>

        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>