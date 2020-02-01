<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>
<#if known>
<#assign
user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
name = user.getUsername()
isAdmin = user.getAuthorities()?seq_contains('ADMIN')
isLogin = true
>
<#else>
<#assign
name = "Not logged"
isAdmin = false
isLogin = false
>
</#if>