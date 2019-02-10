const { $Message } = require('../../dist/base/index');
const { $Toast } = require('../../dist/base/index');
Page({
  data: {
    a3result: 'http://94.191.42.64:1234/xzs/a3result',
    a3getimg: 'http://94.191.42.64:1234/xzs/a3getimg?openid=',
    yzmimg: '',
    openid: '',
    zkzh: '',
    xm: '',
    yzm: ''
  },
  zkzhsInput: function (event) {
    this.setData({
      zkzh: event.detail.detail.value
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
    if (this.data.zkzh.length != 15) {
      $Message({
        content: '准考证号为15位',
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
    if (this.data.xm.length < 1) {
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
    network.getrequest(self.data.a3result, self.data, function (res) {
      $Toast.hide()
      console.log(res)
      if (res.error == '已超时或不存在'){
        $Toast({
          content: '已超时,请点击验证码刷新',
          type: 'warning',
        });
        return;
      }
      if (res.code == '500') {
        $Toast({
          content: '未知错误，请反馈我们',
          type: 'warning',
        });
        return;
      }
      wx.redirectTo({
        url: '../a3result/a3result?zkzh=' + self.data.zkzh + '&r1=' + res.r1 + '&r2=' + res.r2 + '&r3=' + res.r3 + '&r6=' + res.r6 + '&r7=' + res.r7 + '&r8=' + res.r8 + '&r9=' + res.r9 + '&r12=' + res.r12
      })
    }, function () {
      $Toast.hide()
      $Toast({
        content: '连接服务器失败',
        type: 'error'
      });
    })
  },
  reyzm: function (option) {
    $Toast({
      content: '加载中',
      type: 'loading',
      duration: 0
    });
    var network = require("../../tools/network.js")
    network.getrequest(self.data.a3getimg + self.data.openid, null, function (res) {
      $Toast.hide()
      console.log(res)
      if (res.code == '500') {
        $Toast({
          content: '未知错误，请反馈我们',
          type: 'warning',
        });
        return;
      }
      wx.redirectTo({
        url: '../a3/a3?id=' + self.data.openid
      })
    }, function () {
      $Toast.hide()
      $Toast({
        content: '连接服务器失败',
        type: 'error'
      });
    })
  },
  onLoad: function(option) {
    this.setData({
      openid: option.id,
      yzmimg: 'http://94.191.42.64:1234/xzs/' + option.id + '.jpg'
    })
  }
})