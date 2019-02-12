const { $Message } = require('../../dist/base/index');
const { $Toast } = require('../../dist/base/index');
Page({
  data: {
    a4result: 'http://94.191.42.64:1234/yzs/a4result',
    a4getimg: 'http://94.191.42.64:1234/yzs/a4getimg?openid=',
    yzmimg: '',
    openid: '',
    sfzh: '',
    xm: '',
    yzm: ''
  },
  sfzhsInput: function (event) {
    this.setData({
      sfzh: event.detail.detail.value
    })
  },
  xmsInput: function (event) {
    this.setData({
      xm: event.detail.detail.value
    })
  },
  yzmsInput: function (event) {
    this.setData({
      yzm: event.detail.detail.value
    })
  },
  handleClick: function (option) {
    var self = this;
    if (this.data.sfzh.length != 18) {
      $Message({
        content: '身份证号为18位',
        type: 'warning'
      });
      return;
    }
    if (this.data.xm.length < 2) {
      $Message({
        content: '请填写姓名完整',
        type: 'warning'
      });
      return;
    }
    if (this.data.yzm.length < 1) {
      $Message({
        content: '请输入验证码',
        type: 'warning'
      });
      return;
    }
    $Toast({
      content: '加载中',
      type: 'loading',
      duration: 0
    });
    var network = require('../../tools/network.js')
    network.getrequest(self.data.a4result, self.data, function (res) {
      $Toast.hide()
      console.log(res)
      if (res.error == '已超时或不存在') {
        $Toast({
          content: '已超时,请点击验证码刷新',
          type: 'warning',
        });
        return;
      }
      wx.redirectTo({
        url: '../a4result/a4result?url=' + res.url
      })
    }, function () {
      $Toast.hide()
      $Toast({
        content: '找回失败，请检查身份证号和验证码',
        type: 'error'
      });
    })
  },
  reyzm: function (option) {
    var self = this;
    $Toast({
      content: '加载中',
      type: 'loading',
      duration: 0
    });
    var network = require("../../tools/network.js")
    network.getrequest(self.data.a4getimg + self.data.openid, null, function (res) {
      $Toast.hide()
      console.log(res)
      wx.redirectTo({
        url: '../a4/a4?id=' + self.data.openid
      })
    }, function () {
      $Toast.hide()
      $Toast({
        content: '连接服务器失败，请留言反馈给我们',
        type: 'error'
      });
    })
  },
  onLoad: function (option) {
    this.setData({
      openid: option.id,
      yzmimg: 'http://94.191.42.64:1234/yzs/a4' + option.id + '.jpg'
    })
  }
})