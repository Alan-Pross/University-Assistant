    function showLocale(time) {
      var yy = time.getYear();
      if (yy < 1900) yy = yy + 1900;
      var MM = time.getMonth() + 1;
      if (MM < 10) MM = '0' + MM;
      var dd = time.getDate();
      if (dd < 10) dd = '0' + dd;
      var hh = time.getHours();
      if (hh < 10) hh = '0' + hh;
      var mm = time.getMinutes();
      if (mm < 10) mm = '0' + mm;
      var ss = time.getSeconds();
      if (ss < 10) ss = '0' + ss;
      var ww = time.getDay();
      if (ww == 0) ww = "星期日";
      if (ww == 1) ww = "星期一";
      if (ww == 2) ww = "星期二";
      if (ww == 3) ww = "星期三";
      if (ww == 4) ww = "星期四";
      if (ww == 5) ww = "星期五";
      if (ww == 6) ww = "星期六";
      str = yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss + " " + ww ;
      return (str);
    }
    function tick() {
      var now;
      now = new Date();
      document.getElementById("time").innerHTML = showLocale(now);
      window.setTimeout("tick()", 1000);
    }
    tick();