<#import "/parts/common.ftl" as c>
<@c.page>

<#import "/parts/pageTitle.ftl" as pt>
<@pt.pageTitle "Users"/>
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Roles</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <#list users as user>
                <tr>
                    <td>${user.username}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href="/user/${user.id}">edit</a></td>
                    <td>
                        <form method="post" style="display: inline-block" id="delete_user_form">
                            <button class="btn btn-primary rounded ml-2" type="submit" id="delete_button" formaction="/user/delete/${user.id}">Delete</button>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        </form>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
</@c.page>