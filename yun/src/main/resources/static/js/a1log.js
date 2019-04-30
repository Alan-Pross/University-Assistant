window.onload = function () {
    var httpRequest = new XMLHttpRequest();
    var add = document.getElementById('add');
    httpRequest.open('GET', './a1log?qsh=' + GetQueryString('qsh'), true);
    httpRequest.send();
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState == 4 && httpRequest.status == 200) {
            add.innerHTML = "";
            try {
                var log = JSON.parse(httpRequest.responseText);
                document.getElementById('qsh').innerHTML = log[0].qsh + "　";
                for (var i = 0; i < log.length; i++) {
                    var time = log[i].time;
                    var rate = log[i].rate;
                    add.innerHTML += '<div class="am-panel am-panel-primary" style="text-align: center"><div class="am-panel-hd">'+ time +'</div><div class="am-panel-bd">'+ rate +'</div></div>';
                }
            }
            catch(err){
                print(httpRequest.responseText);
            }
        }
        else{
            var qsh = GetQueryString('qsh');
            if(qsh.charAt(0) == 'B'){
                qsh = "北" + qsh.substring(3);
            }
            else if(qsh.charAt(0) == 'N'){
                qsh = "南" + qsh.substring(3);
            }
            document.getElementById('qsh').innerHTML = qsh + "　";
            add.innerHTML = '<div class="am-panel am-panel-primary" style="text-align: center"><div class="am-panel-hd">查询失败</div><div class="am-panel-bd">没有记录</div></div>';
        }
    };
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return "";
}

function print(str) {
    document.getElementById('add').innerHTML += str;
}