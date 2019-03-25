window.onload = function fullScreen() {
    var el = document.documentElement;
    var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullscreen;
    if (typeof rfs != "undefined" && rfs) {
        rfs.call(el);
    }
    return;
}

function admin() {
    var root = document.getElementById("root").value;
    var pass = document.getElementById("pass").value;
    var key = document.getElementById("key").value;
    var data = "root=" + root + "&pass=" + pass + "&key=" + key;
    $.ajax({
        type: "POST",
        url: "/admin",
        data: data,
        dataType: "json",
        success: function (r) {
            var data = eval(r);
            if(data.is == "ok") {
                setCookie("key",data.key,1);
                window.location = "./manage.html";
            } else {
                window.alert(data);
            }
        }
    });
}

function bar() {
    var bar = document.getElementById("bar").value;
    var key = getCookie("key");
    var data = "bar=" + bar + "&key=" + key;
    $.ajax({
        type: "POST",
        url: "/changebar",
        data: data,
        dataType: "json",
        success: function (r) {
            var data = eval(r);
            var str = JSON.parse(data);
            window.alert(str);
        }
    });
}

function setCookie(c_name,value,expiredays)
{
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+expiredays)
    document.cookie=c_name+ "=" +escape(value)+
        ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}

function getCookie(c_name)
{
    if (document.cookie.length>0)
    {
        c_start=document.cookie.indexOf(c_name + "=")
        if (c_start!=-1)
        {
            c_start=c_start + c_name.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            return unescape(document.cookie.substring(c_start,c_end))
        }
    }
    return ""
}