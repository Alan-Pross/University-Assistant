const { $Toast } = require('../../dist/base/index');
Page({
  data: {
    a3getimg: "http://94.191.42.64:1234/xzs/a3getimg?openid=",
    id: ""
  },
  a1: function() {
    wx.navigateTo({
      url: '../a1/a1'
    })
  },
  a2: function() {
    wx.navigateTo({
      url: '../a2/a2'
    })
  },
  a3: function() {
    var self = this
    $Toast({
      content: '加载中',
      type: 'loading',
      duration: 0
    });
    var network = require("../../tools/network.js")
    network.getrequest(this.data.a3getimg + this.data.id, null, function(res) {
      $Toast.hide()
      console.log(res)
      if (res.code == '500') {
        $Toast({
          content: '未知错误，请反馈我们',
          type: 'warning',
        });
        return;
      }
      wx.navigateTo({
        url: '../a3/a3?id=' + self.data.id
      })
    }, function() {
      $Toast.hide()
      $Toast({
        content: '连接服务器失败',
        type: 'error'
      });
    })
  },
  a4: function () {
    wx.navigateTo({
      url: '../a4/a4'
    })
  },
  onLoad: function(option) {
    this.setData({
      id: option.id
    })
  },
})