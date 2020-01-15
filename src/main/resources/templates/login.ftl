<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
${message?ifExists}
    <#if error == true>
        <div class="alert alert-danger" role="alert">
            Incorrect login or password. Please check you details)
        </div>
    </#if>
<@l.login "/login" false/>
</@c.page>