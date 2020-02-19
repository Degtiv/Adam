<#import "/parts/common.ftl" as c>
<@c.page>
<#import "/parts/pageTitle.ftl" as pt>
<@pt.pageTitle "Overview"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
    $(document).ready(function(){
        $("button").click(function(){
            const data = {
              foo: {
                bar: [1, 2, 3]
              }
            };

            $.ajax({
                type: 'POST',
                url: "status",
                data: JSON.stringify(data),
                headers: {
                    "x-csrf-token": "${_csrf.token}",
                    "Content-Type": "application/json"
                },
                dataType: 'json'
            }).done(function(data) {
                console.log(data);
            })
        });
    });
</script>

<button>Search</button>

<#list balances as balance>
<div class="clearfix input-group-sm">

    <p>
        <u>${balance.dateString}</u>
        <b>${balance.amount}</b>
        ${balance.currency}
    </p>
</div>
</#list>
</@c.page>