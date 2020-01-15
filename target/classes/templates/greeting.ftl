<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
    <#if name =="guest">
        <h5>Hello, guest</h5>
    <#else>
        <h5>Hello, ${name}</h5>
    </#if>
    <div>This is a simple online shop</div>
</@c.page>