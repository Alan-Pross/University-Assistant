const app = getApp();

Page({
  data: {
    percent: 0
  },

  onLoad() {
    this.goProgress()
  },

  onShareAppMessage() {
    return {
      title: '江大云助手',
      desc: '江大云助手',
      path: 'pages/index/index',
    };
  },
  goProgress: function () {
    var self = this;
    var network = require("../../tools/network.js")
    network.getrequest('/noticebar', null, function (res) {
      console.log(res)
      self.setData({
        bar: res.msg
      });
    }, function () {
    })
    self.setData({
      percent: self.data.percent + 10
    });
    setInterval(function () {
      if (self.data.percent === 100) return;
      self.setData({
        percent: self.data.percent + 10
      });
      if (self.data.percent === 100) {
        self.setData({
          status: 'success'
        },
          setTimeout(function () {
            if (typeof bar == "undefined" || bar == null || bar == '') {
              self.setData({
                bar: '欢迎进入江大云助手！'
              });
            }
            my.redirectTo({
              url: '../second/second?id='
            })
          }, 100)
        );
      }
    }, 200)
  }
});
