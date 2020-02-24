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
                download(data, 'Detail_Balance.json', "text/plain");
            })
        });
    });

    function download(data, filename, type) {
    var file = new Blob([JSON.stringify(data, null, 2)], {type: type});
    if (window.navigator.msSaveOrOpenBlob) // IE10+
        window.navigator.msSaveOrOpenBlob(file, filename);
    else { // Others
        var a = document.createElement("a"),
                url = URL.createObjectURL(file);
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        setTimeout(function() {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        }, 0);
    }
}
</script>

<button style="display:none;">Debug button</button>

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