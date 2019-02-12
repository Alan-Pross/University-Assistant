const { $Toast } = require('../../dist/base/index');
Page({
  data: {
    a3getimg: "/a3getimg?openid=",
    a4getimg: "/a4getimg?openid=",
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
      wx.navigateTo({
        url: '../a3/a3?id=' + self.data.id
      })
    }, function() {
      $Toast.hide()
      $Toast({
        content: '连接服务器失败，请留言反馈给我们',
        type: 'error'
      });
    })
  },
  a4: function () {
    var self = this
    $Toast({
      content: '加载中',
      type: 'loading',
      duration: 0
    });
    var network = require("../../tools/network.js")
    network.getrequest(this.data.a4getimg + this.data.id, null, function (res) {
      $Toast.hide()
      console.log(res)
      wx.navigateTo({
        url: '../a4/a4?id=' + self.data.id
      })
    }, function () {
      $Toast.hide()
      $Toast({
        content: '连接服务器失败，请留言反馈给我们',
        type: 'error'
      });
    })
  },
  a5: function () {
    wx.navigateTo({
      url: '../a5/a5'
    })
  },
  onLoad: function(option) {
    this.setData({
      id: option.id
    })
  },
})